package com.teamawesome.zurbs.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.teamawesome.zurbs.component.Bounds;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisText;

public class TextBoundsCreator extends BaseEntitySystem {
	private ComponentMapper<Bounds> boundsCm;

	public TextBoundsCreator () {
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
