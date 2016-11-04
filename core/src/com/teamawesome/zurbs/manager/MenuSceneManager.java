package com.teamawesome.zurbs.manager;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.Transform;


public class MenuSceneManager extends BaseSceneManager {
	private Bounds playText;
	private Bounds optionsText;
	private Bounds creditsText;
	private Bounds quitText;

	public MenuSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		playText = getSpriteBounds("StartGame");
		optionsText = getSpriteBounds("Options");
		creditsText = getSpriteBounds("Credits");
		quitText = getSpriteBounds("QuitGame");
		
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (playText.contains(x, y)) {
			soundController.playClick();
			//game.loadGameScene();
		}
		
		if (optionsText.contains(x, y)) {
			soundController.playClick();
			game.loadOptionsScene();
		}

		if (creditsText.contains(x, y)) {
			soundController.playClick();
			//game.loadHelpScene();
		}

		if (quitText.contains(x, y)) {
			soundController.playClick();
			Gdx.app.exit();
		}

		return false;
	}
}