package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class SceneMenu extends Scene {
	private final adozeneggs adozeneggs;
	private Button playButton;
	private Button optButton;
	private GroupLayer gLayer = null;
	
	public SceneMenu (adozeneggs adozeneggs) {
	    this.adozeneggs = adozeneggs;
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
	    // create and add background image layer
	    Image bgImage = assetManager().getImage("images/bg.png");
	    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    
	    gLayer.add(bgLayer);
	    
		this.playButton = new Button(0.10f, 0.10f, "images/newGame.png");
		gLayer.add(playButton.getLayer());
		this.optButton = new Button(0.10f, 0.30f, "images/options.png"); 
		gLayer.add(optButton.getLayer());
		
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		if (playButton.hitTest(event.x(), event.y())) {
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
