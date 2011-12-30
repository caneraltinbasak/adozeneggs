package com.scgame.adozeneggs.core;
public class Vect2d
{
	public float x;
	public float y;
	public Vect2d copy(){
		Vect2d val=new Vect2d(this.x,this.y);
		return val;
	}
	public Vect2d(float x,float y)
	{
		this.x=x;
		this.y=y;
	}
	public Vect2d add(Vect2d position){
		Vect2d val=new Vect2d(0,0);
		val.x=this.x+position.x;
		val.y=this.y+position.y;
		return val;
	}
	public Vect2d subtract(Vect2d position){
		Vect2d val=new Vect2d(0,0);
		val.x=this.x-position.x;
		val.y=this.y-position.y;
		return val;
	}
	public Vect2d multiply(float scale){
		Vect2d val=new Vect2d(0,0);
		val.x=this.x*scale;
		val.y=this.y*scale;
		return val;
	}
	public float length(){
		return (float)Math.sqrt(this.x*this.x+this.y*this.y);
	}
	public Vect2d normalize(){
		if(length()!=0){
			this.x/=length();
			this.y/=length();
		}else{
			this.x=0;
			this.y=0;
		}
		return this;
	}
	public Vect2d nNormalize(){
		Vect2d val=new Vect2d(0,0);
		if(length()!=0){
			val.x=this.x/length();
			val.y=this.y/length();
		}
		return val;
	}
	public float dot(Vect2d v2) 
	{
		return (this.x*v2.x+this.y*v2.y);
	}
	@Override
	public String toString() {
		return ("x:" + this.x + "\ny: "+this.y+"\n");
	}
}