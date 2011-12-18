package com.scgame.adozeneggs.core;

import playn.core.Json;
import static playn.core.PlayN.graphics;
import playn.core.Game;

enum eScenes {
	RESOLUTION, MENU, LEVELS, GAMEPLAY, OPTIONS, CREDITS, LOADING
}


public class SceneNavigator implements Game {
	public Scene activeScene;
	private static SceneNavigator instance = null;
	private Scene scResolution;
	private Scene scMenu;
	private Scene scLevels; 
	private Scene scGameplay;
	private Scene scLoading;
	
	private SceneNavigator() {
		// Creating scenes
		scResolution = new SceneResolution();
	}
	public void createScenes(){
		scMenu = new SceneMenu();
		scLevels = new SceneLevels(); 
		scGameplay = new SceneGameplay(); 
		scLoading = new SceneLoading(); 
	}
	public static SceneNavigator getInstance() {
		if (instance == null) {
			instance = new SceneNavigator();
		}
		return instance;
	}
	public void runScene(eScenes scene, Object data) {
		if (activeScene != null) {
			activeScene.shutdown();
		}
		
		switch (scene) {
		case MENU: 
			activeScene = scMenu;
			activeScene.init(null);
			break;
		case LEVELS:
			activeScene = scLevels;
			activeScene.init(null);
			break;
		case GAMEPLAY:
			activeScene = scGameplay;
			activeScene.init(data);
			break;
		case RESOLUTION:
			activeScene = scResolution;
			activeScene.init(null);
			break;
		case LOADING:
			activeScene = scLoading;
			activeScene.init(null);
			break;
		}
	}
	
	public Scene getActiveScene() {
		return activeScene;
	}

	@Override
	public void init() {
		graphics().setSize(220, 300);
		scResolution = new SceneResolution();
		activeScene = scResolution;
		runScene(eScenes.RESOLUTION, null);
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
		
	
	
	

