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
	private VisText volumeText;


	public OptionsSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();
		UpdateVolumeText("music");
		UpdateVolumeText("fx");
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
			soundController.ToggleFxVolumeUp();
			UpdateVolumeText("fx");
			soundController.playClick();
			//game.loadGameScene();
		}
		
		if (soundDownSprite.contains(x, y)) {
			soundController.ToggleFxVolumeDown();
			UpdateVolumeText("fx");
			soundController.playClick();
			//game.loadOptionsScene();
		}

		if (musicUPSprite.contains(x, y)) {
			soundController.ToggleMusicVolumeUp();
			UpdateVolumeText("music");
			soundController.playClick();
			//game.loadHelpScene();
		}
		
		if (musicDownSprite.contains(x, y)) {
			soundController.ToggleMusicVolumeDown();
			UpdateVolumeText("music");
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

	private void UpdateVolumeText(String volume){
		if(volume.equals("music")){
			volumeText = textCm.get(idManager.get("musicLevel"));
			float normLevel = soundController.GetMusicVol()*100f;
			Integer level = (int)normLevel;
			volumeText.setText(level.toString());
		}
		else if (volume.equals("fx")){
			volumeText = textCm.get(idManager.get("soundLevel"));
			float normLevel = soundController.GetFxVol()*100f;
			Integer level = (int)normLevel;
			volumeText.setText(level.toString());
		}
	}

}