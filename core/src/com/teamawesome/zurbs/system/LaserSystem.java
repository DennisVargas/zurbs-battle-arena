package com.teamawesome.zurbs.system;

/**
 * Created by Amy on 11/24/2016.
 */

import com.artemis.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.MassData;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.teamawesome.zurbs.component.Laser;
import com.teamawesome.zurbs.manager.GameSceneManager;
import java.lang.*;


public class LaserSystem extends BaseSystem implements AfterSceneInit {

    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;
    VisIDManager idManager;

    VisSprite sprite1;
    Body body1;
    Controller controller1;
    Entity laser1;
    float desiredVel1 = 0.0f;


    @Override
    public void afterSceneInit() {
        // player1
        laser1 = idManager.get("Player01_laser");
        sprite1 = spriteCm.get(laser1);
        body1 = physicsCm.get(laser1).body;
        laser1.edit().add(new Laser(sprite1, 1));
        // player1
    }

    @Override
    protected void processSystem() {
        float y1 = body1.getLinearVelocity().y;
        if (Gdx.input.isKeyPressed(Keys.Q)) { // LEFT
            desiredVel1 = -y1;
        } else if (Gdx.input.isKeyPressed(Keys.E)) { // RIGHT
            desiredVel1 = y1;
        }
    }


}
