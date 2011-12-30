package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledImage extends GraphicsEntity {

	private Image bgImage;
	private ImageLayer imageLayer;
	private Vect2d position = new Vect2d(0, 0);

	public BGScrolledImage(String imagePath){
	    this.imageLayer = graphics().createImageLayer();
		bgImage = (Image)CachedResource.getInstance().getResource(imagePath);
		imageLayer.setImage(bgImage);
	}

	@Override
	public void paint(float alpha) {
		imageLayer.setTranslation(position.x, position.y);
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public ImageLayer getTopImageLayer() {
		return imageLayer;
	}

	@Override
	public Vect2d getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vect2d position) {
		this.position = position;
	}

}
