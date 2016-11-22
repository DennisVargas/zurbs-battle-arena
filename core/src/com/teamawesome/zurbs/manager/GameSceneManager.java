package com.teamawesome.zurbs.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.controllers.Controller;
import com.kotcrab.vis.runtime.system.render.SpriteAnimationUpdateSystem;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.manager.BaseSceneManager;

/**
 * Created by Dennis on 11/16/2016.
 */
public class GameSceneManager extends BaseSceneManager {
    public GameSceneManager(ZurbGame game) {
        super(game);
    }

    @Override
    public void afterSceneInit() {
        super.afterSceneInit();
        SpriteAnimationUpdateSystem s;
    }

    @Override
    public boolean keyDown(int keyCode){

        if (keyCode == 131)
            this.game.loadMenuScene();

        if(keyCode == 21)
            MoveXPNG("Player00", - 0.25f);

        return false;
    }

  /*  @Override
    public boolean axisMoved(Controller controller, int axisCode, float value){

    }*/



}
