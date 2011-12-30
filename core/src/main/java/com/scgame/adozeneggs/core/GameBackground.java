package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;

public class GameBackground extends GroupEntity {
	private List<GraphicsEntity> entities = new ArrayList<GraphicsEntity>();
	GroupLayer groupLayer;
	Vect2d position;
	public GameBackground() {
		this.groupLayer = graphics().createGroupLayer();
	}
	public void addItsEntity(GraphicsEntity entity){
		groupLayer.add(entity.getTopImageLayer());
		entities.add(entity);
	}
	@Override
	public void paint(float alpha) {
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).paint(alpha);
	}
	@Override
	public void update(float delta) {
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).update(delta);
	}
	@Override
	public GroupLayer getGroupLayer() {
		return groupLayer;
	}
	@Override
	public Vect2d getPosition() {
		return position;
	}
	@Override
	public void setPosition(Vect2d position) {
		this.position=position;
	}
}