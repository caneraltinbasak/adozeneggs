package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Game;

enum eScenes {
	RESOLUTION, MENU, LEVELS, GAMEPLAY, OPTIONS, CREDITS
}

public class SceneNavigator implements Game {
	public Scene activeScene;
	private Scene scResolution;
	private Scene scMenu;
	private Scene scLevels; 
	private Scene scGameplay; 
	
	/*
	public SceneNavigator() {
		
	}
	*/
	
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
	
	public Scene getActiveScene() {
		return activeScene;
	}

	@Override
	public void init() {
		graphics().setSize(220, 300);
		scResolution = new SceneResolution(this);
		activeScene = scResolution;
		runScene(eScenes.RESOLUTION);
	}

	@Override
	public void update(float delta) {
		activeScene.update(delta);
	}

	@Override
	public void paint(float alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateRate() {
		// TODO Auto-generated method stub
		return 25;
	}
}
		
	
	
	

