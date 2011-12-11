package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class SceneGameplay extends Scene {
	private final adozeneggs adozeneggs;
	private GroupLayer gLayer = null;
	Button backButton; 

	public SceneGameplay (adozeneggs adozeneggs) {
	    this.adozeneggs = adozeneggs;
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		
	}
	
	public void init(int level) {
		System.out.println("level " + level);
		
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
	    // create and add background image layer
	    Image bgImage = assetManager().getImage("images/bg.png");
	    ImageLayer bgLayer = graphics().createImageLayer(bgImage);    
	    gLayer.add(bgLayer);
	    
	    // create and add loading image layer
	    Image ldImage = assetManager().getImage("images/loading.png");
	    ImageLayer ldLayer = graphics().createImageLayer(ldImage);
	    gLayer.add(ldLayer);
	    
	    backButton = new Button(0.10f, 0.90f, "images/back-button.png");
		gLayer.add(backButton.getLayer());
		
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		if (backButton.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneLevels();
	    		}
	    	}
	    });
	  
	}

	@Override
	public void shutdown() {
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.destroy();
			gLayer = null;
		}
	}

}
