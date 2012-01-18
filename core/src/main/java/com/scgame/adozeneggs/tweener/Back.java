package com.scgame.adozeneggs.tweener;

public class Back extends Linear implements EasingIF
{
	float s= 1.70158f;
	public float easeIn (float t, float b, float c, float d)
	{
		return c*(t/=d)*t*((s+1)*t - s) + b;
	}
	public float easeOut (float t, float b, float c, float d)
	{
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	}
	public float easeInOut (float t, float b, float c, float d)
	{
		float s= 1.70158f;
		if ((t/=d*0.5f) < 1f) return c*0.5f*(t*t*(((s*=(1.525f))+1f)*t - s)) + b;
		return c/2f*((t-=2f)*t*(((s*=(1.525f))+1f)*t + s) + 2f) + b;
	}
}
