package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.listeners.TransitionListener;
import com.mygdx.game.util.SoundUtil;
import com.mygdx.game.util.TextureUtil;

public class Splash implements Screen {

    private float timeLeft;
    private TransitionListener transitionListener;

    public Splash() {
        timeLeft = 3f;
        SoundUtil.getInstance(); // Initialize sounds, so they will already be loaded when the game starts
    }

    @Override
    public void tick(float deltaTime) {
        timeLeft -= deltaTime;
        if (timeLeft <= 0) {
            getTransitionListener().transition(new Menu());
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        Texture intro = TextureUtil.getInstance().get("intro");
        batch.draw(intro, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
