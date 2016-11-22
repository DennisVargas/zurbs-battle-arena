package com.teamawesome.zurbs.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.artemis.ComponentMapper;
import com.artemis.Entity;


public class PlayerSystem extends BaseSystem implements AfterSceneInit {
    //assigned by artemis
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;
    ComponentMapper<Transform> transformCm;
    VisIDManager idManager;

    VisSprite sprite;
    Body body;
    Transform trans;


    @Override
    public void afterSceneInit() {
        Entity player = idManager.get("PlayerXX");
        trans = transformCm.get(player);
        sprite = spriteCm.get(player);
        body = physicsCm.get(player).body;
    }

    @Override
    protected void processSystem() {
        float x = body.getLinearVelocity().x;
        float y = body.getLinearVelocity().y;
        float desiredVel = 0;

        if (Gdx.input.isKeyPressed(Keys.A)) { // LEFT
            desiredVel = -20;
            sprite.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Keys.D)) { // RIGHT
            desiredVel = 20;
            sprite.setFlip(true, false);
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) { // UP
            float impulse = body.getMass() * 300;
            body.applyForce(0, impulse, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }

        float velChange = desiredVel - x;
        float impulse = body.getMass() * velChange;
        body.applyForce(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);

        if (body.getPosition().y < 0) {
            body.setTransform(body.getPosition().x, 9.0f, 0.0f);
            System.out.println("position = " + body.getPosition());
        }
        //System.out.println("y = " + trans.getY());

    }
}