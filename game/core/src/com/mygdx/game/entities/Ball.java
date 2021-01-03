package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Ball extends Entity {
    public Ball() {
        super(new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f), "ball");
        float size = Gdx.graphics.getWidth() / 85f * 2;
        setWidth(size);
        setHeight(size);
        restitution = 0.5f;
    }

    @Override
    public void createBody(World physics) {
        super.createBody(physics);
        getBody().getFixtureList().get(0).setUserData("BALL");
    }
}
