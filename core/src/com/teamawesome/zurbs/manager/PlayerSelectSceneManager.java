package com.teamawesome.zurbs.manager;

import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.VisText;

public class PlayerSelectSceneManager extends BaseSceneManager {
    private Bounds backText;
    private Bounds startText;
    private Bounds p1Inner;


    public PlayerSelectSceneManager (ZurbGame game) {
        super(game);
    }

    @Override
    public void afterSceneInit () {
        super.afterSceneInit();

        backText = getSpriteBounds("backButton");
        startText = getSpriteBounds("startButton");
        p1Inner = getSpriteBounds("player1Inner");

    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        unprojectVec.set(screenX, screenY, 0);
        cameraManager.getCamera().unproject(unprojectVec);

        float x = unprojectVec.x;
        float y = unprojectVec.y;

        if (backText.contains(x, y)) {
            soundController.playClick();
            game.loadMenuScene();
        }

        if (startText.contains(x, y)) {
            soundController.playClick();
            game.loadStartGameScene();
        }


        if (p1Inner.contains(x, y)) {
            //soundController.playClick();
            System.out.println("p1Inner Clicked!");
        }


/*
Need to implement player box changes on click
 */

        return false;
    }
}


