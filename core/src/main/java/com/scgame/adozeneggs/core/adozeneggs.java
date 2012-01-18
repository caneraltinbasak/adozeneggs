package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import com.scgame.adozeneggs.tweener.freeTimer;

import playn.core.Game;
import playn.core.GroupLayer;

public class adozeneggs implements Game {
	private GroupLayer layer;
	private GroupLayer baseLayer;
	
	@Override
	public void init() {
		// init screen size for simulation
		graphics().setSize(220, 300);
		SceneNavigator.getInstance().runScene(eScenes.RESOLUTION, null);

	}
	
	@Override
	public void paint(float alpha) {
		if (SceneNavigator.getInstance().getActiveScene() != null) {
			SceneNavigator.getInstance().getActiveScene().paint(alpha);
		}
	}

	@Override
	public void update(float delta) {
		// update tweener timer
		freeTimer.timer.setSysTime(delta);
		if (SceneNavigator.getInstance().getActiveScene() != null) {
			SceneNavigator.getInstance().getActiveScene().update(delta);
		}
	}

	@Override
	public int updateRate() {
		return 1000/GameConstants.ScreenProperties.UPDATE_MS;
	}
}
