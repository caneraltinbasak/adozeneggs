package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

public class AchievementScreen {
	private Vect2d position;
	private GroupLayer groupLayer;
	private ImageLayer imageLayer;
	private Image imgFirstFastJump;
	private Image imgFirstThreeStarJump;
	
	public AchievementScreen() {
		this.groupLayer = graphics().createGroupLayer();	
		groupLayer.setDepth(-2000);
		
		imgFirstFastJump = (Image)CachedResource.getInstance().getResource(SAConstants.IMG_FIRST_FAST_JUMP_PATH);
		imgFirstThreeStarJump = (Image)CachedResource.getInstance().getResource(SAConstants.IMG_FIRST_THREE_STAR_JUMP_PATH);
		
		imageLayer = graphics().createImageLayer();    
		imageLayer.setImage(imgFirstFastJump);
	    groupLayer.add(imageLayer);
	}

	public GroupLayer getGroupLayer() {
		return groupLayer;
	}

	public Vect2d getPosition() {
		return position.copy();
	}

	public void setPosition(Vect2d position) {
		groupLayer.setTranslation(position.x, position.y);
		this.position=position;
	}

	public void setAchievement(String achievement) {
		if (achievement.equals(SAConstants.ACH_FIRST_FAST_JUMP)) {
			imageLayer.setImage(imgFirstFastJump);
		}
		
		if (achievement.equals(SAConstants.ACH_FIRST_THREE_STAR_JUMP)) {
			imageLayer.setImage(imgFirstThreeStarJump);
		}
		show();
	}
	
	
	
	public void show() {
		groupLayer.setDepth(2000);
	}
	
	public void hide() {
		groupLayer.setDepth(-2000);
	}
}
