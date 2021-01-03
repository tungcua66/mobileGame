package com.mygdx.game.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.Game;
import com.mygdx.game.util.SoundUtil;

public class WorldContactListener implements ContactListener {

    private Game game;

    public WorldContactListener(Game game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() == null || b.getUserData() == null) return;
        if (a.getUserData().equals("BALL")) {
            if (b.getUserData().equals("LEFT_GOAL")) {
                game.score(false);
            } else if (b.getUserData().equals("RIGHT_GOAL")) {
                game.score(true);
            } else if (b.getUserData().equals("PLAYER")) {
                SoundUtil.getInstance().play("shoot");
            }
        } else if (b.getUserData().equals("BALL")) {
            if (a.getUserData().equals("LEFT_GOAL")) {
                game.score(false);
            } else if (a.getUserData().equals("RIGHT_GOAL")) {
                game.score(true);
            } else if (b.getUserData().equals("PLAYER")) {
                SoundUtil.getInstance().play("shoot");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
