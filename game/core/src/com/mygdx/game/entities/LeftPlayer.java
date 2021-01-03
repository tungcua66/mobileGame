package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LeftPlayer extends Entity {
    public LeftPlayer() {
        super(new Vector2((Gdx.graphics.getWidth() * 0.25f), Gdx.graphics.getHeight() / 2f), "left");
        float size = Gdx.graphics.getWidth() * 0.8f / 20f;
        setWidth(size);
        setHeight(size);
    }

    @Override
    public void createBody(World physics) {
        super.createBody(physics);
        getBody().getFixtureList().get(0).setUserData("PLAYER");
    }
}
