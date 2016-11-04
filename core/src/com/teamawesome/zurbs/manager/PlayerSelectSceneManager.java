package com.teamawesome.zurbs.manager;

import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.VisText;

public class PlayerSelectSceneManager extends BaseSceneManager {
    private Bounds backText;

    public PlayerSelectSceneManager (ZurbGame game) {
        super(game);
    }

    @Override
    public void afterSceneInit () {
        super.afterSceneInit();

        backText = getSpriteBounds("backButton");

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

        return false;
    }
}


