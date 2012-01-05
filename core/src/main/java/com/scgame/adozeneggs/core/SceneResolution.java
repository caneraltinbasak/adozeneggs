package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.ResourceCallback;

public class SceneResolution extends Scene {
	private GroupLayer gLayer = null;
	private List<NonCachedButton> buttonList = new ArrayList<NonCachedButton>();
	
	public SceneResolution() {
	}

	@Override
	public void init(Object data) {
		
		SAHandler.getInstance().setHighScores(99);
		
		
		System.out.println(SAHandler.getInstance().getHighScoreToday().user);
		System.out.println(SAHandler.getInstance().getHighScoreToday().score);
		System.out.println(SAHandler.getInstance().getHighScoreToday().dayOfMonth);
		System.out.println(SAHandler.getInstance().getHighScoreToday().month);
		System.out.println(SAHandler.getInstance().getHighScoreToday().year);
		
		
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
	    // create and add background image layer
	    Image bgImage = assetManager().getImage("images/bg_medium.png");
	    bgImage.addCallback(new ResourceCallback<Image>() {
			@Override
			public void error(Throwable err) {
				log().error("SceneResolution.init : Error loading backgroung image!", err);
			}
			@Override
			public void done(Image resource) {
				ImageLayer bgLayer = graphics().createImageLayer(resource);  
				bgLayer.setDepth(0);
			    gLayer.add(bgLayer);
			}
		});
	    	    
	    // Adding resolution button
		final NonCachedButton res1 = new NonCachedButton(50, 50, "images/res1.png");
		res1.addCallback(new ButtonCallback() {
			
			@Override
			public void error(Throwable err) {
				log().error("Button could not be created", err);
			}
			
			@Override
			public void done() {
				res1.setLayerDepth(1);
				gLayer.add(res1.getLayer());
			}
		});
		
		// Adding resolution button
		final NonCachedButton res2 = new NonCachedButton(50, 150, "images/res2.png");
		res2.addCallback(new ButtonCallback() {
			
			@Override
			public void error(Throwable err) {
				log().error("Button colud not be created", err);
				
			}
			
			@Override
			public void done() {
				res2.setLayerDepth(2);
				gLayer.add(res2.getLayer());
				
			}
		});
		
		buttonList.add(res1);
		buttonList.add(res2);
		
		res1.setEventListener(new ButtonEventListener() {
			@Override
			public void onClick(Vect2d pointer) {
				GameConstants.ScreenProperties.height = 1024;
				GameConstants.ScreenProperties.width = 768;
				GameConstants.ScreenProperties.gQuality= GameConstants.ScreenProperties.HIGH;
				graphics().setSize(768, 1024);
				SceneNavigator.getInstance().runScene(eScenes.LOADING, null);
			}
		});
		
		res2.setEventListener(new ButtonEventListener() {	
			@Override
			public void onClick(Vect2d pointer) {
				GameConstants.ScreenProperties.height = 480;
				GameConstants.ScreenProperties.width = 320;
				GameConstants.ScreenProperties.gQuality= GameConstants.ScreenProperties.MEDIUM;
				graphics().setSize(320, 480);
				SceneNavigator.getInstance().runScene(eScenes.LOADING, null);
			}
		});
		
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		Vect2d pointer = new Vect2d(event.x(), event.y());
	    		firePointerEndEvent(pointer);		
	    	}
	    });
	    
	    gLayer.setVisible(true);
	}
	

	private synchronized void firePointerEndEvent(Vect2d pointer) {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).clicked(pointer);
		}
	}
	
	@Override
	public void shutdown() {
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.setVisible(false);
		}
	}

}
