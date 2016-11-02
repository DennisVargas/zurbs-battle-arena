package com.teamawesome.zurbs;

import com.artemis.BaseSystem;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.kotcrab.vis.runtime.RuntimeContext;
import com.kotcrab.vis.runtime.data.SceneData;
import com.kotcrab.vis.runtime.font.FreeTypeFontProvider;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.SceneLoader.SceneParameter;
import com.kotcrab.vis.runtime.scene.SystemProvider;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.util.EntityEngineConfiguration;
import com.teamawesome.zurbs.manager.MenuSceneManager;
import com.teamawesome.zurbs.system.TextBoundsCreator;
import com.teamawesome.zurbs.system.TextBoundsUpdater;

public class ZurbGame extends ApplicationAdapter {
	SpriteBatch batch;
	VisAssetManager manager;
	SoundController soundController;
	
	String scenePath;
	Scene scene;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		
		manager = new VisAssetManager(batch);
		manager.getLogger().setLevel(Logger.ERROR);

		manager.enableFreeType(new FreeTypeFontProvider());
		soundController = new SoundController(manager);

		loadMenuScene();
	}
	

	public SoundController getSoundController () {
		return soundController;
	}
	
	public void loadMenuScene () {
		unloadPreviousScene();

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(TextBoundsCreator.class);
		parameter.config.addSystem(TextBoundsUpdater.class);
		parameter.config.addSystem(new SystemProvider() {
			public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
				return new MenuSceneManager(ZurbGame.this);
			}
		});

		scenePath = "scene/menu00.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
	}
	
	

	private void unloadPreviousScene () {
		if (scenePath != null) {
			manager.unload(scenePath);
			scenePath = null;
			scene = null;
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scene.render();
	}

	@Override
	public void resize (int width, int height) {
		scene.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
}
}
