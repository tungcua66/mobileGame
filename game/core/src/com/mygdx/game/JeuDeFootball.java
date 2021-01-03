package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.listeners.TouchListener;
import com.mygdx.game.listeners.TransitionListener;
import com.mygdx.game.screens.Screen;
import com.mygdx.game.screens.Splash;
import com.mygdx.game.util.SoundUtil;
import com.mygdx.game.util.TextureUtil;

public class JeuDeFootball extends ApplicationAdapter {
    static Screen active;
    private SpriteBatch batch;
    private TransitionListener transitionListener;

    @Override
    public void create() {
        batch = new SpriteBatch();
        active = new Splash();
        Gdx.input.setInputProcessor(TouchListener.getInstance());
        transitionListener = new TransitionListener() {
            @Override
            public void transition(Screen newScreen) {
                setScreen(newScreen);
            }
        };
        active.setTransitionListener(transitionListener);
    }

    @Override
    public void render() {

        active.tick(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        active.draw(batch);
        batch.end();
    }

    public void setScreen(Screen newScreen) {
        active = newScreen;
        active.setTransitionListener(transitionListener);
    }

    @Override
    public void dispose() {
		TextureUtil.getInstance().dispose();
		SoundUtil.getInstance().dispose();
    }


}
