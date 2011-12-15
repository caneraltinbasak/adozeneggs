package com.scgame.adozeneggs.core;

enum eScenes {
	RESOLUTION, MENU, LEVELS, GAMEPLAY, OPTIONS, CREDITS
}

public class SceneNavigator {
	private Scene activeScene;
	private Scene scResolution;
	private Scene scMenu;
	private Scene scLevels; 
	private Scene scGameplay; 
	
	public SceneNavigator() {
		// Creating scenes
		scResolution = new SceneResolution(this);
	}
	
	public void createScenes() {
		scMenu = new SceneMenu(this);
		scLevels = new SceneLevels(this); 
		scGameplay = new SceneGameplay(this); 
	}
	
	public void runScene(eScenes scene) {
		if (activeScene != null) {
			activeScene.shutdown();
		}
		
		switch (scene) {
		case MENU: 
			activeScene = scMenu;
			activeScene.init();
			break;
		case LEVELS:
			activeScene = scLevels;
			activeScene.init();
			break;
		case GAMEPLAY:
			activeScene = scGameplay;
			activeScene.init();
			break;
		case RESOLUTION:
			activeScene = scResolution;
			activeScene.init();
			break;
		}
	}
}
		
	
	
	

