package com.teamawesome.zurbs.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.teamawesome.zurbs.component.Bounds;
import com.teamawesome.zurbs.component.Velocity;

/**
 * Created by Dennis on 11/19/2016.
 */
public class PhysicsSystem extends EntityProcessingSystem {
    /**
     * Creates a new EntityProcessingSystem.
     */
    ComponentMapper<VisSprite> spriteCm;
    ComponentMapper<Transform> transCm;
    ComponentMapper<Velocity> velocityCm;
    ComponentMapper<Bounds> boundsCm;

    public PhysicsSystem() {
        super(Aspect.all(VisSprite.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {

    }
}
