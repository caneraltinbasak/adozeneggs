package com.scgame.adozeneggs.core;
/**
 * 
 * @author Caner
 * Event listener interface for listening JumpEvents
 */
public interface EggEventListener {
    public void onEggFall(Egg egg);
	public void onEggJump(Egg egg, int stars);
	public void onEggOnCrashGround();
}
