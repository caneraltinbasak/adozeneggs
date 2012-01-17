package com.scgame.adozeneggs.tweener;

public interface EasingIF 
{
	public float easeNone (float t, float b, float c, float d);	
	public float easeIn (float t, float b, float c, float d);	
	public float easeOut (float t, float b, float c, float d);	
	public float easeInOut(float t, float b, float c, float d);
}
