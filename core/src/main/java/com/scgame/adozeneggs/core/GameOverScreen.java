package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

public class GameOverScreen extends GroupEntity{
	private static final String GAMEOVER_HIGH_TOP_IMAGE_PATH = "images/game_over_high.png";
	private static final String GAMEOVER_MEDIUM_TOP_IMAGE_PATH = "images/game_over_medium.png";
	private static final String GAMEOVER_LOW_TOP_IMAGE_PATH = "images/game_over_medium.png";
	
	private Vect2d position;
	private ImageLayer imageLayer;
	private Image bgImage;
	private GroupLayer groupLayer;

	public GameOverScreen(){
		this.groupLayer = graphics().createGroupLayer();
	    if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.HIGH){
	    	bgImage = (Image)CachedResource.getInstance().getResource(GAMEOVER_HIGH_TOP_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.MEDIUM){
	    	bgImage = (Image)CachedResource.getInstance().getResource(GAMEOVER_MEDIUM_TOP_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.LOW){
	    	bgImage = (Image)CachedResource.getInstance().getResource(GAMEOVER_LOW_TOP_IMAGE_PATH);
	    }
	    this.imageLayer = graphics().createImageLayer();
	    imageLayer.setImage(bgImage);
	    groupLayer.add(imageLayer);
	}
	public void init(){
		setPosition(new Vect2d(10000, 10000));
	}
	public void show(){
		setPosition(new Vect2d(0, 0));
	}
	public Vect2d getPosition() {
		return position.copy();
	}

	public void setPosition(Vect2d position) {
		this.position=position;
	}
	@Override
	public void paint(float alpha) {
		groupLayer.setTranslation(position.x, position.y);
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public float getHeight() {
		return this.imageLayer.height();
	}
	@Override
	public GroupLayer getGroupLayer() {
		return groupLayer;
	}
}
