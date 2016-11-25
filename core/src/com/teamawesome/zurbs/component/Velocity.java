package com.teamawesome.zurbs.component;

import com.artemis.Component;

/**
 * Created by Dennis on 11/19/2016.
 */
public class Velocity extends Component{
    public float x;
    public float y;

    public void SetVelocity(float xTemp, float yTemp){
        this.x = xTemp;
        this.y = yTemp;
    }
}
