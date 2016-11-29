package com.teamawesome.zurbs.manager;

import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;


public class CreditsSceneManager extends BaseSceneManager {
	private Bounds backText;
	

	public CreditsSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();
		backText = getSpriteBounds("backText");
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;
		

		if (backText.contains(x, y)) {
			soundController.playClick();
			//Return to main menu
			
			game.loadMenuScene();
		}


		return false;
	}


}
