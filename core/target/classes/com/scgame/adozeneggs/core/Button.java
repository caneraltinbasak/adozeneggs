package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.ResourceCallback;

public class Button {
	private float px; 	// x coordinate in pixel value
	private float py;	// y coordinate in pixel value
	private float width; 
	private float height;
	private boolean isHit;
	private ImageLayer imageLayer;
	ButtonEventListener listener = null;
	
	/**
	 * Button Constructor
	 * @param x 
	 * @param y 
	 * @param imagePath image for button
	 */
	public Button(float x, float y, String imagePath) {
	    this.px = x;
		this.py = y;
	    this.isHit = false;
	    this.imageLayer = graphics().createImageLayer();
	    this.imageLayer.setTranslation(px, py);
	    	 
		Image button = assetManager().getImage(imagePath);	 	
		this.loadImage(button);
		
		this.height = button.height();
		this.width = button.width();		
	}
  
	public ImageLayer getLayer () {
		return this.imageLayer;
	}
	
	void loadImage(Image image) {
		image.addCallback(new ResourceCallback<Image>() {
			@Override
			public void done(Image image) {
				imageLayer.setImage(image);
				width = image.width();
				height = image.height();
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
	
	public void clicked(Pointer.Event event) {
		if (hitTest(event.x(), event.y())) {
			if (listener != null) {
				listener.onClick(event);
			}
		}
	}
	
	public synchronized void setEventListener(ButtonEventListener listener) {
		this.listener = listener;
	}
}