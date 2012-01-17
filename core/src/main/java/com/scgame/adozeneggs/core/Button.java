package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class Button {
	private float px; 	// x coordinate in pixel value
	private float py;	// y coordinate in pixel value
	private float width; 
	private float height;
	private boolean isHit;
	protected ImageLayer imageLayer = null;
	protected ButtonEventListener listener = null;
	protected Image imgButton = null;
	private boolean visible = true;
	private boolean enabled = true;

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
	   
	    	 
		imgButton = (Image)CachedResource.getInstance().getResource(imagePath);	
		imageLayer.setImage(imgButton);
		// Setting button size after image is loaded
		width = imgButton.width();
		height = imgButton.height();
	}
  
	public ImageLayer getLayer () {
		return this.imageLayer;
	}
		  
	/**
	 * Checks if pointer is clicked-touched on button
	 * @param x pointer (mouse, touch) value on x coordinate 
	 * @param y pointer (mouse, touch) value on y coordinate
	 */
	public boolean hitTest(float x, float y) {	
		if (visible == false) {
			return false;
		}
		
		if (enabled == false) {
			return false;
		}
		
		if ((x > px) && (x < px + width) && (y > py) && (y < py + height)) {
			this.isHit = true;
		} else {
			this.isHit = false;
		}
		return this.isHit;
	}
	
	public boolean clicked(Vect2d pointer) {
		if (hitTest(pointer.x, pointer.y)) {
			if (listener != null) {
				listener.onClick();
			}
			return true;
		}
		return false;
	}
	
	public synchronized void setEventListener(ButtonEventListener listener) {
		this.listener = listener;
	}
	
	public void setLayerDepth(float depth) {
		if (this.imageLayer != null) {
			this.imageLayer.setDepth(depth);
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		if (visible == false) {
			this.imageLayer.setTranslation(-10000, 0);
		}
		else {
			this.imageLayer.setTranslation(this.px, this.py);
		}
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
}