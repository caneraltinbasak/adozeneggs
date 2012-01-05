package com.scgame.adozeneggs.core;

import static playn.core.PlayN.storage;
import playn.core.Sound;

public class SoundControl {
	private static SoundControl instance = null;
	// Loading game music
    private Sound gameMusic;
    private Sound jump;
    
	private SoundControl() {
		
		if (storage().getItem("music") == null) {
			storage().setItem("music", "on");
		}
		
		if (storage().getItem("sound") == null) {
			storage().setItem("sound", "on");
		}
		
		gameMusic = (Sound)CachedResource.getInstance().getResource("sounds/game_music");
		jump = (Sound)CachedResource.getInstance().getResource("sounds/jump");
		
	}
	
	public static SoundControl getInstance() {
		if (instance == null) {
			instance = new SoundControl();
		}
		return instance;
	}
	
	public void setMusicOn() {
		storage().setItem("music", "on");
	}
	
	public void setMusicOff() {
		storage().setItem("music", "off");
	}
	
	public void setSoundOn() {
		storage().setItem("sound", "on");
	}
	
	public void setSoundOff() {
		storage().setItem("sound", "off");
	}
	
	public boolean isMusicOn() {
		if (storage().getItem("music").equals("on")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isSoundOn() {
		if (storage().getItem("sound").equals("on")) {
			return true;
		}
		else {
			return false;
		}

	}
	
	public void playGameMusic() {
		gameMusic.play();
	}
	
	public void stopGameMusic() {
		gameMusic.stop();
	}
	
	public void playJump() {
		jump.play();
	}
}

