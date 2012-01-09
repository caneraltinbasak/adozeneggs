package com.scgame.adozeneggs.core;
/**
 * 
 * @author Caner
 * Event listener interface for listening JumpEvents
 */
public interface EggEventListener {
    public void onEggFall();
	public void onEggJump(Basket basket, int stars);
}
