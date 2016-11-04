package com.teamawesome.zurbs.system;


import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.kotcrab.vis.runtime.component.VisText;
import com.teamawesome.zurbs.component.Bounds;

public class SpriteBoundsCreator extends BaseEntitySystem {
    private ComponentMapper<Bounds> boundsCm;

    public SpriteBoundsCreator() {
        super(Aspect.all(VisText.class));
    }

    @Override
    public void inserted (IntBag entities) {
        int[] data = entities.getData();
        for (int i = 0; i < entities.size(); i++) {
            int entityId = data[i];
            boundsCm.create(entityId);
        }
    }

    @Override
    protected void processSystem () {

    }
}
