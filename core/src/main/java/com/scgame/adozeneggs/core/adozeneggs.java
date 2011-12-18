package com.scgame.adozeneggs.core;

import playn.core.Game;
import playn.core.GroupLayer;
import static playn.core.PlayN.graphics;

public class adozeneggs implements Game {
	GroupLayer layer;

	GroupLayer baseLayer;
	
	@Override
	public void init() {
		
		// init screen size for simulation
		graphics().setSize(220, 300);
		SceneNavigator.getInstance().runScene(eScenes.RESOLUTION, null);
		
		
		// create a group layer to hold everything
		/*
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
}
