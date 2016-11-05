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
import com.teamawesome.zurbs.manager.*;
import com.teamawesome.zurbs.system.*;

//	========
//	ZurbGame
//	==================================================
	public class ZurbGame extends ApplicationAdapter {
		SpriteBatch batch;
		VisAssetManager manager;
		SoundController soundController;

		String scenePath;
		Scene scene;

		@Override
//		======
//		create
//		=====================
		public void create(){
			batch = new SpriteBatch();
			manager = new VisAssetManager(batch);
			manager.getLogger().setLevel(Logger.ERROR);

			manager.enableFreeType(new FreeTypeFontProvider());
			soundController = new SoundController(manager);

			loadMenuScene();
		}//	create()
//		============

//		===============
//		loadMenuScene()
//		============================
		public void loadMenuScene(){
			unloadPreviousScene();

			SceneParameter parameter = new SceneParameter();
			parameter.config.addSystem(BoundsCreator.class);
			parameter.config.addSystem(BoundsUpdater.class);

			parameter.config.addSystem(new SystemProvider() {
				public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
					return new MenuSceneManager(ZurbGame.this);
				}
			});

			scenePath = "scene/menu00.scene";
			scene = manager.loadSceneNow(scenePath, parameter);
		}//	loadMenuScene()
//		===================

//		==================
//		loadOptionsScene()
//		===============================
		public void loadOptionsScene(){
			unloadPreviousScene();

			SceneParameter parameter = new SceneParameter();
			parameter.config.addSystem(BoundsCreator.class);
			parameter.config.addSystem(BoundsUpdater.class);
			parameter.config.addSystem(new SystemProvider() {
				public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
					return new OptionsSceneManager(ZurbGame.this);
				}
			});

			scenePath = "scene/optionsMenu.scene";
			scene = manager.loadSceneNow(scenePath, parameter);
		}//	loadOptionsScene()
//		======================


//		==================
//		loadStartGameScene()
//		====================================
		public void loadPlayerSelectScene(){
			unloadPreviousScene();

			SceneParameter parameter = new SceneParameter();
			parameter.config.addSystem(BoundsCreator.class);
			parameter.config.addSystem(BoundsUpdater.class);
			parameter.config.addSystem(new SystemProvider() {
				public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
					return new PlayerSelectSceneManager(ZurbGame.this);
				}
			});

			scenePath = "scene/playerSelect.scene";	//rename
			scene = manager.loadSceneNow(scenePath, parameter);
		}//	loadStartGameScene()
// 		========================


//		==================
//		loadCreditsScene()
//		===============================
		public void loadCreditsScene(){
			unloadPreviousScene();
			SceneParameter parameter = new SceneParameter();
			parameter.config.addSystem(BoundsCreator.class);
			parameter.config.addSystem(BoundsUpdater.class);
			parameter.config.addSystem(new SystemProvider() {
				public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
					return new CreditsSceneManager(ZurbGame.this);
				}
			});

			scenePath = "scene/creditsScene.scene";	//rename
			scene = manager.loadSceneNow(scenePath, parameter);
		}//	loadCreditsScene()
// 		======================


//		=====================
//		unloadPreviousScene()
//		===================================
		private void unloadPreviousScene(){
			if (scenePath != null) {
				manager.unload(scenePath);
				scenePath = null;
				scene = null;
			}
		}//	unloadPreviousScene()
//		=========================

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

		public SoundController getSoundController () {
		return soundController;
	}
	}//	ZurbGame()
//	==============