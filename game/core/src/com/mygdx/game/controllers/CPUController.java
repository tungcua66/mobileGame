package com.mygdx.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;

public class CPUController implements Controller{
    private Entity ball;
    private Vector2 target;
    private float speed = 50f;

    public CPUController(Entity ball, Vector2 target) {
        this.ball = ball;
        this.target = target;
    }

    @Override
    public void update(Entity controlled) {
        Vector2 dir = ball.getPosition().cpy().sub(target).nor().scl(ball.getWidth());
        Vector2 hitSpot = ball.getPosition().cpy().add(dir);
        Vector2 acceleration = hitSpot.cpy().sub(controlled.getPosition()).limit(speed);
        controlled.accelerate(acceleration.x, acceleration.y);
    }
}
