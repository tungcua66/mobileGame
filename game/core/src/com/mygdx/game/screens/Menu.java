package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.listeners.TouchListener;
import com.mygdx.game.listeners.TransitionListener;
import com.mygdx.game.util.TextureUtil;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Screen {
    List<Button> buttons;
    private TransitionListener transitionListener;
    private float menuW;
    private float menuH;
    private Vector2 menuPos;


    public Menu() {
        menuW = Gdx.graphics.getWidth() / 3f;
        menuH = Gdx.graphics.getHeight() * .8f;
        float menuItemH = menuH / 4f;
        menuPos = new Vector2(Gdx.graphics.getWidth() / 2f - menuW / 2f, Gdx.graphics.getHeight() / 2f - menuH / 2f);
        buttons = new ArrayList<>();

        buttons.add(new Button(menuPos.x, menuPos.y, menuW, menuItemH) {
            @Override
            public void onClick() {
                transitionListener.transition(new Game(true));
            }
        });

        buttons.add(new Button(menuPos.x, menuPos.y + menuItemH, menuW, menuItemH) {
            @Override
            public void onClick() {
                transitionListener.transition(new Game(false));
            }
        });

        buttons.add(new Button(menuPos.x, menuPos.y + menuItemH * 3, menuW, menuItemH) {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });
    }


    @Override
    public void tick(float deltaTime) {
        TouchListener.TouchInfo touch = TouchListener.getInstance().getTouchInfos()[0];
        if (touch.isTouched()) {
            for (Button b : buttons) {
                if (b.isInside(touch.getStartX(), touch.getStartY())) {
                    b.onClick();
                    return;
                }
            }
        }
    }


    @Override
    public void draw(SpriteBatch batch) {
        Texture intro = TextureUtil.getInstance().get("intro");
        batch.draw(intro, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture menu = TextureUtil.getInstance().get("menu");
        batch.draw(menu, menuPos.x, menuPos.y, menuW, menuH);
    }


    @Override
    public TransitionListener getTransitionListener() {
        return transitionListener;
    }

    @Override
    public void setTransitionListener(TransitionListener tl) {
        this.transitionListener = tl;
    }
}
