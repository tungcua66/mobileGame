package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.controllers.Controller;
import com.mygdx.game.util.TextureUtil;

/**
 * This is the main abstract class for the moving entities. It handles most of the common
 * behaviour.
 */
public abstract class Entity {
    public static float scale; // This is the global scaling according to the window size

    Vector2 startPos; // Starting position of the entity
    Vector2 position; // Current position of the entity
    float width;
    float height;
    Texture texture; // The texture of the entity
    float restitution = 0.25f;
    float maxSpeed = 300f;
    Controller controller = null; // The controller for the object
    Body body;

    public Entity(Vector2 startPos, String textureName) {
        this.startPos = startPos;
        this.texture = TextureUtil.getInstance().get(textureName);
        this.position = startPos.cpy();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void reset() {
        position = startPos.cpy();
        body.setLinearVelocity(new Vector2());
        body.setTransform(position.cpy().scl(1 / scale), 0);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2f, position.y - height / 2f, width, height);
    }

    /**
     * Logic of the entity. Updates the controller if exists, and set the position according
     * to the physical location.
     */
    public void tick() {
        if (controller != null) {
            controller.update(this);
        }
        position.set(body.getTransform().getPosition().cpy().scl(scale));
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Body getBody() {
        return body;
    }

    /**
     * This will create the Box2D body for the entity.
     * @param physics reference to the World object.
     */
    public void createBody(World physics) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.linearDamping = 1.5f;
        bodyDef.position.set(position.cpy().scl(1 / scale));
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = physics.createBody(bodyDef);

        FixtureDef fd = new FixtureDef();
        fd.density = 1f;
        fd.restitution = this.restitution;

        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth() / scale / 2f);
        fd.shape = shape;

        body.createFixture(fd);
        shape.dispose();
    }

    /**
     * Accelerates the entity in the given direction, limits according to maxSpeed
     * @param x
     * @param y
     */
    public void accelerate(float x, float y) {
        if (body == null) return;
        body.applyLinearImpulse(new Vector2(x, y), body.getPosition(), true);
        body.setLinearVelocity(body.getLinearVelocity().limit(maxSpeed));
    }
}
