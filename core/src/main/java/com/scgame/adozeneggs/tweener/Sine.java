package com.scgame.adozeneggs.tweener;

public class Sine extends Linear implements EasingIF{
	
	private static float HALF_PI = (float)Math.PI * 0.5f;
			
	public float easeIn (float t, float b, float c, float d) {
		return -c * (float)Math.cos(t/d * HALF_PI) + c + b;
	}
	public float easeOut (float t, float b, float c, float d) {
		return c * (float)Math.sin(t/d * HALF_PI) + b;
	}
	public float easeInOut (float t, float b, float c, float d)
	{
		return -c*0.5f * ((float)Math.cos((float)Math.PI*t/d) - 1f) + b;
	}

} 
