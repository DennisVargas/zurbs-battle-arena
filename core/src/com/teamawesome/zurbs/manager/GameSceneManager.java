package com.teamawesome.zurbs.manager;

import com.artemis.*;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.*;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.system.render.SpriteAnimationUpdateSystem;
import com.kotcrab.vis.runtime.util.entity.composer.EntityComposer;
import com.kotcrab.vis.runtime.util.entity.composer.SpriteEntityComposer;
import com.teamawesome.zurbs.WorldContactListener;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Laser;
import com.teamawesome.zurbs.component.Player;
import com.teamawesome.zurbs.component.Velocity;
import com.teamawesome.zurbs.WorldContactListener;
import com.teamawesome.zurbs.system.GameModeControllerListener;
import javafx.geometry.Pos;

import java.util.IdentityHashMap;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager {
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

    private VisSpriteAnimation animation1, animation2;
    private Entity player1, player2, laser;

    Archetype laserArchetype;

    //Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short WALL_BIT = 1;
    public static final short PLAYER01_BIT = 2;
    public static final short PLAYER01_HEAD_BIT = 4;
    public static final short PLAYER01_LASER_BIT = 8;
    public static final short PLAYER02_BIT = 16;
    public static final short PLAYER02_HEAD_BIT = 32;
    public static final short PLAYER02_LASER_BIT = 64;
    //public static final short x1_BIT = 32;
    //public static final short x2_BIT = 64;
    //public static final short x3_BIT = 128;
    //public static final short x4_BIT = 256;
    //public static final short MARIO_HEAD_BIT = 512;
    //public static final short FIREBALL_BIT = 1024;


    public GameSceneManager(ZurbGame game) {
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
        player1 = idManager.get("Player01");
        player2 = idManager.get("Player02");

        animation1 = animationCM.get(player1);
        animation2 = animationCM.get(player2);

        physicsCm.get(player1).body.getWorld().setContactListener(new WorldContactListener());

        Array<Controller> controllers = Controllers.getControllers();

        if(controllers.size > 0)
            controllers.get(0).addListener(new GameModeControllerListener(player1, this));

    }


    @Override
    public boolean keyDown(int keyCode){
        if (keyCode == 131) // ESC
            this.game.loadMenuScene();

        // player 1
//       if(!playerCm.get(player1).isDestroyed() && !playerCm.get(player1).getToDestroy()) {
            if(keyCode == 29 || keyCode == 32){ // player 1
                animation1.setAnimationName("zurbBLUE_run");
            }
           System.out.println(keyCode);
           if(keyCode == 59){
               LaserFactory(player1);
               System.out.println("BLUE fired");
           }
//       }

        // player 2
//        if(!playerCm.get(player2).isDestroyed() && !playerCm.get(player2).getToDestroy()) {
            if(keyCode == 21 || keyCode == 22){ // player 2
                animation2.setAnimationName("zurbRED_run");
            }
            if(keyCode == 60){
                LaserFactory(player2);
                System.out.println("RED fired");
            }
//       }

        /*
         if(keyCode == 21)
            MoveXPNG("Player02", - 0.25f);
        */

        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCode == 29 || keyCode == 32){ // player 1
            animation1.setAnimationName("zurbBLUE_idle");
        }

        if(keyCode == 21 || keyCode == 22){ // player 2
            animation2.setAnimationName("zurbRED_idle");
        }
        return false;
    }

  /*  @Override
    public boolean axisMoved(Controller controller, int axisCode, float value){

    }*/

    public void LaserFactory(Entity player){
        float laserVelocity = 0.05f;
        float originX = transCM.get(player).getX();
        float originY = transCM.get(player).getY();
        String color = playerCm.get(player).getSpriteColor();
        boolean facingRight = playerCm.get(player).isFacingRight();

        if (facingRight) {
            originX += .3f;
            originY += .25f;
        }  else {
            originX += -.03f;
            originY += .25f;
            laserVelocity = -laserVelocity;
        }

        if (color == "zurbBLUE")
            laser = idManager.get("Player1_laser");
        else if (color == "zurbRED")
            laser = idManager.get("Player2_laser");
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
        PolygonShape laserShape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(.19f, .43f);
        vertice[1] = new Vector2(.42f, .43f);
        vertice[2] = new Vector2(.42f, .35f);
        vertice[3] = new Vector2(.19f, .35f);
        laserShape.set(vertice);
        fdefLaser.shape = laserShape;

        BodyDef laserBodyDef = new BodyDef();
        laserBodyDef.type = BodyDef.BodyType.DynamicBody;
        laserBodyDef.position.set(5, 5);
        laserBodyDef.gravityScale = 0.0f;


        fdefLaser.filter.categoryBits = GameSceneManager.PLAYER01_LASER_BIT;

        // OldFactory
        EntityComposer ec = new EntityComposer(game.getScene());
        VisSprite laserSprite = spriteCm.get(laser);
        SpriteEntityComposer spriteComp = ec.sprite(laserSprite, -1.0f, -1.0f);
        Entity newLaser = spriteComp.finish();

        OriginalRotation ogRotComp = ogRotCm.create(newLaser);
        ogRotComp.rotation = 0.0f;

        Laser laserComp = laserCm.create(newLaser);
        laserComp.whoShotId = idCm.get(player);
        velocityCm.create(newLaser).SetVelocity(laserVelocity,0.0f);

        Body newLaserBody = physicsCm.get(laser).body.getWorld().createBody(laserBodyDef);
        Array<Fixture> fixArray = physicsCm.get(laser).body.getFixtureList();
        fdefLaser.shape = fixArray.get(0).getShape();
        newLaserBody.createFixture(fdefLaser);

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

}
