package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Game;
import playn.core.GroupLayer;

public class adozeneggs implements Game {
	private GroupLayer layer;
	private GroupLayer baseLayer;
	private SceneNavigator sceneNavigator;
	
	@Override
	public void init() {
		// init screen size for simulation
		graphics().setSize(220, 300);
		SceneNavigator sceneNavigator = new SceneNavigator();
		sceneNavigator.runScene(eScenes.RESOLUTION);
	}
	
	@Override
	public void paint(float alpha) {
		/*
		if (sceneNavigator.activeScene != null) {
			sceneNavigator.activeScene.paint(alpha);
		}
		*/
	}

	@Override
	public void update(float delta) {
		System.out.println("adozeneggs update");
		if (sceneNavigator.getActiveScene() != null) {
			//System.out.println("not null");
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
}
