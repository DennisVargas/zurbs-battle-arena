package com.teamawesome.zurbs.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityTransmuter;
import com.artemis.systems.IteratingSystem;
import com.kotcrab.vis.runtime.component.Renderable;

/**
 * Created by Dennis on 11/16/2016.
 */
public class SelectorMainUpdater extends IteratingSystem {
    /**
     * Creates a new EntityProcessingSystem.
     */
    public SelectorMainUpdater() {
        super(Aspect.all(Renderable.class));
    }

    @Override
    protected void process(int entityId) {
        int tempLevel;



    }
}
