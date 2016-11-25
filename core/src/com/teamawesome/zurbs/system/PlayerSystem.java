package com.teamawesome.zurbs.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.teamawesome.zurbs.component.Player;
import java.lang.*;


public class PlayerSystem extends BaseSystem implements AfterSceneInit {
    //assigned by artemis
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;
    ComponentMapper<Player> playerCm;
    VisIDManager idManager;

    VisSprite sprite1, sprite2;
    Body body1, body2;
    Controller controller1, controller2;
    Entity player1, player2;
    boolean flip1 = false;
    boolean flip2 = false;


    private float maxVel = 8.0f;
    MassData massData = new MassData();
    boolean falling = false;
    boolean peak = false;


    @Override
    public void afterSceneInit() {
        massData.mass = 50.0f;

        // original code
        /*
        for(int i = 0; i < 4; i++){
            try{
                controller = Controllers.getControllers().get(i);
                controller.addListener(ListenerFactory(body));
            }catch(Exception e){
                controller = null;
                System.out.println("no control "+i);
            }
        }*/
        // original code

        // player1
        player1 = idManager.get("Player01");
        player1.edit().add(new Player(controller1, "zurbBLUE", true));
        sprite1 = spriteCm.get(player1);
        body1 = physicsCm.get(player1).body;
        body1.setMassData(massData);
        // player1

        //player2
        player2 = idManager.get("Player02");
        player2.edit().add(new Player(controller2, "zurbRED", true));
        sprite2 = spriteCm.get(player2);
        body2 = physicsCm.get(player2).body;
        body2.setMassData(massData);
        //player2

    }

    @Override
    protected void processSystem() {
        sprite1.setFlip(flip1, false);
        sprite2.setFlip(flip2, false);

        // players 1 & 2
        float x1 = body1.getLinearVelocity().x;
        float x2 = body2.getLinearVelocity().x;
        float y1 = body1.getLinearVelocity().y;
        float y2 = body2.getLinearVelocity().y;
        // players 1 & 2

        float desiredVel1 = 0.0f;
        float desiredVel2 = 0.0f;

        // move player1
        if (Gdx.input.isKeyPressed(Keys.A)) { // LEFT
            desiredVel1 = -maxVel;
            sprite1.setFlip(false, false);
            flip1 = true;
            playerCm.get(player1).setFacingRight(false);
        } else if (Gdx.input.isKeyPressed(Keys.D)) { // RIGHT
            desiredVel1 = maxVel;
            sprite1.setFlip(true, false);
            flip1 = false;
            playerCm.get(player1).setFacingRight(true);
        }

        if (Math.abs(body1.getLinearVelocity().y) < 0.005 && Gdx.input.isKeyJustPressed(Keys.SPACE)) { // UP
            float impulse1 = body1.getMass() * 400;
            body1.applyForce(0, impulse1, body1.getWorldCenter().x, body1.getWorldCenter().y, true);
        }

        float velChange1 = desiredVel1 - x1;
        float impulse1 = body1.getMass() * velChange1;
        body1.applyForce(impulse1, 0, body1.getWorldCenter().x, body1.getWorldCenter().y, true);

        if (body1.getPosition().y < 0) {
            body1.setTransform(body1.getPosition().x, 9.0f, 0.0f);
            System.out.println("position = " + body1.getPosition());
        }

        if (body1.getPosition().y > 9) {
            body1.setTransform(body1.getPosition().x, 0.0f, 0.0f);
            System.out.println("position = " + body1.getPosition());
        }
        // move player1


        // move player2
        if (Gdx.input.isKeyPressed(Keys.LEFT)) { // LEFT
            desiredVel2 = -maxVel;
            sprite2.setFlip(false, false);
            flip2 = true;
            playerCm.get(player2).setFacingRight(false);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) { // RIGHT
            desiredVel2 = maxVel;
            sprite2.setFlip(true, false);
            flip2 = false;
            playerCm.get(player2).setFacingRight(true);
        }

        if (Math.abs(body2.getLinearVelocity().y) < 0.005 && Gdx.input.isKeyJustPressed(Keys.UP)) { // UP
            float impulse2 = body2.getMass() * 400;
            body2.applyForce(0, impulse2, body2.getWorldCenter().x, body2.getWorldCenter().y, true);
        }

        float velChange2 = desiredVel2 - x2;
        float impulse2 = body2.getMass() * velChange2;
        body2.applyForce(impulse2, 0, body2.getWorldCenter().x, body2.getWorldCenter().y, true);

        if (body2.getPosition().y < 0) {
            body2.setTransform(body2.getPosition().x, 9.0f, 0.0f);
            System.out.println("position = " + body2.getPosition());
        }

        if (body2.getPosition().y > 9) {
            body2.setTransform(body2.getPosition().x, 0.0f, 0.0f);
            System.out.println("position = " + body2.getPosition());
        }
        // move player2

    }

}