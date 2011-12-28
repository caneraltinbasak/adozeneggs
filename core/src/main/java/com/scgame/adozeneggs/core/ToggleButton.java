package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.Pointer;



public class ToggleButton extends Button {
	Image imgButtonOff = null;
	Image activeImage = null;
	int activeToogle;
	
	public ToggleButton(float px, float py, String onImagePath, String offImagePath, int toggle) {
		super(px, py, onImagePath);
		imgButtonOff = (Image)CachedResource.getInstance().getResource(offImagePath);	
		
		if (toggle == Toggle.ON) {
			imageLayer.setImage(imgButton);
			activeImage = imgButton;
			activeToogle = Toggle.ON;
		} 
		else
		{
			imageLayer.setImage(imgButtonOff);
			activeImage = imgButtonOff;
			activeToogle = Toggle.OFF;
		}
	}
	
	public void toggle() {
		if (activeImage != null) {
			if (activeImage == imgButton) {
				imageLayer.setImage(imgButtonOff);
				activeImage = imgButtonOff;
				activeToogle = Toggle.OFF;
			}
			else {
				imageLayer.setImage(imgButton);
				activeImage = imgButton;
				activeToogle = Toggle.ON;
			}
		}
		else {
			log().error("ToggleButton::toggle(), there is no active image for toggle button!");
		}
	}
	
	public boolean clicked(Vect2d pointer) {
		if (hitTest(pointer.x, pointer.y)) {
			toggle();
			if (listener != null) {
				listener.onClick(pointer);
			}
			return true;
		}
		return false;
	}
	
	public int getToggle() {
		return activeToogle;
	}

}
