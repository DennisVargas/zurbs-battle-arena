package com.teamawesome.zurbs.component;

import com.artemis.Component;
import com.kennycason.gdx.controller.Controller;


/**
 * Created by Dennis on 11/22/2016.
 */
public class Player extends Component {


    private Controller controller = null;
    private String spriteColor = "bland";
    private boolean facingRight = true;
    public boolean toDestroy = false;
    private boolean destroyed = false;

    public Player(Controller controller, final String spriteColor, boolean right) {
        this.spriteColor = spriteColor;
        this.controller = controller;
        this.facingRight = right;
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

    public boolean isFacingRight() { return facingRight; }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean getToDestroy() { return toDestroy; }

    public void setToDestroy(boolean setToDestroy) { this.toDestroy = setToDestroy; }

    public boolean isDestroyed() { return destroyed; }

    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }

    public void update () {
        if (toDestroy && !destroyed) {

        }
    }
}
