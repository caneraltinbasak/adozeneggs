package com.scgame.adozeneggs.core;

import playn.core.GroupLayer;

public abstract class GroupEntity extends Entity {
	public abstract void paint(float alpha);
	public abstract void update(float delta);
	public abstract GroupLayer getGroupLayer();

}
