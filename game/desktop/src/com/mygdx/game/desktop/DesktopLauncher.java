package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.JeuDeFootball;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1228;
		config.height = 768;
		config.resizable = false;
		config.title = "Jeu de Football";
		new LwjglApplication(new JeuDeFootball(), config);
	}
}
