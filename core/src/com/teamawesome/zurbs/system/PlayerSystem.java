package com.teamawesome.zurbs.system;


import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Player;
import com.teamawesome.zurbs.component.Winner;
import com.teamawesome.zurbs.manager.GameSceneManager;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class PlayerSystem extends BaseSystem implements AfterSceneInit {
    //assigned by artemis
    CameraManager cameraManager;
    ComponentMapper<Transform> transCm;
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<PhysicsBody> physicsCm;
    ComponentMapper<Player> playerCm;
    ComponentMapper<Winner> winnerCm;

    private ComponentMapper<VisSpriteAnimation> animationCM;
    Robot robot = new Robot();

    VisIDManager idManager;

    Array<String> players = new Array<String>();

    //  initialize debugRenderer
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private float maxVel = 8.0f;
    MassData massData = new MassData();
    boolean falling = false;
    boolean peak = false;
    int deathCount = 0;
    int deathMax = 1;
    Entity winner ;

    public PlayerSystem() throws AWTException {
    }

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


        com.kennycason.gdx.controller.Controller<GameControls> controller = ControllerFactory.buildMultiController("Player01", 0);
        players.add("Player01");
        InitializePlayer("Player01", "zurbBLUE", controller, fdefZurb, fdefHead,
                GameSceneManager.PLAYER01_BIT, GameSceneManager.PLAYER01_HEAD_BIT);


        controller = ControllerFactory.buildMultiController("Player02", 1);
        players.add("Player02");
        InitializePlayer("Player02", "zurbRED", controller, fdefZurb, fdefHead,
                GameSceneManager.PLAYER02_BIT, GameSceneManager.PLAYER02_HEAD_BIT);


        if(Controllers.getControllers().size > 2) {
            controller = ControllerFactory.buildMultiController("Player03", 2);
            players.add("Player03");
            InitializePlayer("Player03", "zurbGREEN", controller, fdefZurb, fdefHead,
                    GameSceneManager.PLAYER03_BIT, GameSceneManager.PLAYER03_HEAD_BIT);
            deathMax = 2;
        }
        else {
            Entity player = idManager.get("Player03");
            player.deleteFromWorld();
        }

        if(Controllers.getControllers().size > 3) {
            controller = ControllerFactory.buildMultiController("Player04", 3);
            players.add("Player04");
            InitializePlayer("Player04", "zurbPURPLE", controller, fdefZurb, fdefHead,
                    GameSceneManager.PLAYER04_BIT, GameSceneManager.PLAYER04_HEAD_BIT);
            deathMax = 3;
        }
        else {
            Entity player = idManager.get("Player04");
            player.deleteFromWorld();
        }

    }

    protected void InitializePlayer(String name, String spriteName, com.kennycason.gdx.controller.Controller con,
                                    FixtureDef fxdBody, FixtureDef fxdHead,
                                    short bodyBit, short headBit) {

        Entity player = idManager.get(name);
        player.edit().add(new Player(con, spriteName, true));
        Body body = physicsCm.get(player).body;
        body.setMassData(massData);
        fxdBody.filter.categoryBits = bodyBit;
        body.createFixture(fxdBody).setUserData(this); // attaches body box
        fxdHead.filter.categoryBits = headBit;
        body.createFixture(fxdHead).setUserData(this); // attaches head

    }

    protected void ProcessPlayerController(String name) {
        Entity player = idManager.get(name);
        Body body = physicsCm.get(player).body;
        VisSprite sprite  = spriteCm.get(player);
        Player playerClass = player.getComponent(Player.class);

        // if player is alive
        if (!playerCm.get(player).getToDestroy() && !playerCm.get(player).isDestroyed()) {
            sprite.setFlip(!playerClass.isFacingRight(), false);
            float x1 = body.getLinearVelocity().x;
            float y1 = body.getLinearVelocity().y;
            float desiredVel = 0.0f;

            com.kennycason.gdx.controller.Controller con = player.getComponent(Player.class).getController();

            if(con != null) {
                if (con.isPressed(GameControls.DPAD_LEFT)) { // LEFT
                    desiredVel = -maxVel;
                    sprite.setFlip(false, false);
                    playerClass.setFacingRight(false);
                    player.getComponent(VisSpriteAnimation.class).setAnimationName(playerClass.getSpriteColor() + "_run");

                } else if (con.isPressed(GameControls.DPAD_RIGHT)) { // RIGHT
                    desiredVel = maxVel;
                    sprite.setFlip(true, false);
                    playerClass.setFacingRight(true);
                    player.getComponent(VisSpriteAnimation.class).setAnimationName(playerClass.getSpriteColor() + "_run");
                }
                else {
                    player.getComponent(VisSpriteAnimation.class).setAnimationName(playerClass.getSpriteColor() + "_idle");
                }

                if (Math.abs(body.getLinearVelocity().y) < 0.005 && con.isPressed(GameControls.B)) { // UP
                    float impulse1 = body.getMass() * 400;
                    body.applyForce(0, impulse1, body.getWorldCenter().x, body.getWorldCenter().y, true);
                }
            }
            float impulse = body.getMass() * desiredVel - x1;
            body.applyForce(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);

            if (body.getPosition().y < 0) {
                body.setTransform(body.getPosition().x, 9.0f, 0.0f);
                System.out.println("position = " + body.getPosition());
            }

            if (body.getPosition().y > 9) {
                body.setTransform(body.getPosition().x, 0.0f, 0.0f);
                System.out.println("position = " + body.getPosition());
            }
        } else {
            body.setTransform(0.0f, 15.0f, 0.0f);  // transport to zurb prison
        }

    }

    @Override
    protected void processSystem() {
        //  renders Debugger based on Box2dWorld from body1 and a Matrix4 of the camera projection
        //debugRenderer.render(body1.getWorld(),new Matrix4(cameraManager.getCombined()));

        for(String player : players)
            ProcessPlayerController(player);

    }

    public void hitOnHead(String playerKiller, String playerKilled) {
        System.out.println(playerKilled + " was smooshed by " + playerKiller + "!!1111");

        Entity player = idManager.get(playerKilled);
        playerCm.get(player).setDestroyed(true);
        //player.getWorld().deleteEntity(player);
        deathCount++;
        if (deathCount == deathMax ) {
            System.out.println(playerKiller + " is the winner");
            WinnerPNG(playerKiller);
        }
    }

    public void hitByLaser(String playerKiller, String playerKilled) {
        System.out.println(playerKilled + " was vaporized by " + playerKiller);

        Entity player = idManager.get(playerKilled);
        playerCm.get(player).setDestroyed(true);
        //player.getWorld().deleteEntity(player);
        deathCount++;
        if (deathCount == deathMax ) {
            System.out.println(playerKiller + " is the winner");
            WinnerPNG(playerKiller);
        }
    }
    public void WinnerPNG(String id1) {
        float x, y;
     //   VisSprite sprite;
        Entity spriteEnt;

        switch(id1) {
            case "Player01":
                spriteEnt = idManager.get("Player1Win");
                break;
            case "Player02":
                spriteEnt = idManager.get("Player2Win");
                break;
            case "Player03":
                spriteEnt = idManager.get("Player3Win");
                break;

            default:
                spriteEnt = idManager.get("Player4Win");

        }


       // sprite = spriteCm.get(spriteEnt);

        x = 4.375f;
        y = 2.25f;

        transCm.get(spriteEnt).setPosition(x, y);

        winner = idManager.get("Winner");
        if (winnerCm.get(winner).isWinner == false)
            winnerCm.get(winner).isWinner = true;
       // robot.keyPress(KeyEvent.VK_ESCAPE); // robot hits escape to pause game

    }
}