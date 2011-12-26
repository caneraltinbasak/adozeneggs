package com.scgame.adozeneggs.core;

import playn.core.ImageLayer;

/**
 * @author Caner
 *
 */
public abstract class Entity {
	public abstract Vect2d getPosition() ;
	public abstract void setPosition(Vect2d position) ;
	public abstract void paint(float alpha) ;
	public abstract void update(float delta) ;
}
