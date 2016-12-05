package com.teamawesome.zurbs.system;

import com.badlogic.gdx.Input;
import com.kennycason.gdx.controller.KeyboardController;
import com.kennycason.gdx.controller.LogitechController;
import com.kennycason.gdx.controller.MultiplexedController;
import com.kennycason.gdx.controller.mapping.Axis;
import com.kennycason.gdx.controller.mapping.AxisMapper;
import com.kennycason.gdx.controller.mapping.ButtonMapper;

/**
 * Created by powerpup on 12/5/16.
 */
public class ControllerFactory {

    public static MultiplexedController<GameControls> buildMultiController(String player, int conNum) {
        return new MultiplexedController<>(buildKeyboard(player), buildLogitech(conNum));
    }

    public static KeyboardController<GameControls> buildKeyboard(String player) {
        final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();
        if(player == "Player01") {
            buttonMapper.map(GameControls.DPAD_UP, Input.Keys.W);
            buttonMapper.map(GameControls.DPAD_DOWN, Input.Keys.S);
            buttonMapper.map(GameControls.DPAD_LEFT, Input.Keys.A);
            buttonMapper.map(GameControls.DPAD_RIGHT, Input.Keys.D);

            buttonMapper.map(GameControls.START, Input.Keys.F);
            buttonMapper.map(GameControls.SELECT, Input.Keys.TAB);

            buttonMapper.map(GameControls.A, Input.Keys.Z);
            buttonMapper.map(GameControls.B, Input.Keys.SPACE);
            buttonMapper.map(GameControls.X, Input.Keys.Q);
            buttonMapper.map(GameControls.Y, Input.Keys.SHIFT_LEFT);

            buttonMapper.map(GameControls.L1, Input.Keys.NUM_1);
            buttonMapper.map(GameControls.L2, Input.Keys.NUM_2);
            buttonMapper.map(GameControls.R1, Input.Keys.NUM_3);
            buttonMapper.map(GameControls.R2, Input.Keys.NUM_4);
        }
        else if(player == "Player02") {
            buttonMapper.map(GameControls.DPAD_UP, Input.Keys.UP);
            buttonMapper.map(GameControls.DPAD_DOWN, Input.Keys.DOWN);
            buttonMapper.map(GameControls.DPAD_LEFT, Input.Keys.LEFT);
            buttonMapper.map(GameControls.DPAD_RIGHT, Input.Keys.RIGHT);

            buttonMapper.map(GameControls.START, Input.Keys.ENTER);
            buttonMapper.map(GameControls.SELECT, Input.Keys.END);

            buttonMapper.map(GameControls.A, Input.Keys.NUMPAD_0);
            buttonMapper.map(GameControls.B, Input.Keys.CONTROL_RIGHT);
            buttonMapper.map(GameControls.X, Input.Keys.ALT_RIGHT);
            buttonMapper.map(GameControls.Y, Input.Keys.SHIFT_RIGHT);

            buttonMapper.map(GameControls.L1, Input.Keys.NUMPAD_1);
            buttonMapper.map(GameControls.L2, Input.Keys.NUMPAD_4);
            buttonMapper.map(GameControls.R1, Input.Keys.NUMPAD_2);
            buttonMapper.map(GameControls.R2, Input.Keys.NUMPAD_5);
        }

        return new KeyboardController<>(buttonMapper);
    }

    public static LogitechController<GameControls> buildLogitech(int conNum) {
        final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();
        // dpad buttons are read from axis's, not button codes directly
        // consider allowing axis's to be configured.

        buttonMapper.map(GameControls.START, NextController.BUTTON_START);
        buttonMapper.map(GameControls.SELECT, NextController.BUTTON_START);

        buttonMapper.map(GameControls.A, NextController.BUTTON_A);
        buttonMapper.map(GameControls.B, NextController.BUTTON_B);
        buttonMapper.map(GameControls.X, NextController.BUTTON_X);
        buttonMapper.map(GameControls.Y, NextController.BUTTON_Y);

        // left joystick pressed 10
        // right joystick pressed 11

        buttonMapper.map(GameControls.L1, NextController.BUTTON_L);
        buttonMapper.map(GameControls.L2, 5);
        buttonMapper.map(GameControls.R1, NextController.BUTTON_R);
        buttonMapper.map(GameControls.R2, 7);

        // treating the axis / joystick as a typical d-pad
        final AxisMapper<GameControls> axisMapper = new AxisMapper<>();
        axisMapper.map(GameControls.DPAD_UP, new Axis(NextController.AXIS_Y, -NextController.STICK_DEADZONE));
        axisMapper.map(GameControls.DPAD_DOWN, new Axis(NextController.AXIS_Y, NextController.STICK_DEADZONE));
        axisMapper.map(GameControls.DPAD_LEFT, new Axis(NextController.AXIS_X, -NextController.STICK_DEADZONE));
        axisMapper.map(GameControls.DPAD_RIGHT, new Axis(NextController.AXIS_X, NextController.STICK_DEADZONE));

        // hook in joystick for raw usage, i.e you need precise control over the joystick's position.
        axisMapper.map(GameControls.RIGHT_JOYSTICK_VERTICAL, new Axis(3, 0.01f));
        axisMapper.map(GameControls.RIGHT_JOYSTICK_HORIZONTAL, new Axis(2, 0.01f));

        return new LogitechController<>(conNum, buttonMapper, axisMapper);
    }

}