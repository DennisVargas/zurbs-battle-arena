package com.teamawesome.zurbs.manager;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.*;


public class MenuSceneManager extends BaseSceneManager {
//	private Bounds playText;
//	private Bounds optionsText;
//	private Bounds creditsText;
//	private Bounds quitText;
	private Bounds playButton;
	private Bounds optionsButton;
	private Bounds creditsButton;
	private Bounds quitButton;
	enum Selections {Start, Options, Credits, Quit};
	Selections selection = Selections.Start;
	public MenuSceneManager (ZurbGame game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		playButton = getSpriteBounds("StartGame");
		optionsButton = getSpriteBounds("Options");
		creditsButton = getSpriteBounds("Credits");
		quitButton = getSpriteBounds("QuitGame");
		SwapPNG("StartGame","StartSelect");

	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (playButton.contains(x, y)) {
			soundController.playClick();
			game.loadPlayerSelectScene();
		}
		
		if (optionsButton.contains(x, y)) {
			soundController.playClick();
			game.loadOptionsScene();
		}

		if (creditsButton.contains(x, y)) {
			soundController.playClick();
			game.loadCreditsScene();
		}

		if (quitButton.contains(x, y)) {
			soundController.playClick();
			Gdx.app.exit();
		}

		return false;
	}
	@Override
	public boolean keyDown(int keyCode){
		System.out.println(keyCode);
		if(keyCode == 20){
			if(selection == Selections.Start){
				SwapPNG("StartSelect","StartGame");
				SwapPNG("Options", "OptionsSelect");
				selection = Selections.Options;
			}
			else if(selection == Selections.Options){
				SwapPNG("OptionsSelect","Options");
				SwapPNG("Credits","CreditsSelect");
				selection = Selections.Credits;
			}
			else if(selection == Selections.Credits){
				SwapPNG("CreditsSelect","Credits");
				SwapPNG("QuitGame","QuitGameSelect");
				selection = Selections.Quit;
			}
			else if(selection == Selections.Quit){
				SwapPNG("QuitGameSelect","QuitGame");
				SwapPNG("StartGame","StartSelect");
				selection = Selections.Start;
			}

		}
		if(keyCode == 19){
			if(selection == Selections.Start){
				SwapPNG("StartSelect","StartGame");
				SwapPNG("QuitGame", "QuitGameSelect");
				selection = Selections.Quit;
			}
			else if(selection == Selections.Options){
				SwapPNG("OptionsSelect","Options");
				SwapPNG("StartGame","StartSelect");
				selection = Selections.Start;
			}
			else if(selection == Selections.Credits){
				SwapPNG("CreditsSelect","Credits");
				SwapPNG("Options", "OptionsSelect");
				selection = Selections.Options;
			}
			else if(selection == Selections.Quit){
				SwapPNG("QuitGameSelect","QuitGame");
				SwapPNG("CreditsSelect","Credits");
				selection = Selections.Credits;
			}

		}


		return false;
	}

	
}