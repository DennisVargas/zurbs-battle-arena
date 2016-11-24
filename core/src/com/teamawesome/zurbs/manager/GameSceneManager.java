package com.teamawesome.zurbs.manager;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.system.render.SpriteAnimationUpdateSystem;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.manager.BaseSceneManager;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager {

    private ComponentMapper<VisSpriteAnimation> animationCM;
    private VisSpriteAnimation animation;
    private Entity player1;
    public GameSceneManager(ZurbGame game) {
        super(game);
    }

    @Override
    public void afterSceneInit() {
        super.afterSceneInit();
        player1 = idManager.get("Player01");
        animation = animationCM.get(player1);


    }

    @Override
    public boolean keyDown(int keyCode){
        System.out.println(keyCode);
        if (keyCode == 131)
            this.game.loadMenuScene();
        if(keyCode == 29){
            System.out.println("Left");
            animation.setAnimationName("zurbBLUE_run");
        }



        /*
         if(keyCode == 21)
            MoveXPNG("Player02", - 0.25f);
        */

        return false;
    }

  /*  @Override
    public boolean axisMoved(Controller controller, int axisCode, float value){

    }*/



}
