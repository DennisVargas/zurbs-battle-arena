package com.teamawesome.zurbs.manager;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.Transform;


public class MenuSceneManager extends BaseSceneManager {
	private Bounds playSprite;
	private Bounds helpSprite;
	private Bounds quitSprite;
	

	public MenuSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();


		playSprite = getSpriteBounds("StartGame");
		helpSprite = getSpriteBounds("Credits");
		quitSprite = getSpriteBounds("QuitGame");
		
		soundController.setSoundEnabled(true);
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;
		

		if (playSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadGameScene();
		}

		if (helpSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadHelpScene();
		}

		if (quitSprite.contains(x, y)) {
			soundController.playClick();
			Gdx.app.exit();
		}


		return false;
	}


}