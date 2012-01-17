package com.scgame.adozeneggs.tweener;

public class Expo extends Linear implements EasingIF{


	public float  easeIn(float t, float b, float c, float d)
	{
		return (t==0) ? b : c * (float)Math.pow(2, 10 * (t/d - 1)) + b - c * 0.001f;
	}
	
	public float  easeOut(float t, float b, float c, float d)
	{
		return (t==d) ? b+c : c * (-(float)Math.pow(2, -10 * t/d) + 1) + b;
	}
	
	public float easeInOut(float t, float b, float c, float d)
	{
		if (t==0) return b;
		if (t==d) return b+c;
		if ((t/=d*0.5f) < 1f) return c*0.5f * (float)Math.pow(2, 10 * (t - 1)) + b;
		return c*0.5f * (-(float)Math.pow(2f, -10f * --t) + 2) + b;

	}
	

}
