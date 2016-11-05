package com.teamawesome.zurbs.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisText;
import com.teamawesome.zurbs.component.Bounds;

/**
 * Created by Dennis on 11/4/2016.
 */
public class BoundsCreator extends BaseEntitySystem {
    private ComponentMapper<Bounds> boundsCm;

    public BoundsCreator(){
        super(Aspect.one(VisSprite.class,VisText.class));
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
