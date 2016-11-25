package com.teamawesome.zurbs.manager;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.Renderable;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.system.render.SpriteAnimationUpdateSystem;
import com.kotcrab.vis.runtime.util.entity.composer.EntityComposer;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Laser;
import com.teamawesome.zurbs.component.Velocity;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager {
    ComponentMapper<Renderable> renderCM;
    ComponentMapper <Laser> laserCm;
    ComponentMapper <Velocity> velocityCm;
    private ComponentMapper<VisSpriteAnimation> animationCM;
    ComponentMapper<Transform> transCM;
    private VisSpriteAnimation animation1, animation2;
    private Entity player1, player2,laser;
    EntityComposer ec;

    private float laserVelocity = 15.0f;
    private float laserDeltaX = 0.1f;
    public GameSceneManager(ZurbGame game) {
        super(game);

    }


    @Override
    public void afterSceneInit() {
        super.afterSceneInit();

        player1 = idManager.get("Player01");
        player2 = idManager.get("Player02");
        laser = idManager.get("Player1_laser");
        animation1 = animationCM.get(player1);
        animation2 = animationCM.get(player2);
    }

    @Override
    public boolean keyDown(int keyCode){

        System.out.println(keyCode);
        if(keyCode == 59){
            LaserFactory();
            System.out.println();
        }
            //ec.sprite(laser.getComponent(VisSprite.class),4, 5);

        if (keyCode == 131) // ESC
            this.game.loadMenuScene();

        if(keyCode == 29 || keyCode == 32){ // player 1
            animation1.setAnimationName("zurbBLUE_run");
        }

        if(keyCode == 21 || keyCode == 22){ // player 2
            animation2.setAnimationName("zurbRED_run");
        }

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
    public void LaserFactory(){
        laserDeltaX += 0.1f;
        EntityComposer ec = new EntityComposer(game.getScene());
        VisSprite laserSprite = spriteCm.get(laser);
        ec.sprite(laserSprite, 4.0f+laserDeltaX, 5.0f);

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
