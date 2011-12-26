package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledImage extends GraphicsEntity {
	private Image bgImage;
	private ImageLayer imageLayer;

	public BGScrolledImage(String imagePath){
	    this.imageLayer = graphics().createImageLayer();
		bgImage = (Image)CachedResource.getInstance().getResource(imagePath);
		imageLayer.setImage(bgImage);
	}

	@Override
	public void paint(float alpha) {
		
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public ImageLayer getImageLayer() {
		return imageLayer;
	}

	@Override
	public Vect2d getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vect2d position) {
		// TODO Auto-generated method stub
		
	}

}
