package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.ResourceCallback;

public class NonCachedButton {
	private float px; 	// x coordinate in pixel value
	private float py;	// y coordinate in pixel value
	private float width; 
	private float height;
	private boolean isHit;
	private ImageLayer imageLayer = null;
	private ButtonEventListener listener = null;
	private ButtonCallback callback = null;
	private boolean btnReady = false;
	
	/**
	 * Button Constructor
	 * @param x 
	 * @param y 
	 * @param imagePath image for button
	 */
	public NonCachedButton(float x, float y, String imagePath) {
	    this.px = x;
		this.py = y;
	    this.isHit = false;
	    this.imageLayer = graphics().createImageLayer();
	    this.imageLayer.setTranslation(px, py);
	    	 
		Image button = assetManager().getImage(imagePath);	 	
		this.loadImage(button, this);	
	}
  
	public ImageLayer getLayer () {
		return this.imageLayer;
	}
	
	void loadImage(Image image, final NonCachedButton button) {
		image.addCallback(new ResourceCallback<Image>() {
			@Override
			public void done(Image image) {
				imageLayer.setImage(image);
				// Setting button size after image is loaded
				width = image.width();
				height = image.height();
				btnReady = true;
				
				if (callback != null) {
					callback.done();
				}
			}

			@Override
			public void error(Throwable err) {
				log().error("Button.loadImage : Error loading button image! ", err);
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
	
	public void clicked(Vect2d pointer) {
		if (hitTest(pointer.x, pointer.y)) {
			if (listener != null) {
				listener.onClick();
			}
		}
	}
	
	public synchronized void setEventListener(ButtonEventListener listener) {
		this.listener = listener;
	}
	
	public void addCallback(ButtonCallback callback) {
		this.callback = callback;
		
		if (btnReady) {
			callback.done();
		}
	}
	
	public void setLayerDepth(float depth) {
		if (this.imageLayer != null) {
			this.imageLayer.setDepth(depth);
		}
	}
}