package com.teamawesome.zurbs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teamawesome.zurbs.MenuScene0;
import com.teamawesome.zurbs.ZurbGame;
import com.teamawesome.zurbs.ZurbGameScene;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new ZurbGame(), config);
//		new LwjglApplication(new ZurbGameScene(), config);
		new LwjglApplication(new MenuScene0(), config);
	}
}