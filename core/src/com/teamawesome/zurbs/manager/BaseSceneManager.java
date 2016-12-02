package com.teamawesome.zurbs.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisText;
import com.kotcrab.vis.runtime.scene.Scene;
import com.teamawesome.zurbs.SoundController;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

public abstract class BaseSceneManager extends Manager implements InputProcessor,  AfterSceneInit {
	protected ComponentMapper<Bounds> boundsCm;
	protected ComponentMapper<Transform> transformCm;
	protected ComponentMapper<VisSprite> spriteCm;
	protected ComponentMapper<VisText> textCm;
	protected ComponentMapper<PhysicsBody> physicsCm;
	public ZurbGame game;
	protected SoundController soundController;

	protected CameraManager cameraManager;
	protected VisIDManager idManager;

	protected Vector3 unprojectVec = new Vector3();


	public BaseSceneManager (ZurbGame game) {
		this.game = game;
		this.soundController = game.getSoundController();

	}

	protected Bounds getSpriteBounds (String id) {
		Entity entity = idManager.get(id);
		return boundsCm.get(entity);
	}

	public void SwapPNG(String id1, String id2){
		float x, y;
		Entity entity1 = idManager.get(id1);
		Entity entity2 = idManager.get(id2);

		Transform trans1 = transformCm.get(entity1);
		Transform trans2 = transformCm.get(entity2);

		x = trans1.getX();
		y = trans1.getY();

		trans1.setPosition(trans2.getX(),trans2.getY());
		trans2.setPosition(x, y);
	}

	protected void MoveXPNG(String id, float displacement){
		Entity entity = idManager.get(id);
		Transform trans = transformCm.get(entity);

		trans.setPosition(trans.getX()+displacement, trans.getY());

	}

	protected void MoveYPNG(String id, float displacement){
		Entity entity = idManager.get(id);
		Transform trans = transformCm.get(entity);

		trans.setPosition(trans.getX(),trans.getY()+displacement);
	}


	@Override
	public void afterSceneInit () {
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public boolean keyDown (int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

}