package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.Pointer;

enum eToggle {
	ON, OFF
}
public class ToggleButton extends Button {
	Image imgButtonOff = null;
	Image activeImage = null;
	eToggle activeToogle;
	
	public ToggleButton(float px, float py, String onImagePath, String offImagePath, eToggle toggle) {
		super(px, py, onImagePath);
		imgButtonOff = (Image)CachedResource.getInstance().getResource(offImagePath);	
		
		if (toggle == eToggle.ON) {
			imageLayer.setImage(imgButton);
			activeImage = imgButton;
			activeToogle = eToggle.ON;
		} 
		else
		{
			imageLayer.setImage(imgButtonOff);
			activeImage = imgButtonOff;
			activeToogle = eToggle.OFF;
		}
	}
	
	public void toggle() {
		if (activeImage != null) {
			if (activeImage == imgButton) {
				imageLayer.setImage(imgButtonOff);
				activeImage = imgButtonOff;
				activeToogle = eToggle.OFF;
			}
			else {
				imageLayer.setImage(imgButton);
				activeImage = imgButton;
				activeToogle = eToggle.ON;
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
	
	public eToggle getToggle() {
		return activeToogle;
	}

}
