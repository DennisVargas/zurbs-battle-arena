package com.teamawesome.zurbs.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import java.lang.*;


public class PlayerSystem extends BaseSystem implements AfterSceneInit {
    //assigned by artemis
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;

    VisIDManager idManager;

    VisSprite sprite;
    Body body;

    private float maxVel = 8.0f;

    MassData massData = new MassData();
    boolean falling = false;
    boolean peak = false;

    @Override
    public void afterSceneInit() {
        Entity player = idManager.get("PlayerXX");
              sprite = spriteCm.get(player);
        body = physicsCm.get(player).body;

        massData.mass = 50.0f;
        body.setMassData(massData);
    }

    @Override
    protected void processSystem() {
        float x = body.getLinearVelocity().x;
        System.out.println("x velocity: "+x);
        float y = body.getLinearVelocity().y;
        System.out.println("y velocity: "+y);

        float desiredVel = 0.0f;

        if (Gdx.input.isKeyPressed(Keys.A)) { // LEFT
            desiredVel = -maxVel;
            sprite.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Keys.D)) { // RIGHT
            desiredVel = maxVel;
            sprite.setFlip(true, false);
        }

        if (Math.abs(body.getLinearVelocity().y) < 0.005 && Gdx.input.isKeyJustPressed(Keys.SPACE)) { // UP
            float impulse = body.getMass() * 350;
            body.applyForce(0, impulse, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }

        float velChange = desiredVel - x;
        float impulse = body.getMass() * velChange;
        body.applyForce(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);

        if (body.getPosition().y < 0) {
            body.setTransform(body.getPosition().x, 9.0f, 0.0f);
            System.out.println("position = " + body.getPosition());
        }

        if (body.getPosition().y > 9) {
            body.setTransform(body.getPosition().x, 0.0f, 0.0f);
            System.out.println("position = " + body.getPosition());
        }

    }
}