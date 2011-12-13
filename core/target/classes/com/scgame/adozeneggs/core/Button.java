package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import playn.core.ResourceCallback;

public class Button implements Listener {
	float px; 	// x coordinate in pixel value
	float py;	// y coordinate in pixel value
	float width; 
	float height;
	boolean isHit;
	ImageLayer imageLayer;
	
	/**
	 * Button Constructor
	 * @param marginLeft left margin for button (in percentage)
	 * @param marginTop top margin for button (in percentage)
	 * @param imagePath image for button
	 */
	public Button(float marginLeft, float marginTop, String imagePath) {
	    this.px = GameConstants.ScreenProperties.width * (marginLeft);
		this.py = GameConstants.ScreenProperties.height * (marginTop);
	    this.isHit = false;
	    this.imageLayer = graphics().createImageLayer();
	    this.imageLayer.setTranslation(px, py);
	    	 
		Image button = assetManager().getImage(imagePath);	 	
		this.loadImage(button);
	}
  
	public ImageLayer getLayer () {
		return this.imageLayer;
	}
	
	void loadImage(Image image) {
		image.addCallback(new ResourceCallback<Image>() {
			@Override
			public void done(Image image) {
				imageLayer.setImage(image);
				height = image.height();
				width = image.width();
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
			});
	}
	  
	/**
	 * Checks if pointer is clicked-touched on button
	 * @param x pointer (mouse, touch) value on x coordinate 
	 * @param y pointer (mouse, touch) value on y coordinate
	 */
	public boolean hitTest(float x, float y) {
		if ((x > px) && (x < px + width) && (y > py) && (y < py + height)) {
			this.isHit = true;
		} else {
			this.isHit = false;
		}
		return this.isHit;
	}

	@Override
	public void onPointerStart(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPointerEnd(Event event) {
		System.out.println(event.x() + " " + event.y());
		
	}

	@Override
	public void onPointerDrag(Event event) {
		// TODO Auto-generated method stub
		
	}
}