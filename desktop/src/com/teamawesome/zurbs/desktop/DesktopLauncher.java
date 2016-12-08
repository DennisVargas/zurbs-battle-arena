package com.teamawesome.zurbs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teamawesome.zurbs.ZurbGame;

import java.awt.*;


public class DesktopLauncher {
	public static void main (String[] arg) throws AWTException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ZurbGame(), config);
	}
}