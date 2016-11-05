package com.teamawesome.zurbs.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.kotcrab.vis.runtime.component.VisText;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.teamawesome.zurbs.component.Bounds;

/**
 * Created by Dennis on 11/4/2016.
 */
public class TextSwitchingSystem extends EntityProcessingSystem implements AfterSceneInit{
    ComponentMapper<VisText> textCm;
    ComponentMapper<Bounds> boundsCm;

    /**
     * Creates a new EntityProcessingSystem.
     *
     * @param aspect the aspect to match entities
     */
    public TextSwitchingSystem(Aspect.Builder aspect) {
        super(aspect);
    }

    @Override
    protected void process(Entity e) {

    }

    @Override
    public void afterSceneInit() {

    }
}
