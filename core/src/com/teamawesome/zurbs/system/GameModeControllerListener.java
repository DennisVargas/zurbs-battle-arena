package com.teamawesome.zurbs.system;

import com.artemis.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.kotcrab.vis.runtime.component.VisSpriteAnimation;
import com.teamawesome.zurbs.component.Player;
import com.teamawesome.zurbs.manager.GameSceneManager;

/**
 * Created by ben on 11/27/16.
 */
public class GameModeControllerListener implements ControllerListener {
    Entity player;
    GameSceneManager gameSceneManager;

    public GameModeControllerListener(Entity id, GameSceneManager gm) {
        this.player = id;
        this.gameSceneManager = gm;
    }

    @Override
    public void connected(Controller controller) {
        System.out.println("Controller: '" + controller.getName() + "' connected!");
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Controller: '" + controller.getName() + "' disconnected!");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(buttonCode == NextController.BUTTON_Y || buttonCode == NextController.BUTTON_A)
            gameSceneManager.LaserFactory(player);
        else if(buttonCode == NextController.BUTTON_SELECT)
            gameSceneManager.game.loadMenuScene();

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        //We could implement here the mechanics to jump applying force when jumping.
        //eg, smaller jumps

        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        VisSpriteAnimation animation = player.getComponent(VisSpriteAnimation.class);
        String color = player.getComponent(Player.class).getSpriteColor();
        if(axisCode == NextController.AXIS_X) {
            if(value < -NextController.STICK_DEADZONE || value > NextController.STICK_DEADZONE) {
                animation.setAnimationName(color + "_run");
            }
            else {
                animation.setAnimationName(color + "_idle");
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
