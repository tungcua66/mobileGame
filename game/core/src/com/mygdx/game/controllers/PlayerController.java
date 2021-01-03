package com.mygdx.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.listeners.TouchListener;

public class PlayerController implements Controller {

    private int[] buttons;
    private float speed = 60f;
    private Vector2 controllerCenter;
    private float radius;
    private float radiusSq;
    private float controllerScale;

    public PlayerController(int u, int d, int l, int r, Vector2 controllerCenter, float radius) {
        buttons = new int[]{u, d, l, r};
        this.controllerCenter = controllerCenter.cpy();
        this.radius = radius;
        this.radiusSq = radius * radius;
        this.controllerScale = 1 / radius * speed;
    }

    @Override
    public void update(Entity controlled) {
        Input input = Gdx.input;
        TouchListener tl = TouchListener.getInstance();
        if (input.isKeyPressed(buttons[0])) { //up
            controlled.accelerate(0, speed);
        } else if (input.isKeyPressed(buttons[1])) { // down
            controlled.accelerate(0, -speed);
        }

        if (input.isKeyPressed(buttons[2])) { //left
            controlled.accelerate(-speed, 0);
        } else if (input.isKeyPressed(buttons[3])) { // right
            controlled.accelerate(speed, 0);
        }

        for (TouchListener.TouchInfo ti : tl.getTouchInfos()) {
            if (!ti.isTouched()) continue;
            Vector2 startLoc = new Vector2(ti.getStartX(), ti.getStartY());
            Vector2 currLoc = new Vector2(ti.getTouchX(), ti.getTouchY());
            if (controllerCenter.dst2(startLoc) < radiusSq) {
                Vector2 acc = controllerCenter.cpy().sub(currLoc).nor().scl(-speed, speed);
                controlled.accelerate(acc.x, acc.y);
            }
        }

    }
}
