package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledImage extends ScrollableGraphicsEntity {
	private Vect2d position = new Vect2d(0, 0);;
	private ImageLayer imageLayer = null;
	private float scrollingSpeed = 0.0f;
	private float scrollPosition = 0.0f;
	private Image bgImage = null;

	public BGScrolledImage(String bgImagePath) {
		this.imageLayer = graphics().createImageLayer();
		bgImage = (Image)CachedResource.getInstance().getResource(bgImagePath);
		imageLayer.setImage(bgImage);
	}
	@Override
	public void scrollTo(float scrollPosition) {
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
		this.position=position;
	}

	@Override
	public void paint(float alpha) {
		imageLayer.setTranslation(position.x, position.y);
	}

	@Override
	public void update(float delta) {
		if(scrollingSpeed != 0.0f)
		{
			position.y = position.y + GameConstants.PhysicalProperties.verticalInPixels(scrollingSpeed) * delta / 1000;
			if(scrollPosition <= position.y)
			{
				scrollingSpeed = 0;
			}
		}
	}

}
