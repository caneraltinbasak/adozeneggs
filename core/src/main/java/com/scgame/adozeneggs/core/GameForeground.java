package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;

public class GameForeground extends GroupEntity {
	private Vect2d position;
	private List<GraphicsEntity> entities = new ArrayList<GraphicsEntity>();
	private GroupLayer groupLayer;
	private float scrollingSpeed = 0.0f;
	private Vect2d scrollPosition = null;
	public GameForeground() {
		this.groupLayer = graphics().createGroupLayer();
	}
	public void addItsEntity(GraphicsEntity entity){
		groupLayer.add(entity.getImageLayer());
		entities.add(entity);
	}
	@Override
	public void paint(float alpha) {
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).paint(alpha);
		if(scrollPosition != null)
			groupLayer.setTranslation(position.x, position.y);
	}
	@Override
	public void update(float delta) {
		if(scrollPosition != null)
		{
			position.y = position.y + GameConstants.PhysicalProperties.verticalInPixels(scrollingSpeed) * delta / 1000;
			if(scrollPosition.y < position.y)
			{
				scrollingSpeed = 0;
				scrollPosition = null;
			}
		}
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).update(delta);
	}
	@Override
	public GroupLayer getGroupLayer() {
		return groupLayer;
	}
	@Override
	public Vect2d getPosition() {
		return position.copy();
	}
	// setPosition will also be used for scrolling
	@Override
	public void setPosition(Vect2d position) {
		groupLayer.setTranslation(position.x, position.y);
		this.position=position;
	}
	public void scrollTo(Vect2d scrollPosition){
		if(scrollingSpeed == 0)
			scrollingSpeed = GameConstants.PhysicalProperties.ForegroundScrollSpeed;
		else
			scrollingSpeed *= 2;
		this.scrollPosition = scrollPosition;
	}
}
