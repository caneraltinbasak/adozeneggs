package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledImage extends GraphicsEntity {
	private Vect2d position = new Vect2d(0, 0);;
	private ImageLayer imageLayer = null;
	private Image bgImage = null;
	private OnScreenCheckInterface parentRect;
	private Vect2d initialPosition = null;


	public BGScrolledImage(String bgImagePath) {
		this.imageLayer = graphics().createImageLayer();
		bgImage = (Image)CachedResource.getInstance().getResource(bgImagePath);
		imageLayer.setImage(bgImage);
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
		this.initialPosition = position.copy();
		this.position=position;
	}

	@Override
	public void paint(float alpha) {
		imageLayer.setTranslation(position.x, position.y);
	}

	@Override
	public void update(float delta) {
		switch(getParentRect().isInRect(this))
		{
		case TOP_IS_UNDER_VISIBLE_AREA:
			position.y -= bgImage.height()*3;
			break;
		case BOTTOM_IS_OVER_VISIBLE_AREA: // this should happen scrolling down.
			if(position.y != initialPosition.y)
				position.y += bgImage.height()*3;
			break;
		default:
			break;
		}
	}
	@Override
	public float getHeight() {
		return this.imageLayer.height();
	}

	public OnScreenCheckInterface getParentRect() {
		return parentRect;
	}

	public void setParentRect(OnScreenCheckInterface parentRect) {
		this.parentRect = parentRect;
	}

}
