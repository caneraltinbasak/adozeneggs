package com.scgame.adozeneggs.core;

import playn.core.ImageLayer;

public abstract class GraphicsEntity extends Entity {
	public abstract void paint(float alpha);
	public abstract void update(float delta);
	public abstract ImageLayer getImageLayer();

}
