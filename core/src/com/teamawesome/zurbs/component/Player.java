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
        this.controller = controller;}

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
