package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;
import playn.core.Image;

public class ToggleButton extends Button {
	Image imgButtonOff = null;
	Image activeImage = null;
	
	public ToggleButton(float px, float py, String onImagePath, String offImagePath) {
		super(px, py, onImagePath);
		activeImage = imgButton;
		imgButtonOff = (Image)CachedResource.getInstance().getResource(offImagePath);	
		
	}
	
	public void toggle() {
		if (activeImage != null) {
			if (activeImage == imgButton) {
				imageLayer.setImage(imgButtonOff);
				activeImage = imgButtonOff;
			}
			else {
				imageLayer.setImage(imgButton);
				activeImage = imgButton;
			}
		}
		else {
			log().error("ToggleButton::toggle(), there is no active image for toggle button!");
		}
		
	}
}
