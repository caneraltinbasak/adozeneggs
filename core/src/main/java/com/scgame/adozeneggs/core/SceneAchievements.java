package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

import com.scgame.adozeneggs.tweener.Circ;
import com.scgame.adozeneggs.tweener.Tweener;

public class SceneAchievements extends Scene {
	private GroupLayer gLayer = null;
	Tweener testTween=new Tweener();

	boolean mousedown=false;


	float scrolly=0;
	float mousey=0;
	float mouseDiffy=0;
	
	ImageLayer layer;

	public SceneAchievements() {
		initLayout();
	    gLayer.setVisible(false);
	}
	
	private void initLayout() {    
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
	    // create and add background image layer
  	  	Image bgImage = (Image)CachedResource.getInstance().getResource("images/bg_medium.png");
  	  	ImageLayer bgLayer = graphics().createImageLayer(bgImage);
  	  	gLayer.add(bgLayer);
  	  	
  	    Image image = (Image)CachedResource.getInstance().getResource("images/highscores_medium.png");
	  	layer = graphics().createImageLayer(image);
	  	System.out.println("h : " + layer.height() + ", w : " + layer.width());

	  	
	  	gLayer.add(layer);
	}

	@Override
	public void init(Object data) {
		testTween=new Tweener(new Circ());

	    pointer().setListener(new Pointer.Adapter() {
			
			@Override
			public void onPointerStart(Pointer.Event event) {
				mousedown=true;
				mouseDiffy = 0;
				
			}
			
			@Override
			public void onPointerEnd(Pointer.Event event) {
				testTween.clear();
				testTween.add(0, 1f, Tweener.EASING_OUT);
				testTween.add(1, 0f);
				mousedown=false;
				System.out.println("onPointerEnd");
			}
			
			@Override
			public void onPointerDrag(Pointer.Event event) {
				mouseDiffy=mousey-event.y();
				scrolly-=mouseDiffy;
				mousey=event.y();
				
			}
		});
	    
	    gLayer.setVisible(true);
	}
	
	public void update(float delta) {
		if(scrolly<0)scrolly=0;
		if(scrolly>400)scrolly=400;
		
		scrolly-=testTween.getValue()*mouseDiffy;
		layer.setTranslation(0, scrolly);
	}
	
	public void paint(float alpha) {
	}

	@Override
	public void shutdown() {	
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.setVisible(false);
		}
	}
}
