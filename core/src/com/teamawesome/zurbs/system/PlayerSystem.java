package com.teamawesome.zurbs.system;


import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.teamawesome.zurbs.component.Player;
import com.teamawesome.zurbs.manager.GameSceneManager;
import java.lang.*;

public class PlayerSystem extends BaseSystem implements AfterSceneInit {
    //assigned by artemis
    CameraManager cameraManager;
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;
    ComponentMapper<Player> playerCm;
    private ComponentMapper<VisSpriteAnimation> animationCM;

    VisIDManager idManager;

    VisSprite sprite1, sprite2;

    Body body1, body2;
    Controller controller1, controller2, controller3, controller4;
    Entity player1, player2;



    boolean flip1 = false;
    boolean flip2 = false;

    //  initialize debugRenderer
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private float maxVel = 8.0f;
    MassData massData = new MassData();
    boolean falling = false;
    boolean peak = false;




    @Override
    public void afterSceneInit() {
        massData.mass = 50.0f;

        // head box
        FixtureDef fdefHead = new FixtureDef();
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(.19f, .43f);
        vertice[1] = new Vector2(.42f, .43f);
        vertice[2] = new Vector2(.42f, .35f);
        vertice[3] = new Vector2(.19f, .35f);
        head.set(vertice);
        fdefHead.shape = head;


        // body box
        FixtureDef fdefZurb = new FixtureDef();
        PolygonShape body = new PolygonShape();
        Vector2[] vertice2 = new Vector2[5];
        vertice2[0] = new Vector2(.14f, .00f);
        vertice2[1] = new Vector2(.21f, .36f);
        vertice2[2] = new Vector2(.31f, .40f);
        vertice2[3] = new Vector2(.39f, .36f);
        vertice2[4] = new Vector2(.43f, .00f);
        body.set(vertice2);
        fdefZurb.shape = body;
        fdefZurb.isSensor = true;

/*
        // feet box
        FixtureDef fdefZurb = new FixtureDef();
        PolygonShape feet = new PolygonShape();
        Vector2[] vertice2 = new Vector2[4];
        vertice2[0] = new Vector2(.14f, .05f);
        vertice2[1] = new Vector2(.46f, .05f);
        vertice2[2] = new Vector2(.14f, .00f);
        vertice2[3] = new Vector2(.46f, .00f);
        feet.set(vertice2);
        fdefZurb.shape = feet;
        fdefZurb.isSensor = true;
*/

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
        Array<Controller> controllers = Controllers.getControllers();



        if(controllers.size > 2) {
            controller3 = controllers.get(2);
        }
        if(controllers.size > 3) {
            controller4 = controllers.get(3);
        }




        // player1
        player1 = idManager.get("Player01");
        player1.edit().add(new Player(controller1, "zurbBLUE", true));
        sprite1 = spriteCm.get(player1);
        body1 = physicsCm.get(player1).body;
        body1.setMassData(massData);
        fdefZurb.filter.categoryBits = GameSceneManager.PLAYER01_BIT;
        body1.createFixture(fdefZurb).setUserData(this); // attaches body box
        fdefHead.filter.categoryBits = GameSceneManager.PLAYER01_HEAD_BIT;
        body1.createFixture(fdefHead).setUserData(this); // attaches head
        if(controllers.size > 0) {
            controller1 = controllers.get(0);

        }

        // player1

        // player 2
        player2 = idManager.get("Player02");
        player2.edit().add(new Player(controller2, "zurbRED", true));
        sprite2 = spriteCm.get(player2);
        body2 = physicsCm.get(player2).body;
        body2.setMassData(massData);
        fdefZurb.filter.categoryBits = GameSceneManager.PLAYER02_BIT;
        body2.createFixture(fdefZurb).setUserData(this); // attaches body box
        fdefHead.filter.categoryBits = GameSceneManager.PLAYER02_HEAD_BIT;
        body2.createFixture(fdefHead).setUserData(this); // attaches head box
        if(controllers.size > 1) {
            controller2 = controllers.get(1);

        }

        // player 2

    }

    @Override
    protected void processSystem() {

        //  renders Debugger based on Box2dWorld from body1 and a Matrix4 of the camera projection
        debugRenderer.render(body1.getWorld(),new Matrix4(cameraManager.getCombined()));

        // if player 1 is alive
        if (!playerCm.get(player1).getToDestroy() && !playerCm.get(player1).isDestroyed()) {
            sprite1.setFlip(flip1, false);
            float x1 = body1.getLinearVelocity().x;
            float y1 = body1.getLinearVelocity().y;
            float desiredVel1 = 0.0f;

            if (controller1.getAxis(NextController.AXIS_X) < -NextController.STICK_DEADZONE) { // LEFT
                desiredVel1 = -maxVel;

                sprite1.setFlip(false, false);
                flip1 = true;
                playerCm.get(player1).setFacingRight(false);


            } else if (controller1.getAxis(NextController.AXIS_X) > NextController.STICK_DEADZONE) { // RIGHT
                desiredVel1 = maxVel;
                sprite1.setFlip(true, false);
                flip1 = false;
                playerCm.get(player1).setFacingRight(true);

            }


            if (Math.abs(body1.getLinearVelocity().y) < 0.005 && controller1.getButton(NextController.BUTTON_B)) { // UP
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
        } // if player 1 is alive

        // if player 2 is alive
        if (!playerCm.get(player2).getToDestroy() && !playerCm.get(player2).isDestroyed()) {
            sprite2.setFlip(flip2, false);
            float x2 = body2.getLinearVelocity().x;
            float y2 = body2.getLinearVelocity().y;
            float desiredVel2 = 0.0f;

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


            if (Math.abs(body2.getLinearVelocity().y) < 0.005 && Gdx.input.isKeyPressed(Keys.UP)) { // UP
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
        } // if player 2 is alive


    }

    public void hitOnHead(String playerKiller, String playerKilled) {
        System.out.println(playerKilled + " was smooshed by " + playerKiller + "!!1111");


        if (playerKilled == "Player01") {
            playerCm.get(player1).setToDestroy(true);
            //player1.getWorld().deleteEntity(player1);
        } else {
            playerCm.get(player2).setToDestroy(true);
            //player2 = idManager.get("Player02_squish");
            //body2.getWorld().destroyBody(body2);
        }
    }

    public void hitByLaser(String playerKiller, String playerKilled) {
        System.out.println(playerKilled + " was vaporized by " + playerKiller);
    }

}