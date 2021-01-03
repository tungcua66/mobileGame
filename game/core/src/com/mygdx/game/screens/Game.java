package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.controllers.CPUController;
import com.mygdx.game.controllers.PlayerController;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.LeftPlayer;
import com.mygdx.game.entities.RightPlayer;
import com.mygdx.game.listeners.TouchListener;
import com.mygdx.game.listeners.TransitionListener;
import com.mygdx.game.listeners.WorldContactListener;
import com.mygdx.game.util.ModelUtil;
import com.mygdx.game.util.SoundUtil;
import com.mygdx.game.util.TextureUtil;

import java.util.ArrayList;
import java.util.List;

public class Game implements Screen {
    static final float STEP_TIME = 1 / 60f;

    private List<Entity> entities;
    private World physics;
    private float timeLeft;
    private float accumulator;
    private Score score;
    private boolean resetPositions;
    private GlyphLayout glyphLayout;

    private float padSize;
    private Vector2 leftPad;
    private Vector2 rightPad;

    private TransitionListener transitionListener;
    private float goalTimer;
    private float gameOverTimer;
    private boolean singlePlayer;
    private Button exitButton;

    public Game(boolean singlePlayer) {
        timeLeft = 180f;
        entities = new ArrayList<>();
        physics = new World(new Vector2(0, 0), false);
        physics.setContactListener(new WorldContactListener(this));
        score = new Score();
        glyphLayout = new GlyphLayout();
        padSize = Gdx.graphics.getHeight() / 9.6f;
        this.singlePlayer = singlePlayer;
        float buttonW = Gdx.graphics.getWidth() * 0.072f;
        float buttonH = Gdx.graphics.getHeight() * 0.13f;
        exitButton = new Button(Gdx.graphics.getWidth()/2f -buttonW/2f, Gdx.graphics.getHeight()-buttonH, buttonW, buttonH) {
            @Override
            public void onClick() {
                transitionListener.transition(new Menu());
            }
        };
        init();
    }

    private void init() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        leftPad = new Vector2(width * 0.1f - padSize / 2f, height / 2f);
        rightPad = new Vector2(width * 0.9f + padSize / 2f, height / 2f);

        //Add entities
        Ball ball = new Ball();
        entities.add(ball);
        Entity leftPlayer = new LeftPlayer();
        leftPlayer.setController(new PlayerController(Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, leftPad, padSize * 1.5f));
        entities.add(leftPlayer);
        RightPlayer rightPlayer = new RightPlayer();
        if (!singlePlayer) {
            rightPlayer.setController(new PlayerController(Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, rightPad, padSize * 1.5f));
        } else {
            rightPlayer.setController(new CPUController(ball, leftPad.cpy()));
        }
        entities.add(rightPlayer);

        float aspect = (width * 0.8f) / height;
        Entity.scale = height / 100f;

        //Add entity bodies
        for (Entity e : entities) {
            e.createBody(physics);
        }
        //Add border fixture
        float xOffs = width * 0.1f / Entity.scale;
        BodyDef borderDef = new BodyDef();
        borderDef.position.set(xOffs, 0);

        Body border = physics.createBody(borderDef);

        List<Vector2> vertices = ModelUtil.read("models/terrain.txt", aspect, 1f);
        EdgeShape edge = new EdgeShape();
        for (int i = 0; i < vertices.size() - 1; ++i) {
            edge.set(vertices.get(i), vertices.get(i + 1));
            FixtureDef fd = new FixtureDef();
            fd.shape = edge;
            fd.restitution = 1f;
            fd.density = 0f;

            border.createFixture(fd);
        }
        edge.dispose();

        //Add goals
        BodyDef goalsDef = new BodyDef();
        goalsDef.type = BodyDef.BodyType.StaticBody;
        goalsDef.position.set(xOffs, 0);

        Body goals = physics.createBody(goalsDef);
        FixtureDef goalDef = new FixtureDef();
        goalDef.isSensor = true;
        PolygonShape goalShape = new PolygonShape();
        goalShape.setAsBox(.5f * aspect, 8.7890625f, new Vector2(5.6640625f * aspect, 49.804688f), 0);
        goalDef.shape = goalShape;
        goals.createFixture(goalDef).setUserData("LEFT_GOAL");
        goalShape.setAsBox(.5f * aspect, 8.7890625f, new Vector2(94.33594f * aspect, 49.804688f), 0);
        goals.createFixture(goalDef).setUserData("RIGHT_GOAL");
        goalShape.dispose();

    }

    /**
     * Called when a player scores
     * @param left
     */
    public void score(boolean left) {
        if (left) {
            score.left++;
        } else {
            score.right++;
        }
        SoundUtil.getInstance().play("goal");
        goalTimer = 1f;
    }


    @Override
    public void tick(float deltaTime) {
        // We should reset the Body positions when World is not updated
        if (resetPositions) {
            for (Entity e : entities) {
                e.reset();
            }
            resetPositions = false;
        }

        // Check if exit button is pressed
        for(TouchListener.TouchInfo touch: TouchListener.getInstance().getTouchInfos()) {
            if(touch.isTouched() && exitButton.isInside(touch.getStartX(), touch.getStartY())) {
                exitButton.onClick();
                break;
            }
        }

        float frameTime = Math.min(deltaTime, 0.25f);
        if (timeLeft > 0) {
            if (goalTimer > 0) { // If we have a goal timer, don't do anything else
                goalTimer -= frameTime;
                if (goalTimer <= 0) {
                    resetPositions = true; // Reset the entity positions when goalTimer reached zero
                }
                return;
            }
            timeLeft -= frameTime;

            accumulator += frameTime;
            while (accumulator > STEP_TIME) {
                physics.step(STEP_TIME, 6, 2);
                accumulator -= STEP_TIME;
            }

            // Let entities process their controllers and update their positions
            for (Entity e : entities) {
                e.tick();
            }
            if(timeLeft <= 0) gameOverTimer = 5f;
        }
        if(gameOverTimer > 0) {
            gameOverTimer-= frameTime;
            if(gameOverTimer <= 0) {
                resetPositions = true;
                score.reset();
                timeLeft = 180f;
            }
        }

    }

    @Override
    public void draw(SpriteBatch batch) {

        TextureUtil textures = TextureUtil.getInstance();
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        batch.draw(textures.get("background"), screenW * 0.1f, 0, screenW * 0.8f, screenH);
        Texture pad = textures.get("pad");

        // Draw entities
        for (Entity e : entities) {
            e.draw(batch);
        }

        // Draw gamepads
        batch.setColor(Color.YELLOW);
        float halfPad = padSize / 2f;
        batch.draw(pad, leftPad.x - halfPad, leftPad.y - halfPad, padSize, padSize);

        if (!singlePlayer) { // Don't draw the right pad when it's not necessary
            batch.setColor(Color.CYAN);
            batch.draw(pad, rightPad.x - halfPad, rightPad.y - halfPad, padSize, padSize);
        }
        batch.setColor(Color.WHITE);

        // Draw UI
        BitmapFont font = textures.getGameFont();
        String text = "" + score.left;
        font.draw(batch, text, screenW * .25f - getGlyphWidth(text) / 2f, screenH * 0.975f);
        text = "" + score.right;
        font.draw(batch, text, screenW * .75f - getGlyphWidth(text) / 2f, screenH * 0.975f);
        text = "" + (int) (Math.ceil(timeLeft));
        font.draw(batch, text, screenW * .5f - getGlyphWidth(text) / 2f, screenH * 0.975f);

        // Draw goal screen
        if (goalTimer > 0) {
            Texture goal = textures.get("goal");
            batch.draw(goal, screenW * .25f, screenH * .25f, screenW / 2f, screenH / 2f);
        }

        if(gameOverTimer > 0) {
            text = "GAME OVER";
            font.draw(batch, text, screenW /2f - getGlyphWidth(text) /2f, screenH * 0.85f);
            if(score.right > score.left) {
                text = "GAUCHE GAGNE";
            } else if (score.right < score.left) {
                text = "DROITE GAGNE";
            } else {
                text = "MATCH NUL";
            }
            font.draw(batch, text, screenW /2f - getGlyphWidth(text) /2f, screenH/2f - font.getLineHeight()/2f);
        }
    }

    @Override
    public TransitionListener getTransitionListener() {
        return transitionListener;
    }

    @Override
    public void setTransitionListener(TransitionListener tl) {
        this.transitionListener = tl;
    }

    private float getGlyphWidth(String text) {
        glyphLayout.setText(TextureUtil.getInstance().getGameFont(), text);
        return glyphLayout.width;
    }

    static class Score {
        int left;
        int right;

        public Score() {
            left = 0;
            right = 0;
        }

        public void reset() {
            left = 0;
            right = 0;
        }
    }
}
