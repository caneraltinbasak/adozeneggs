package com.scgame.adozeneggs.tweener;


public class freeTimer  
{
	public static freeTimer timer=new freeTimer();

	long pauseStartTime=0;
	long pauseTime=0;
	
	public long starttime=-1;

	public float maxTime=0.0f;
	public float diff=0.0f;
	
	long currentTime = 0;

	public freeTimer()
	{
		reset();
	}
	
	public long getSysTime()
	{
		return currentTime; 
	}

	public void reset()
	{
		starttime=getSysTime();
		
	}
	
	public void setSysTime(float delta) {
		currentTime += delta;
	}

	public float getTime()
	{
		 
		if(starttime==-1)
		{
			reset();
		}
		float t=(getSysTime()-starttime-pauseTime)/1000.0f;
		
		if(maxTime!=0.0f && t>maxTime)
		{
			starttime=getSysTime();
			t=0;
		}
		return t;
	}

	public long timeSincePause()
	{
		return getSysTime()-pauseStartTime;
	}

	public void pause(boolean t)
	{
		if(t)
		{
			pauseStartTime=getSysTime();
		}
		else
		{
			pauseTime=getSysTime()-pauseStartTime;
		}
	}




}
