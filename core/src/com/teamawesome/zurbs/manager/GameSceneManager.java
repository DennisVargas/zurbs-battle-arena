package com.teamawesome.zurbs.manager;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.system.render.SpriteAnimationUpdateSystem;
import com.kotcrab.vis.runtime.util.entity.composer.EntityComposer;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.manager.BaseSceneManager;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager {

    private ComponentMapper<VisSpriteAnimation> animationCM;
    //private ComponentMapper<VisSprite> spriteCM;
    private VisSpriteAnimation animation1, animation2;
    private Entity player1, player2;
    private Array<Entity> lasers;
    EntityComposer ec;
    //private VisSprite sprite1, sprite2;


    public GameSceneManager(ZurbGame game) {
        super(game);

    }


    @Override
    public void afterSceneInit() {
        super.afterSceneInit();

        player1 = idManager.get("Player01");
        player2 = idManager.get("Player02");
        lasers = idManager.getMultiple("Player1_laser");
        animation1 = animationCM.get(player1);
        animation2 = animationCM.get(player2);

        //sprite1 = spriteCM.get(player1);
        //sprite2 = spriteCM.get(player2);
    }

    @Override
    public boolean keyDown(int keyCode){
        ec = new EntityComposer(game.getScene());
        System.out.println(keyCode);
        if(keyCode == 59)
            ec.sprite(lasers.get(0).getComponent(VisSprite.class),4, 5);

        if (keyCode == 131)
            this.game.loadMenuScene();

        if(keyCode == 29){ // player 1
            animation1.setAnimationName("zurbBLUE_run");
            //sprite1.setFlip(false, false);
        }
        if(keyCode == 32){ // player 1
            animation1.setAnimationName("zurbBLUE_run");
            //sprite1.setFlip(true, false);
        }

        if(keyCode == 21 || keyCode == 22){ // player 2
            animation2.setAnimationName("zurbRED_run");
            //sprite2.setFlip(false, false);
        }

        /*
         if(keyCode == 21)
            MoveXPNG("Player02", - 0.25f);
        */

        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCode == 29){ // player 1
            animation1.setAnimationName("zurbBLUE_idle");
            //sprite1.setFlip(true, false);
        }
        if(keyCode == 32){ // player 1
            animation1.setAnimationName("zurbBLUE_idle");
            //sprite1.setFlip(false, false);
        }
        if(keyCode == 21 || keyCode == 22){ // player 2
            animation2.setAnimationName("zurbRED_idle");
            //sprite2.setFlip(false, false);
        }
        return false;
    }

  /*  @Override
    public boolean axisMoved(Controller controller, int axisCode, float value){

    }*/



}
