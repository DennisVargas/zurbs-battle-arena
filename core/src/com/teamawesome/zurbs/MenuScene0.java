package com.teamawesome.zurbs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.runtime.font.FreeTypeFontProvider;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.VisAssetManager;


public class MenuScene0 extends ApplicationAdapter {
	SpriteBatch batch;
	VisAssetManager manager;

	Scene scene;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new VisAssetManager(batch);
		manager.enableFreeType(new FreeTypeFontProvider());
		scene = manager.loadSceneNow("scene/menu00.scene");
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scene.render();
	}

	@Override
	public void resize(int width, int height) {

		scene.resize(width, height);
	}

	@Override
	public void dispose(){
		batch.dispose();
		manager.dispose();
	}
}
