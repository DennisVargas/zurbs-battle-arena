package com.teamawesome.zurbs.manager;

import com.artemis.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.*;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.system.physics.PhysicsBodyManager;
import com.kotcrab.vis.runtime.system.physics.PhysicsSystem;
import com.kotcrab.vis.runtime.util.entity.composer.EntityComposer;
import com.kotcrab.vis.runtime.util.entity.composer.SpriteEntityComposer;
import com.teamawesome.zurbs.WorldContactListener;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Laser;
import com.teamawesome.zurbs.component.Player;
import com.teamawesome.zurbs.component.Velocity;
import com.teamawesome.zurbs.component.Winner;
import com.teamawesome.zurbs.system.GameModeControllerListener;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager{
    ComponentMapper<Layer> layerCm;
    ComponentMapper<Renderable> renderCM;
    ComponentMapper <Laser> laserCm;
    ComponentMapper <Velocity> velocityCm;
    ComponentMapper<VisID> idCm;
    ComponentMapper<VisPolygon> visPolyCm;
    ComponentMapper<OriginalRotation> ogRotCm;
    private ComponentMapper<VisSpriteAnimation> animationCM;
    private ComponentMapper<Transform> transCM;
    private ComponentMapper<Player> playerCm;
    private ComponentMapper<PhysicsBody> physicsCm;
    private ComponentMapper<Winner> winnerCm;
    private VisSpriteAnimation animation1, animation2;
    private Entity player1, player2;
    private Entity laser;
    boolean isWinner;
    Entity winner;

    private Array<String> players = new Array<String>();

    String wallName = "";
    Entity wall;

    Archetype laserArchetype;

    //Box2D Collision Category Bits
    public static final short NOTHING_BIT = 0;
    public static final short WALL_BIT = 1;

    public static final short PLAYER01_BIT = 2;
    public static final short PLAYER01_HEAD_BIT = 4;
    public static final short PLAYER01_LASER_BIT = 8;

    public static final short PLAYER02_BIT = 16;
    public static final short PLAYER02_HEAD_BIT = 32;
    public static final short PLAYER02_LASER_BIT = 64;

    public static final short PLAYER03_BIT = 128;
    public static final short PLAYER03_HEAD_BIT =256;
    public static final short PLAYER03_LASER_BIT = 512;

    public static final short PLAYER04_BIT = 1024;
    public static final short PLAYER04_HEAD_BIT = 2048;
    public static final short PLAYER04_LASER_BIT = 4096;



    //public static final short x1_BIT = 32;
    //public static final short x2_BIT = 64;
    //public static final short x3_BIT = 128;
    //public static final short x4_BIT = 256;
    //public static final short MARIO_HEAD_BIT = 512;
    //public static final short FIREBALL_BIT = 1024;



    public void AddWallBits() {
    /*
        FixtureDef fdefZurb = new FixtureDef();
        PolygonShape body = new PolygonShape();
        Vector2[] vertice2 = new Vector2[5];
        vertice2[0] = new Vector2(.14f, .00f);
        body.set(vertice2);
        fdefZurb.shape = body;
        fdefZurb.isSensor = true;
        fdefZurb.filter.categoryBits = GameSceneManager.PLAYER01_BIT;
        body1.createFixture(fdefZurb).setUserData(this); // attaches body box
*/
        //String wallName = "";
        //Entity wall;

        for(int i = 1; i < 10; i ++){
           // FixtureDef wallSensor = new FixtureDef();
            if(i < 10&& i!=0)
                wallName = "wall0"+i;
            else if(i == 0)
                wallName = "wall";
            else
                wallName = "wall"+i;

            wall = idManager.get(wallName);
            Array<Fixture> fixArray = physicsCm.get(wall).body.getFixtureList();
       /*     fixArray.get(0).getFilterData().categoryBits = WALL_BIT;
            wallSensor.shape = fixArray.get(0).getShape();
            wall.getComponent(PhysicsBody.class).body.createFixture(wallSensor);*/
            for(Fixture fixture: fixArray) {
                Filter filterData = new Filter();
                filterData.categoryBits = WALL_BIT;
                fixture.setFilterData(filterData);
                //wallSensor.shape = fixture.getShape();
                //wall.getComponent(PhysicsBody.class).body.createFixture(wallSensor);
            }
        }
    }

    public GameSceneManager(ZurbGame game){
        super(game);
    }

    @Override
    public void afterSceneInit() {

        super.afterSceneInit();

      laserArchetype = new ArchetypeBuilder().add(VisSprite.class)
                                                    .add(VisPolygon.class)
                                                    .add(PhysicsProperties.class)
                                                    .add(Laser.class)
                                                    .add(Renderable.class)
                                                    .add(Layer.class)
                                                    .add(Transform.class)
                                                    .build(world);
        winner = world.createEntity();// creates winner entity
        idCm.create(winner).id = "Winner";// create id for winner component
        winnerCm.create(winner);//  creates winner component on winner entity


        players.add("Player01");
        Entity p = idManager.get("Player01");
        this.AddWallBits();

        physicsCm.get(p).body.getWorld().setContactListener(new WorldContactListener());
        player1 = p;
        animation1 = animationCM.get(p);
        p = idManager.get("Player02");
        animation2 = animationCM.get(p);
        player2 = p;

        for(int i = 0; i < game.controllers.size; i++) {
            if(i >= 1)
                players.add("Player0"+(i+1));
            Entity player = idManager.get(players.get(i));
            ControllerListener listener = new GameModeControllerListener(player, this);
            game.controllers.get(i).addListener(listener);
            game.controllerListeners.add(listener);
        }
    }

    @Override
    public boolean keyDown(int keyCode){
        VisSprite pauseMenu;
        Entity spriteEnt;
        spriteEnt = idManager.get("PauseMenu");
        pauseMenu = spriteCm.get(spriteEnt);

        if (keyCode == 131) { // ESC - pause
            PauseGame();

        }
        if(keyCode == 41){ // M - main menu
            if(this.game.getState() == ZurbGame.State.paused){
                this.game.setState(ZurbGame.State.play);
                transCM.get(spriteEnt).setPosition(1600f, 900f); // remove pause menu
                this.game.loadMenuScene();
            }
        }

        if(keyCode == 46){ // R - restart
            if(this.game.getState() == ZurbGame.State.paused){
                this.game.setState(ZurbGame.State.play);
                transCM.get(spriteEnt).setPosition(1600f, 900f); // remove pause menu
                this.game.loadStartGameScene();
            }
        }
            /*if(physicsCm.get(player1).body.isActive())
                physicsCm.get(player1).body.setActive(false);
            else
                physicsCm.get(player1).body.setActive(true);*/

        //this.game.loadMenuScene();

        // player 1
       if(!playerCm.get(player1).isDestroyed() && !playerCm.get(player1).getToDestroy()) {
            if(keyCode == Input.Keys.A || keyCode == Input.Keys.D){ // player 1
                animation1.setAnimationName("zurbBLUE_run");
            }

            if(keyCode  == Input.Keys.SHIFT_LEFT || keyCode == Input.Keys.ALT_LEFT )
                LaserFactory(player1);
       }

        // player 2
        if(!playerCm.get(player2).isDestroyed() && !playerCm.get(player2).getToDestroy()) {
            if(keyCode == Input.Keys.LEFT || keyCode == Input.Keys.RIGHT){ // player 2
                animation2.setAnimationName("zurbRED_run");
            }

            if(keyCode  == Input.Keys.SHIFT_RIGHT || keyCode == Input.Keys.CONTROL_RIGHT )
                LaserFactory(player2);
       }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCode == Input.Keys.A || keyCode == Input.Keys.D){ // player 1
            animation1.setAnimationName("zurbBLUE_idle");
        }

        if(keyCode == Input.Keys.LEFT || keyCode == Input.Keys.RIGHT){ // player 2
            animation2.setAnimationName("zurbRED_idle");
        }
        return false;
    }

    @Override
    protected void processSystem() {
        super.processSystem();
        if(winnerCm.get(winner).isWinner){
            winnerCm.get(winner).isWinner = false;
            this.game.state = ZurbGame.State.winner;
        }
        if(this.game.state == ZurbGame.State.doneWinning){
            this.game.state = ZurbGame.State.play;
            PauseGame();}
    }

    private void PauseGame() {
        Entity spriteEnt;
        spriteEnt = idManager.get("PauseMenu");

        if(this.game.getState() == ZurbGame.State.play) {
            transCM.get(spriteEnt).setPosition(4.375f, 2.25f); // place pause menu WHY WON'T YOU WORK??!!!!
            this.game.setState(ZurbGame.State.pausing);
        }
        else {
            this.game.setState(ZurbGame.State.play);
            transCM.get(spriteEnt).setPosition(1600f, 900f); // remove pause menu
        }
    }

    public void LaserFactory(Entity player) {
        float laserVelocity = 0.50f;
        float originX = transCM.get(player).getX();
        float originY = transCM.get(player).getY();
        String color = playerCm.get(player).getSpriteColor();
        boolean facingRight = playerCm.get(player).isFacingRight();

        if (facingRight) {
            originX += .6f;
            originY += .25f;
        } else {
            originX += -.06f;
            originY += .25f;
            //     laserVelocity = -laserVelocity;
        }

        if (color == "zurbBLUE")
            laser = idManager.get("Player1_laser");
        else if (color == "zurbRED")
            laser = idManager.get("Player2_laser");
        else if (color == "zurbGREEN")
            laser = idManager.get("Player3_laser");
        else if (color == "zurbPURPLE")
            laser = idManager.get("Player4_laser");

        /*
//  MyNewLaserFactory
        int newLaser = world.create(laserArchetype);
        Transform newLaserTrans = transCM.get(newLaser);
        VisPolygon newVisPolygon = visPolyCm.get(newLaser);
        VisSprite newVisSprite = spriteCm.get(newLaser);
        newVisSprite = spriteCm.get(laser);
        newVisPolygon = visPolyCm.get(laser);
        //newLaserTrans.setPosition(originX, originY);
        //PhysicsBody newLaserbody = physicsCm.get(newLaser);
        //newLaserbody = new PhysicsBody(physicsCm.get(laser).body);
        //world.edit(newLaser).add(newLaserbody);
        //  myNewLaserFactory
*/
        FixtureDef fdefLaser = new FixtureDef();
       /* PolygonShape laserShape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(.19f, .43f);
        vertice[1] = new Vector2(.42f, .43f);
        vertice[2] = new Vector2(.42f, .35f);
        vertice[3] = new Vector2(.19f, .35f);
        laserShape.set(vertice);
        fdefLaser.shape = laserShape;*/
        fdefLaser.isSensor = true;

        BodyDef laserBodyDef = new BodyDef();
        laserBodyDef.type = BodyDef.BodyType.DynamicBody;
        //laserBodyDef.position.set(5, 5);
        laserBodyDef.gravityScale = 0.0f;

        if (facingRight) {
            laserBodyDef.linearVelocity.set(3.0f, 0.0f);
        } else {
            laserBodyDef.linearVelocity.set(-3.0f, 0.0f);
        }

        if (color == "zurbRED")
            fdefLaser.filter.categoryBits = GameSceneManager.PLAYER02_LASER_BIT;
        else if(color == "zurbBLUE")
            fdefLaser.filter.categoryBits = GameSceneManager.PLAYER01_LASER_BIT;
        else if(color == "zurbGREEN")
            fdefLaser.filter.categoryBits = GameSceneManager.PLAYER03_LASER_BIT;
        else if(color == "zurbPURPLE")
            fdefLaser.filter.categoryBits = GameSceneManager.PLAYER04_LASER_BIT;


        // OldFactory
        EntityComposer ec = new EntityComposer(game.getScene());
        VisSprite laserSprite = spriteCm.get(laser);
        SpriteEntityComposer spriteComp = ec.sprite(laserSprite, -1.0f, -1.0f);
        Entity newLaser = spriteComp.finish();
        layerCm.get(newLaser).layerId = 4;

        OriginalRotation ogRotComp = ogRotCm.create(newLaser);
        ogRotComp.rotation = 0.0f;

        Laser laserComp = laserCm.create(newLaser);
        laserComp.whoShotId = idCm.get(player);
        velocityCm.create(newLaser).SetVelocity(laserVelocity, 0.0f);

        Body newLaserBody = physicsCm.get(laser).body.getWorld().createBody(laserBodyDef);
        Array<Fixture> fixArray = physicsCm.get(laser).body.getFixtureList();
        for (Fixture fixture : fixArray){
            fdefLaser.shape = fixture.getShape();
            newLaserBody.createFixture(fdefLaser).setUserData(newLaser);
        }
        newLaserBody.setUserData(newLaser);

        PhysicsBody newLaserPhysicsBody = new PhysicsBody(newLaserBody);


        //PhysicsBody newLaserPhysicsBody = new PhysicsBody(physicsCm.get(laser).body);

        //MassData massData = new MassData();
        //massData.mass = 1.0f;
        //newLaserPhysicsBody.body.setMassData(massData);


        newLaserPhysicsBody.body.setTransform(originX, originY, 0.0f);

        System.out.print("");

        world.edit(newLaser.getId()).add(newLaserPhysicsBody);


      //OldFactory

        //junk
        //physicsCm.create(newLaser);
        //physics = physicsCm.get(laser,true);
        //pBody.body.applyForceToCenter(20.0f, 0.0f, true);
      /*  int newLaser = world.create();
        renderCM.create(newLaser);
        laserCm.create(newLaser);
        spriteCm.create(newLaser);
        velocityCm.create(newLaser).SetVelocity(laserVelocity,0.0f);
        transCM.create(newLaser).setPosition(4.0f,5.0f);
        TextureRegion laserTextureRegion= spriteCm.get(laser).getRegion();
        spriteCm.get(newLaser).setRegion(laserTextureRegion);*/

       /* return newLaser;*/
    }

  /*  public void AddWallBits(){

        String wallName = "";
        Entity wall;

        for(int i = 1; i < 11; i ++){
            if(i < 10)
                wallName = "wall0"+i;
            else
                wallName = "wall"+i;

            wall = idManager.get(wallName);
            Array<Fixture> fixArray = physicsCm.get(wall).body.getFixtureList();
            for(Fixture fixture: fixArray)
                fixture.getFilterData().categoryBits = WALL_BIT;
        }

    }*/

}
