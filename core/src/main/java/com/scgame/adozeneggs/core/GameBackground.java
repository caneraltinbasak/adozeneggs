package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;

public class GameBackground extends ScrollableGroupEntity {
	private static final String BG_HIGH_LAYER0_0_IMAGE_PATH = "images/layer0_0.png";
	private static final String BG_HIGH_LAYER1_0_IMAGE_PATH = "images/layer1_0.png";
	private static final String BG_HIGH_LAYER2_0_IMAGE_PATH = "images/layer2_0.png";
	private static final String BG_HIGH_LAYER0_1_IMAGE_PATH = "images/layer0_1.png";
	private static final String BG_HIGH_LAYER1_1_IMAGE_PATH = "images/layer1_1.png";
	private static final String BG_HIGH_LAYER2_1_IMAGE_PATH = "images/layer2_1.png";
	private static final String BG_HIGH_LAYER0_2_IMAGE_PATH = "images/layer0_2.png";
	private static final String BG_HIGH_LAYER1_2_IMAGE_PATH = "images/layer1_2.png";
	private static final String BG_HIGH_LAYER2_2_IMAGE_PATH = "images/layer2_2.png";
	private static final String BG_MEDIUM_LAYER0_0_IMAGE_PATH = "images/layer0_0.png";
	private static final String BG_MEDIUM_LAYER1_0_IMAGE_PATH = "images/layer1_0.png";
	private static final String BG_MEDIUM_LAYER2_0_IMAGE_PATH = "images/layer2_0.png";
	private static final String BG_MEDIUM_LAYER0_1_IMAGE_PATH = "images/layer0_1.png";
	private static final String BG_MEDIUM_LAYER1_1_IMAGE_PATH = "images/layer1_1.png";
	private static final String BG_MEDIUM_LAYER2_1_IMAGE_PATH = "images/layer2_1.png";
	private static final String BG_MEDIUM_LAYER0_2_IMAGE_PATH = "images/layer0_2.png";
	private static final String BG_MEDIUM_LAYER1_2_IMAGE_PATH = "images/layer1_2.png";
	private static final String BG_MEDIUM_LAYER2_2_IMAGE_PATH = "images/layer2_2.png";
	private static final String BG_LOW_LAYER0_0_IMAGE_PATH = "images/layer0_0.png";
	private static final String BG_LOW_LAYER1_0_IMAGE_PATH = "images/layer1_0.png";
	private static final String BG_LOW_LAYER2_0_IMAGE_PATH = "images/layer2_0.png";
	private static final String BG_LOW_LAYER0_1_IMAGE_PATH = "images/layer0_1.png";
	private static final String BG_LOW_LAYER1_1_IMAGE_PATH = "images/layer1_1.png";
	private static final String BG_LOW_LAYER2_1_IMAGE_PATH = "images/layer2_1.png";
	private static final String BG_LOW_LAYER0_2_IMAGE_PATH = "images/layer0_2.png";
	private static final String BG_LOW_LAYER1_2_IMAGE_PATH = "images/layer1_2.png";
	private static final String BG_LOW_LAYER2_2_IMAGE_PATH = "images/layer2_2.png";
	
	private List<ScrollableGroupEntity> entities = new ArrayList<ScrollableGroupEntity>();
	GroupLayer groupLayer;
	Vect2d position;
	public GameBackground() {
		this.groupLayer = graphics().createGroupLayer();
		BGScrolledGroup scrolledImage0 = null;
		BGScrolledGroup scrolledImage1 = null;
		BGScrolledGroup scrolledImage2 = null;
	    if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.HIGH){
			scrolledImage0 = new BGScrolledGroup(BG_HIGH_LAYER0_0_IMAGE_PATH,BG_HIGH_LAYER0_1_IMAGE_PATH,BG_HIGH_LAYER0_2_IMAGE_PATH);
			scrolledImage1 = new BGScrolledGroup(BG_HIGH_LAYER1_0_IMAGE_PATH,BG_HIGH_LAYER1_1_IMAGE_PATH,BG_HIGH_LAYER1_2_IMAGE_PATH);
			scrolledImage2 = new BGScrolledGroup(BG_HIGH_LAYER2_0_IMAGE_PATH,BG_HIGH_LAYER2_1_IMAGE_PATH,BG_HIGH_LAYER2_2_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.MEDIUM){
			scrolledImage0 = new BGScrolledGroup(BG_MEDIUM_LAYER0_0_IMAGE_PATH,BG_MEDIUM_LAYER0_1_IMAGE_PATH,BG_MEDIUM_LAYER0_2_IMAGE_PATH);
			scrolledImage1 = new BGScrolledGroup(BG_MEDIUM_LAYER1_0_IMAGE_PATH,BG_MEDIUM_LAYER1_1_IMAGE_PATH,BG_MEDIUM_LAYER1_2_IMAGE_PATH);
			scrolledImage2 = new BGScrolledGroup(BG_MEDIUM_LAYER2_0_IMAGE_PATH,BG_MEDIUM_LAYER2_1_IMAGE_PATH,BG_MEDIUM_LAYER2_2_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.LOW){
			scrolledImage0 = new BGScrolledGroup(BG_LOW_LAYER0_0_IMAGE_PATH,BG_LOW_LAYER0_1_IMAGE_PATH,BG_LOW_LAYER0_2_IMAGE_PATH);
			scrolledImage1 = new BGScrolledGroup(BG_LOW_LAYER1_0_IMAGE_PATH,BG_LOW_LAYER1_1_IMAGE_PATH,BG_LOW_LAYER1_2_IMAGE_PATH);
			scrolledImage2 = new BGScrolledGroup(BG_LOW_LAYER2_0_IMAGE_PATH,BG_LOW_LAYER2_1_IMAGE_PATH,BG_LOW_LAYER2_2_IMAGE_PATH);
	    }
		groupLayer.add(scrolledImage0.getGroupLayer());
		groupLayer.add(scrolledImage1.getGroupLayer());
		groupLayer.add(scrolledImage2.getGroupLayer());

		entities.add(scrolledImage0);
		entities.add(scrolledImage1);
		entities.add(scrolledImage2);
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
	@Override
	public void scrollTo(float f) {
		
	}
}