package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;

public class GamePauseScreen extends GroupEntity {
	private Vect2d position;
	private GroupLayer groupLayer;
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	
	public GamePauseScreen() {
		this.groupLayer = graphics().createGroupLayer();	
	}
	
	@Override
	public void paint(float alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GroupLayer getGroupLayer() {
		return groupLayer;
	}

	@Override
	public Vect2d getPosition() {
		return position.copy();
	}

	@Override
	public void setPosition(Vect2d position) {
		groupLayer.setTranslation(position.x, position.y);
		this.position=position;
	}

	public void setBackground(String imagePath) {
		bgImage = (Image) CachedResource.getInstance().getResource(imagePath);
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		groupLayer.add(bgLayer);
	}
	
	// Button to return main menu from pause screen
	public void addMainButton(float x, float y, String path) {
		Button button = new Button(x, y, path);
		final ImageLayer layer = button.getLayer();
		groupLayer.add(layer);
		button.setEventListener(new ButtonEventListener() {
			
			@Override
			public void onClick(Vect2d position) {
				SceneNavigator.getInstance().runScene(eScenes.MENU, null);	
			}
		});
		buttonList.add(button);
	}
	
	// Button to restart the game from pause screen
	public void addRestartButton(float x, float y, String path) {
		Button button = new Button(x, y, path);
		final ImageLayer layer = button.getLayer();
		groupLayer.add(layer);
		button.setEventListener(new ButtonEventListener() {
			
			@Override
			public void onClick(Vect2d position) {
				SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "restart");	
			}
		});
		buttonList.add(button);
	}
	
	// Button to game play from pause screen
	public void addResumeButton(float x, float y, String path) {
		Button button = new Button(x, y, path);
		final ImageLayer layer = button.getLayer();
		groupLayer.add(layer);
		button.setEventListener(new ButtonEventListener() {
			
			@Override
			public void onClick(Vect2d position) {
				SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "resume");	
			}
		});
		buttonList.add(button);
	}
	
	public synchronized void firePointerEndEvent(Vect2d pointer) {
		pointer.x = pointer.x - position.x;
		pointer.y = pointer.y - position.y;
		
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).clicked(pointer);
		}
	}
	
	public void show() {
		groupLayer.setDepth(1000);
	}
	
	public void hide() {
		groupLayer.setDepth(-1000);
	}

}
