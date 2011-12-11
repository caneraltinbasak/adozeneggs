package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;

public class Button {
	float px; // x coordinate in percentage 
	float py; // y coordinate in percentage
	int width; 
	int height;
	boolean isHit;
	ImageLayer imageLayer;
	
	public Button(float px, float py, String imagePath) {
	    this.px = px;
	    this.py = py;
	    this.isHit = false;
	    this.imageLayer = graphics().createImageLayer();
	    this.imageLayer.setTranslation(px, py);
	    	    
		Image button = assetManager().getImage(imagePath);		
		this.height = button.height();
		this.width = button.width();
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
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
			});
	}
	  
	public boolean hitTest(float x, float y) {
		if ((x > px) && (x < px + width) && (y > py) && (y < py + height)) {
			this.isHit = true;
		} else {
			this.isHit = false;
		}
		return this.isHit;
	}
}