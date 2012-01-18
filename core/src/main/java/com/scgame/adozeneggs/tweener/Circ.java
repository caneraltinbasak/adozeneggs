package com.scgame.adozeneggs.tweener;

public class Circ extends Linear implements EasingIF
{

	public float easeIn (float t, float b, float c, float d)
	{
		return -c * ((float)Math.sqrt(1 - (t/=d)*t) - 1) + b;
	}
	public float easeOut (float t, float b, float c, float d)
	{
		return c * (float)Math.sqrt(1 - (t=t/d-1)*t) + b;
	}
	public float easeInOut (float t, float b, float c, float d)
	{
		if ((t/=d*0.5f) < 1) return -c*0.5f * ((float)Math.sqrt(1 - t*t) - 1) + b;
		return c*0.5f * ((float)Math.sqrt(1 - (t-=2)*t) + 1) + b;
	}
}
