package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.listeners.TransitionListener;

public interface Screen {
    /**
     * Executes the screen's logic
     * @param deltaTime the time since the last frame rendered
     */
    void tick(float deltaTime);


    /**
     * Draws the current frame of the screen
     * @param batch the batch we'll use during rendering
     */
    void draw(SpriteBatch batch);


    /*
     * These are the getter and setter for the TransitionListener, which could handle
     * Screen transitions
     */
    TransitionListener getTransitionListener();

    void setTransitionListener(TransitionListener tl);
}
