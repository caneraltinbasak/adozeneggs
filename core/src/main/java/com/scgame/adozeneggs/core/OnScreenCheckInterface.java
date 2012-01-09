package com.scgame.adozeneggs.core;

enum eOnScreenState {
	TOTAL_IS_IN_VISIBLE_AREA,
	TOP_IS_UNDER_VISIBLE_AREA, 
	BOTTOM_IS_UNDER_VISIBLE_AREA,
	TOP_IS_OVER_VISIBLE_AREA,
	BOTTOM_IS_OVER_VISIBLE_AREA
}
public interface OnScreenCheckInterface {

	eOnScreenState isInRect(Entity entity);

}
