package com.teamawesome.zurbs.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
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
    ComponentMapper<VisSpriteAnimation> visSprtAnimCM;
    VisIDManager idManager;

    VisSprite sprite, sprite1, sprite2;
    VisSpriteAnimation animation, animation1, animation2;
    Body body, body1, body2;

    private float maxVel = 8.0f;

    MassData massData = new MassData();
    boolean falling = false;
    boolean peak = false;

    Controller controller, controller1, controller2;
    Entity player, player1, player2;


    @Override
    public void afterSceneInit() {
        // original code
        player = idManager.get("PlayerXX");
//        player.edit().add(new Player(controller, "zurbBlue"));
        sprite = spriteCm.get(player);
        animation = visSprtAnimCM.get(player);
        body = physicsCm.get(player).body;

        massData.mass = 50.0f;
        body.setMassData(massData);

  /*      for(int i = 0; i < 4; i++){
            player = idManager.get("Player0"+(i+1));
            sprite = spriteCm.get(player);
            animation = visSprtAnimCM.get(player);
            body = physicsCm.get(player).body;
            massData.mass = 50.0f;
            body.setMassData(massData);

            try{
                controller = Controllers.getControllers().get(i);
                controller.addListener(ListenerFactory(body));
            }catch(Exception e){
                controller = null;
                System.out.println("no control "+i);
            }
        }*/
        //animation.setAnimationName("zurbBlue_idle");
        // original code

        // player1
        player1 = idManager.get("Player01");
        player1.edit().add(new Player(controller1, "zurbBLUE"));
        sprite1 = spriteCm.get(player1);
        animation1 = visSprtAnimCM.get(player1);
//        run1 = new Animation(0.07, );
        body1 = physicsCm.get(player1).body;

//        massData.mass = 50.0f;
        body1.setMassData(massData);
        // player1

        //player2
        player2 = idManager.get("Player02");
        player2.edit().add(new Player(controller2, "zurbRED"));
        sprite2 = spriteCm.get(player2);
        animation2 = visSprtAnimCM.get(player2);
        body2 = physicsCm.get(player2).body;

//        massData.mass = 50.0f;
        body2.setMassData(massData);
        //player2

    }

    @Override
    protected void processSystem() {
        float x = body.getLinearVelocity().x;
      //  System.out.println("x velocity: "+x);
        float y = body.getLinearVelocity().y;
       // System.out.println("y velocity: "+y);

        // players 1 & 2
        float x1 = body1.getLinearVelocity().x;
        float x2 = body2.getLinearVelocity().x;
        float y1 = body1.getLinearVelocity().y;
        float y2 = body2.getLinearVelocity().y;
        // players 1 & 2

        float desiredVel1 = 0.0f;
        float desiredVel2 = 0.0f;


        // original code
        /*
        if (Gdx.input.isKeyPressed(Keys.A)) { // LEFT
            desiredVel = -maxVel;
       //     animation.setAnimationName(player.getComponent(Player.class).getSpriteColor()+"_run");
            sprite.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Keys.D)) { // RIGHT
            desiredVel = maxVel;
            //  animation.setAnimationName("zurbBlue_run");
            sprite.setFlip(true, false);
        }

        if (Math.abs(body.getLinearVelocity().y) < 0.005 && Gdx.input.isKeyJustPressed(Keys.SPACE)) { // UP
            float impulse = body.getMass() * 400;
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
        */
        // original code


        // move player1
        if (Gdx.input.isKeyPressed(Keys.A)) { // LEFT
            desiredVel1 = -maxVel;
            animation1.setAnimationName(player1.getComponent(Player.class).getSpriteColor()+"_run");
            sprite1.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Keys.D)) { // RIGHT
            desiredVel1 = maxVel;
            animation1.setAnimationName("zurbBLUE_run");
            sprite1.setFlip(true, false);
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
            animation2.setAnimationName(player2.getComponent(Player.class).getSpriteColor()+"_run");
            sprite2.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) { // RIGHT
            desiredVel2 = maxVel;
            animation2.setAnimationName("zurbRED_run");
            sprite2.setFlip(true, false);
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

    private ControllerListener ListenerFactory(final Body bod){
        ControllerListener listener = new ControllerListener() {
            @Override
            public void connected(Controller controller) {

            }

            @Override
            public void disconnected(Controller controller) {

            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                bod.applyForceToCenter(100.0f, 0, true);
                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
                return false;
            }
        };
        return listener;
    }
}