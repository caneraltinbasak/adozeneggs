package com.scgame.adozeneggs.tweener;

public class Cubic extends Linear implements EasingIF{

	
	public float easeIn(float t, float b, float c, float d)
	{
		return c*(t/=d)*t*t + b;
	}
	
	public float easeOut(float t, float b, float c, float d)
	{
		return c*((t=t/d-1)*t*t + 1f) + b;
	}
	
	public float easeInOut(float t, float b, float c, float d)
	{
		if ((t/=d*0.5f) < 1) return c*0.5f*t*t*t + b;
		return c*0.5f*((t-=2f)*t*t + 2f) + b;
	}
	
}
