package com.teamawesome.zurbs;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.kotcrab.vis.runtime.component.VisSound;
import com.kotcrab.vis.runtime.scene.VisAssetManager;


public class SoundController {
	boolean enabled = false;
	Music music;
	Sound click;

	public SoundController (VisAssetManager manager) {
		//music is persisted across whole game, so we load it manually
		String musicPath = "music/boss.ogg";
		String clickPath = "sound/accept.mp3";

		manager.load(musicPath, Music.class);
		manager.load(clickPath, Sound.class);

		manager.finishLoading();

		music = manager.get(musicPath, Music.class);
		click = manager.get(clickPath, Sound.class);
		
		music.setLooping(true);

		if (enabled) music.play();
	}

	public void playClick () {
		play(click);
	}

	public void play (VisSound component) {
		play(component.sound);
	}

	public void play (Sound sound) {
		if (enabled) {
			sound.play();
		}
	}

	public void setSoundEnabled (boolean enabled) {
		this.enabled = enabled;

		if (enabled)
			music.play();
		else
			music.stop();
	}
}