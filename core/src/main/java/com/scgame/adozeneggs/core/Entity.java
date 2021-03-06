package com.scgame.adozeneggs.core;

import playn.core.ImageLayer;

/**
 * @author Caner
 *
 */
enum eEntity {
	BASKET, EGG
}
public abstract class Entity {
	public eEntity type;
	public abstract Vect2d getPosition() ;
	public abstract void setPosition(Vect2d position) ;
	public abstract void paint(float alpha) ;
	public abstract void update(float delta) ;
	public abstract float getHeight();
	public void clicked(Vect2d pointer){};
	public void init(){}
}
