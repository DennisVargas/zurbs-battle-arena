package com.teamawesome.zurbs;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.kotcrab.vis.runtime.component.VisSound;
import com.kotcrab.vis.runtime.scene.VisAssetManager;


public class SoundController {
	boolean enabled = true;
	Music music;
	Sound click;
	float fxVolume = 0.5f;

	public SoundController (VisAssetManager manager) {
		//music is persisted across whole game, so we load it manually
		String musicPath = "music/boss.ogg";
		String clickPath = "sound/accept.wav";

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
			sound.play(fxVolume);
		}
	}

	public void setSoundEnabled (boolean enabled) {
		if(this.enabled == enabled)
			return;
		
		this.enabled = enabled;

		if (enabled)
			music.play();
		else
			music.stop();
	}

//	===========
//	GetMusicVol
//	=================
	public float GetMusicVol(){
		return this.music.getVolume();
	}// GetMusicVol()
//	=================

//	==========
//	GetFxVol()
//	========================
	public float GetFxVol(){
		return fxVolume;
	}//	GetFxVol()
//	==============

//	===================
//	ToggleMusicVolumeUp
//	==================================
	public void ToggleMusicVolumeUp(){

		if( GetMusicVol() < 1){
			this.music.setVolume(this.music.getVolume() + 0.01f);
		}
		else{
			System.out.println("Volume Max!");
		}
	}//	ToggleMusicVolumeUp
//	=======================

//	=====================
//	ToggleMusicVolumeDown
//	====================================
	public void ToggleMusicVolumeDown(){

		if( this.music.getVolume() > 0){
			this.music.setVolume(this.music.getVolume() - 0.01f);
		}
		else{
			System.out.println("Volume Min!");
		}
	}//	toggleMusicVolumeDown
//	=========================

//	================
//	ToggleFxVolumeUp
//	===============================
	public void ToggleFxVolumeUp(){

		if( this.fxVolume < 1.0f){
			this.fxVolume += 0.01f;
		}
		else{
			System.out.println("Volume Max!");
		}
	}//	ToggleFXVolumeUp
//	====================

//	=====================
//	ToggleFxVolumeDown
//	=================================
	public void ToggleFxVolumeDown(){

		/* ugh comparing floats */
		if( this.fxVolume > 0.01f){
			this.fxVolume -= 0.01f;
		}
		else{
			this.fxVolume = 0.0f;
			System.out.println("Volume Min!");
		}
	}//	toggleMusicVolumeDown
//	=========================
}