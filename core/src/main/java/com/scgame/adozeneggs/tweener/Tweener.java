package com.scgame.adozeneggs.tweener;

import java.util.Vector;

import com.scgame.adozeneggs.tweener.Cubic;
import com.scgame.adozeneggs.tweener.EasingIF;
import com.scgame.adozeneggs.tweener.KeyEvent;
import com.scgame.adozeneggs.tweener.freeTimer;

public class Tweener 
{
	public static final int EASING_IN=0;
	public static final int EASING_OUT=1;
	public static final int EASING_INOUT=2;
	public static final int EASING_NONE=3;

	private boolean isKeyframed=true;
	private KeyFloat keyframes[]=null;
	private int lastNotifiedKey=-1;
	EasingIF defaultEasing;

	Vector tweenEventListeners=null;

	
	public Tweener()
	{
		defaultEasing =(EasingIF)new Cubic();
		initKeyframes();
	}

	public Tweener(EasingIF easing)
	{
		this.defaultEasing =easing;
		initKeyframes();
	}
	
	private void initKeyframes()
	{
		keyframes=new KeyFloat[1];
		keyframes[0]=new KeyFloat(0, 0, EASING_NONE);
		if(tweenEventListeners==null)tweenEventListeners=new Vector();
	}

	private void notifyKeysChanged(Object o)
	{
		for(int i=0;i<tweenEventListeners.size();i++)
		{
			tweenEventListeners.elementAt(i);
		}
	}

	/**
	 * adds a new key to the tweener. 
	 *
	 * @param  time		relative time of the new key
	 * @param  value 	float value of the new key
	 */
	public KeyFloat add(float timeDiff, float value)
	{
		return set(freeTimer.timer.getTime()+timeDiff, value, EASING_NONE,null,null);
	}

	public KeyFloat add(float timeDiff, float value, int dir, EasingIF ease, KeyEvent keyEvent) 
	{
		return set(freeTimer.timer.getTime()+timeDiff, value, dir,ease,keyEvent);
	}

	public KeyFloat add(float timeDiff, float value, int dir, KeyEvent event)
	{
		return set(freeTimer.timer.getTime()+timeDiff, value, dir,null,event);
	}

	public KeyFloat add(float timeDiff, float value, int dir, EasingIF easing)
	{
		return set(freeTimer.timer.getTime()+timeDiff, value, dir,easing,null);
	}

	public KeyFloat add(float timeDiff, float value, int dir)
	{
		return set(freeTimer.timer.getTime()+timeDiff, value, dir,null,null);
	}

	public KeyFloat set(float time, float value)
	{
		return set(time, value, EASING_NONE,null,null);
	}

	public KeyFloat add(float timeDiff, float value, KeyEvent keyEvent) {
		return set(timeDiff, value, EASING_NONE,null,keyEvent);
	}

	public KeyFloat set(float time, float value, int dir)
	{
		return set(time, value, dir,null,null);
	}

	public KeyFloat set(float time, float value, int dir, EasingIF ease)
	{
		return set(time, value, dir,ease,null);
	}

	/**
	 * adds a new key to the tweener. 
	 *
	 * @param  time		absolute time of the new key
	 * @param  value 	float value of the new key
	 * @param  dir 		direction (in / out / inout /)
	 * @param  event 	an event to execute, when the key is reached.
	 */
	public KeyFloat set(float time, float value, int dir, EasingIF ease, KeyEvent event)
	{
		KeyFloat ret=null;
		if(!isKeyframed)
		{
			keyframes[0].setValue(value);
			if(event!=null)keyframes[0].addKeyEventListener(event);
			ret=keyframes[0];
		}
		else
		{
			boolean alldone=false;
			for(int i=0;i<keyframes.length;i++)
			{
				if(keyframes[i].getTime()==time)
				{
					keyframes[i].setValue(value);
					keyframes[i].setEasingDirection(dir);
					keyframes[i].addKeyEventListener(event);
					normalize();
					alldone=true;
					ret=keyframes[i];
				}
			}

			if(!alldone)
			{
				if(time>keyframes[keyframes.length-1].getTime())
				{
					keyframes=(KeyFloat[])resizeArray(keyframes,1);
					keyframes[keyframes.length-1]=new KeyFloat(time,value,dir);
					keyframes[keyframes.length-1].addKeyEventListener(event);
					ret=keyframes[keyframes.length-1];
				}
				else				
				if(time>keyframes[0].getTime() && time<keyframes[keyframes.length-1].getTime())
				{
					int keystart=getKeyIndex(time);
					keyframes=(KeyFloat[])resizeArray(keyframes,1);

					for(int i=keyframes.length-1;i>keystart+1;i--)
					{
						keyframes[i]=keyframes[i-1];
					}
					keyframes[keystart+1]=new KeyFloat(time,value,dir);
					keyframes[keystart+1].addKeyEventListener(event);
					ret=keyframes[keystart+1];

				}
				normalize();
			}
		}
		
		if(ease!=null)
			ret.setEasing(ease);
		else
			ret.setEasing(defaultEasing);
		
		return ret;
	}

	
	private KeyFloat getMaxValueKey()
	{
		KeyFloat max=new KeyFloat(0, -9999999f, EASING_NONE);
		for(int i=0;i<keyframes.length;i++){
			if(keyframes[i].getValue()>max.getValue())max=keyframes[i];
		}
		return max;
	}
	
	private KeyFloat getMinValueKey()
	{
		KeyFloat min=new KeyFloat(0, 9999999f, EASING_NONE);
		for(int i=0;i<keyframes.length;i++){
			if(keyframes[i].getValue()<min.getValue())min=keyframes[i];
		}
		return min;
	}
	
	float allValue=0.0f;
	KeyFloat min;
	
	private void normalize()
	{
		min=getMinValueKey();
		KeyFloat max=getMaxValueKey();

		allValue=Math.abs(max.getValue() - min.getValue());

//		System.out.println(min.getValue()+" min !!");
//		System.out.println(max.getValue()+" max !!");
//		System.out.println("diff: "+allValue);

		for(int i=0;i<keyframes.length;i++)
		{
			float idiff=Math.abs(keyframes[i].getValue() - min.getValue());
			keyframes[i].setInternalValue(idiff/allValue);
		}
	}
	


	
	/**
	 * @param time
	 * @return
	 */
	public int getKeyIndex(float time)
	{
		int which=0;
		for(int i=0;i<keyframes.length;i++)
		{
			if(keyframes[i].getTime()<=time)
			{
				which=i; 
			}
		}
		return which;
	}




	public float getValue(float time)
	{
		return getValue(time, true);
	}
	
	public float getValue(float time, boolean notify)
	{
		if(!isKeyframed)
		{
			if(notify && 0!=lastNotifiedKey)
			{
				lastNotifiedKey=0;
				keyframes[0].notifyKeyEventListeners();
			}
			return keyframes[0].getValue();
		}
		else 
		{
			
			// reached last key
			if(time>=keyframes[keyframes.length-1].getTime())
			{
				if(notify && keyframes[keyframes.length-1].hasKeyEventListeners())
				{
					lastNotifiedKey=keyframes.length-1;
					keyframes[lastNotifiedKey].notifyKeyEventListeners();
				}

				return keyframes[keyframes.length-1].getValue();
	
			}
				

			int which=getKeyIndex(time);
			int next=which+1;

			float ttime=time-keyframes[which].getTime();
			float ratio=0f;

			if(notify && lastNotifiedKey!=which && keyframes[which].hasKeyEventListeners())
			{
				lastNotifiedKey=which;
				keyframes[which].notifyKeyEventListeners();	
			}
			
			
			EasingIF theEasing=keyframes[which].getEasing();

			// calculate the ratio between the two values... 
			if(keyframes[which].getEasingDirection()==EASING_IN)
				ratio=theEasing.easeIn( ttime, 0, 1, keyframes[next].getTime()-keyframes[which].getTime());
			else
				if(keyframes[which].getEasingDirection()==EASING_OUT)
					ratio=theEasing.easeOut( ttime, 0, 1, keyframes[next].getTime()-keyframes[which].getTime());
				else
					if(keyframes[which].getEasingDirection()==EASING_INOUT)
						ratio=theEasing.easeInOut(ttime, 0, 1, keyframes[next].getTime()-keyframes[which].getTime());
					else
						if(keyframes[which].getEasingDirection()==EASING_NONE)
							ratio=theEasing.easeNone( ttime, 0, 1, keyframes[next].getTime()-keyframes[which].getTime());


			float r=1.0f;

			// calculate internal value using ratio
			float di=Math.abs(keyframes[next].getInternalValue()- keyframes[which].getInternalValue());
			
			if(keyframes[next].getValue()>keyframes[which].getValue())
			{
				r=keyframes[which].getInternalValue()+di*ratio;
			}
			else
			{
				r=keyframes[which].getInternalValue()-di*ratio;
			}

			

			// get real value
			return r*allValue+min.getValue();
		}
	}


	private int getLastkeyIndex() {
		return keyframes.length-1;
	}

	private KeyFloat[] resizeArray(KeyFloat[] storedObjects, int growthFactor) 
	{
		if(storedObjects == null){ 
			throw new NullPointerException();
		}
		int currentCapacity = storedObjects.length;
		int newCapacity = currentCapacity + ((currentCapacity * growthFactor) / 100);
		if (newCapacity == currentCapacity ) {
			newCapacity++;
		}
		KeyFloat[] newStore = new KeyFloat[ newCapacity ];
		System.arraycopy( storedObjects, 0, newStore, 0, storedObjects.length );
		return newStore;
	}


	public void printKeys()
	{
		System.out.println("------------------------------------");

		System.out.println(
				"time \t"+
				"intervalue \t"+
				"value \t"+
				"events \t");

		for(int i=0;i<keyframes.length;i++)
		{
			System.out.println(
					keyframes[i].getTime()+" \t "+
					keyframes[i].getInternalValue()+"\t\t"+
					keyframes[i].getValue()+"\t"+
					keyframes[i].hasKeyEventListeners());
		}
	}



	public int getIntValue() {
		return (int)getValue(freeTimer.timer.getTime(),true);
	}
	
	public void clear(float f) {
		initKeyframes();
		keyframes[0].setValue(f);
	}
	
	public void clear() {
		float val=getLastKey().getValue();
		clear(val);
	}

	public float getTime(){
		return freeTimer.timer.getTime();
	}
	
	public boolean isAnimFinished(){
		return getTime() > getLastKey().getTime();
	}
	
	public void setEasing(EasingIF easing) {
		this.defaultEasing=easing;
	}

	public EasingIF getEasing() {
		return defaultEasing;
	} 

	public KeyFloat getLastKey() {
		return keyframes[keyframes.length-1];
	}
	
	public float getValue() {
		return getValue(freeTimer.timer.getTime());
	}

	public KeyFloat getKey(float time)	{
		return keyframes[getKeyIndex(time)];
	}

	public int getIntValue(float time) {
		return (int)getValue(time,true);
	}

	public int getIntValue(float time, boolean notify) {
		return (int)getValue(time,notify);
	}

	public void resetTimer() {
		freeTimer.timer.reset();
	}

}
