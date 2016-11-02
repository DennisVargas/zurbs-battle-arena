package com.teamawesome.zurbs.manager;

import com.artemis.ComponentMapper;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.VisText;


public class OptionsSceneManager extends BaseSceneManager {
	private ComponentMapper<VisText> soundLevel;
	private ComponentMapper<VisText> musicLevel;
	private boolean fullscreen;
	
	private Bounds soundUPSprite;
	private Bounds soundDownSprite;
	private Bounds musicUPSprite;
	private Bounds musicDownSprite;
	private Bounds fullscreenSprite;
	private Bounds doneText;
	

	public OptionsSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();


		soundUPSprite = getSpriteBounds("soundUP");
		soundDownSprite = getSpriteBounds("soundDown");
		musicUPSprite = getSpriteBounds("musicUP");
		musicDownSprite = getSpriteBounds("musicDown");
		//fullscreenSprite = getSpriteBounds("fullscreen");
		doneText = getSpriteBounds("doneText");
		
		
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;
		

		if (soundUPSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadGameScene();
		}
		
		if (soundDownSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadOptionsScene();
		}

		if (musicUPSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadHelpScene();
		}
		
		if (musicDownSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadHelpScene();
		}
		
		/*
		if (fullscreenSprite.contains(x, y)) {
			soundController.playClick();
			//game.loadHelpScene();
		}
		*/
		

		if (doneText.contains(x, y)) {
			soundController.playClick();
			//Save option changes
			
			game.loadMenuScene();
		}


		return false;
	}


}