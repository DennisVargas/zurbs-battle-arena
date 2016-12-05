package com.teamawesome.zurbs.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.teamawesome.zurbs.system.NextController;


public class MenuSceneManager extends BaseSceneManager implements ControllerListener {
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

		for(Controller con : game.controllers)
			con.addListener(this);

		game.controllerListeners.add(this);

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
		if(keyCode == Input.Keys.DOWN){
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
		if(keyCode == Input.Keys.UP){
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
		if(keyCode == Input.Keys.SPACE || keyCode == Input.Keys.ENTER ){
			soundController.playClick();

			switch(selection) {
				case Start:
					game.loadStartGameScene();
					break;
				case Options:
					game.loadOptionsScene();
					break;
				case Credits:
					game.loadCreditsScene();
					break;
				case Quit:
					Gdx.app.exit();
					break;
			}

		}



		return false;
	}

	@Override
	public void connected(Controller controller) {

	}

	@Override
	public void disconnected(Controller controller) {

	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		if(buttonCode == NextController.BUTTON_B) {

			soundController.playClick();

			switch(selection) {
				case Start:
					game.loadStartGameScene();
					break;
				case Options:
					game.loadOptionsScene();
					break;
				case Credits:
					game.loadCreditsScene();
					break;
				case Quit:
					Gdx.app.exit();
					break;
			}

		}


		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		if(axisCode == NextController.AXIS_Y) {
			if(value > NextController.STICK_DEADZONE) {
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
			else if(value < -NextController.STICK_DEADZONE) {
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
		}

		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}
	
}