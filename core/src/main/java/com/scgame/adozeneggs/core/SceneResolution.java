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
import playn.core.Pointer.Event;

public class SceneResolution extends Scene {
	private GroupLayer gLayer = null;
	private List<Button> buttonList = new ArrayList<Button>();
	
	public SceneResolution() {
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(Object data) {
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
		final Button res1 = new Button(50, 50, "images/res1.png");
		res1.addCallback(new ButtonCallback() {
			
			@Override
			public void error(Throwable err) {
				log().error("Button colud not be created", err);
			}
			
			@Override
			public void done() {
				res1.setLayerDepth(1);
				gLayer.add(res1.getLayer());
			}
		});
		
		// Adding resolution button
		final Button res2 = new Button(50, 150, "images/res2.png");
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
			public void onClick(Event event) {
				GameConstants.ScreenProperties.height = 1024;
				GameConstants.ScreenProperties.width = 768;
				GameConstants.ScreenProperties.gQuality= GameConstants.ScreenProperties.HIGH;
				graphics().setSize(768, 1024);
				SceneNavigator.getInstance().createScenes();
				SceneNavigator.getInstance().runScene(eScenes.MENU, null);
			}
		});
		
		res2.setEventListener(new ButtonEventListener() {	
			@Override
			public void onClick(Event event) {
				GameConstants.ScreenProperties.height = 480;
				GameConstants.ScreenProperties.width = 320;
				GameConstants.ScreenProperties.gQuality= GameConstants.ScreenProperties.LOW;
				graphics().setSize(320, 480);
				SceneNavigator.getInstance().createScenes();
				SceneNavigator.getInstance().runScene(eScenes.MENU, null);
			}
		});
		
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		firePointerEndEvent(event);		
	    	}
	    });
	    
	    gLayer.setVisible(true);
	}
	

	private synchronized void firePointerEndEvent(Pointer.Event event) {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).clicked(event);
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
