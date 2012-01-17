package com.scgame.adozeneggs.tweener;

import java.util.Vector;

import com.scgame.adozeneggs.tweener.EasingIF;
import com.scgame.adozeneggs.tweener.KeyEvent;

public class KeyFloat 
{

	private int easingDirection=Tweener.EASING_INOUT;
	
	private float value=0.0f;
	private float internalValue=0.0f;
	private float time=0.0f;
	private EasingIF easing;
	

	Vector keyEventListeners=null;
	
	public String toString()
	{
		String s="";
		//s+=value+" @ "+time;
		s+=easing.getClass().getSimpleName()+" (";
		if(easingDirection==-1)s+="none";
		if(easingDirection==0)s+="in";
		if(easingDirection==1)s+="out";
		if(easingDirection==2)s+="in&out";
		if(easingDirection==3)s+="none";
		s+=")";
		
		return s;
	}


	public KeyFloat(float time, float value, int dir)
	{
		this.easingDirection=dir;
		this.value=value;
		this.time=time;
	}
	
	
	public float getInternalValue() {
		return internalValue;
	}

	public float getTime() {
		return time;
	}


	public void setInternalValue(float value) {
		this.internalValue=value;
		
	}
	
	public void setEasingDirection(int dir)
	{
		easingDirection=dir;
	}
	
	public int getEasingDirection()
	{
		return easingDirection;
	}


	public void notifyKeyEventListeners() 
	{
		if(keyEventListeners!=null)
			for(int i=0;i<keyEventListeners.size();i++)
			{
				KeyEvent ke=(KeyEvent)keyEventListeners.elementAt(i);
				ke.KeyEvent(this);
			}
	}

	public void addKeyEventListener(KeyEvent ev)
	{
		if(ev==null)return;
		if(keyEventListeners==null)keyEventListeners=new Vector();
		
		keyEventListeners.addElement(ev);
		
		System.out.println("added "+keyEventListeners.size());
	}


	public boolean hasKeyEventListeners() 
	{
		if(keyEventListeners==null)return false;
		else
			if(keyEventListeners.size()==0)return false;
			else		
				return true;
	}

	
	public float getValue() {
		return value;
	}


	public void setValue(float value) {
		this.value = value;
	}

	public EasingIF getEasing() {
		return easing;
	}
	
	public void setEasing(EasingIF ease){
		this.easing=ease;
	}

	
}
