package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class SceneLevels extends Scene {
	private final adozeneggs adozeneggs;
	private GroupLayer gLayer;
	private Button btLevel1;
	private Button btLevel2;
	private Button btLevel3;
	private Button btLevel4;
	private Button btLevel5;
	private Button btLevel6;
	private Button backButton;
	
	public SceneLevels (adozeneggs adozeneggs) {
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
	    Image bgImage = assetManager().getImage("images/bg.png");
	    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    gLayer.add(bgLayer);
	    
	    this.btLevel1 = new Button(0.10f, 0.10f, "images/l1.png");
		gLayer.add(btLevel1.getLayer());		
		this.btLevel2 = new Button(0.30f, 0.10f, "images/l2.png");
		gLayer.add(btLevel2.getLayer());
		this.btLevel3 = new Button(0.50f, 0.10f, "images/l3.png");
		gLayer.add(btLevel3.getLayer());
		this.btLevel4 = new Button(0.10f, 0.30f, "images/l4.png");
		gLayer.add(btLevel4.getLayer());
		this.btLevel5 = new Button(0.30f, 0.30f, "images/l5.png");
		gLayer.add(btLevel5.getLayer());
		this.btLevel6 = new Button(0.50f, 0.30f, "images/l6.png");
		gLayer.add(btLevel6.getLayer());
		this.backButton = new Button(0.10f, 0.90f, "images/back-button.png");
		gLayer.add(backButton.getLayer());
		
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		if (btLevel1.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(1);	
	    		}
	    		if (btLevel2.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(2);	
	    		}
	    		if (btLevel3.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(3);	
	    		}
	    		if (btLevel4.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(4);	
	    		}
	    		if (btLevel5.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(5);	
	    		}
	    		if (btLevel6.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneGameplay(6);	
	    		}
	    		if (backButton.hitTest(event.x(), event.y())) {
	    			adozeneggs.runSceneMenu();
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
