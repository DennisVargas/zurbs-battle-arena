package com.teamawesome.zurbs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.runtime.scene.Scene;
import com.sun.prism.image.ViewPort;

public class ZurbGameScene extends ApplicationAdapter {
	Stage stage;
	
	@Override
	public void create () {
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		ZurbActor zurb0 = new ZurbActor();
		stage.addActor(zurb0);
		stage.setKeyboardFocus(zurb0);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());

		stage.draw();
	}
}
