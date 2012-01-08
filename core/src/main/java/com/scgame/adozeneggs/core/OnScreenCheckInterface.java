package com.scgame.adozeneggs.core;

enum eOnScreenState {
	IS_UNDER_VISIBLE_AREA, 
	IS_OVER_VISIBLE_AREA
}
public interface OnScreenCheckInterface {

	eOnScreenState isInRect(Entity entity);

}
