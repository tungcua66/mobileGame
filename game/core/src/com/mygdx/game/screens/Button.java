package com.mygdx.game.screens;

import com.badlogic.gdx.math.Vector2;

public abstract class Button {
    private Vector2 topLeft;
    private Vector2 bottomRight;
    private float width;
    private float height;

    public Button(float x, float y, float width, float height) {
        topLeft = new Vector2(x, y);
        this.width = width;
        this.height = height;
        bottomRight = topLeft.cpy().add(width, height);
    }

    public boolean isInside(float x, float y) {
        return (x > topLeft.x && y > topLeft.y && x < bottomRight.x && y < bottomRight.y);
    }

    public abstract void onClick();

    public Vector2 getTopLeft() {
        return topLeft;
    }

    public Vector2 getBottomRight() {
        return bottomRight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
