package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Game;
import playn.core.GroupLayer;

public class adozeneggs implements Game {
	GroupLayer layer;
	private Scene activeScene;
	private Scene scMenu;
	private Scene scLevels; 
	private Scene scGameplay; 
	GroupLayer baseLayer;
	private int screenWidth;
	private int screenHeight;
	
	@Override
	public void init() {
		
		// init screen size for simulation
		this.screenWidth = 320;
		this.screenHeight = 480;
		graphics().setSize(screenWidth, screenHeight);
		
		// Creating scenes
		scMenu = new SceneMenu(this);
		scLevels = new SceneLevels(this); 
		scGameplay = new SceneGameplay(this); 
		
		runSceneMenu();
		
		/*
		// create a group layer to hold everything
		layer = graphics().createGroupLayer();
		graphics().rootLayer().add(layer);
		// create and add background image layer

		Image bgImage = assetManager().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		layer.add(bgLayer);
		
		Basket aBasket = new Basket(new Vect2d(0, 0), new Vect2d(400, 400));
		Basket bBasket = new Basket(new Vect2d(0, 0), new Vect2d(400, 200));
		
		Egg egg= new Egg(layer, aBasket, bBasket);

		 */
	}


	public void runSceneMenu() {
		if (activeScene != null) {
			activeScene.shutdown();
		}
		activeScene = scMenu;
		activeScene.init();
	}
	
	public void runSceneLevels() {
		if (activeScene != null) {
			activeScene.shutdown();
		}
		activeScene = scLevels;
		activeScene.init();
	}
	
	public void runSceneGameplay(String level) {
		if (activeScene != null) {
			activeScene.shutdown();
		}
		activeScene = scGameplay;
		((SceneGameplay) scGameplay).init(level);
	}
	
	@Override
	public void paint(float alpha) {
			
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public int updateRate() {
		return 25;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
}
