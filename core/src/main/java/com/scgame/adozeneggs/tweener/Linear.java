package com.scgame.adozeneggs.tweener;

public class Linear implements EasingIF
{
	/**
	 * Easing function that interpolates the numbers
	 * 
	 * @param t time
	 * @param b start
	 * @param c change 
	 * @param d duration
	 * @return Result of the ease
	 */
	public float easeNone (float t, float b, float c, float d)
	{
		return c*t/d + b;
	}

	public float easeIn (float t, float b, float c, float d)
	{
		return c*t/d + b;
	}
	
	public float easeOut (float t, float b, float c, float d)
	{
		return c*t/d + b;
	}
	
	public float easeInOut(float t, float b, float c, float d)
	{
		return c*t/d + b;
	}
}
