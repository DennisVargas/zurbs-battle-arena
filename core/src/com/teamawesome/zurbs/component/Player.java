package com.teamawesome.zurbs.component;

import com.artemis.Component;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Dennis on 11/22/2016.
 */
public class Player extends Component {


    private Controller controller = null;
    private String spriteColor = "bland";

    public Player(Controller controller, final String spriteColor) {
        this.spriteColor = spriteColor;
        this.controller = controller;

        this.controller.addListener(new ControllerListener() {
            @Override
            public void connected(Controller controller) {

            }

            @Override
            public void disconnected(Controller controller) {

            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                System.out.println(spriteColor+": AxisCode: "+axisCode+" Value: "+value);

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
        });
    }

    public String getSpriteColor() {
        return spriteColor;
    }

    public void setSpriteColor(String spriteColor) {
        this.spriteColor = spriteColor;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
