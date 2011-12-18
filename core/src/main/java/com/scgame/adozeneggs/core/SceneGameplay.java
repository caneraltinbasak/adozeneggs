package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import pythagoras.f.Transform;

public class SceneGameplay extends Scene {
	private GroupLayer layer;
	private ImageLayer[] segments;
	private float dx = 10, dy = 5, dd = 1;
	private boolean gamePaused =  false;
	private List<Button> buttonList = new ArrayList<Button>();

	public SceneGameplay (SceneNavigator sceneNavigator) {
	    
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	  public void init() {
	    // create a group layer to hold everything
	    layer = graphics().createGroupLayer();
	    graphics().rootLayer().add(layer);

	    Image bgImage;
	    // create and add background image layer
	    if (graphics().height() == 480 && graphics().width() == 320) {
	    	bgImage = assetManager().getImage("images/bg_medium.png");
	    }
	    else{
	    	bgImage = assetManager().getImage("images/bg_high.png");
	    }
	    
	    final ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    bgLayer.setDepth(2);
	    layer.add(bgLayer);
	    

	    // create our snake segments
	    Image segImage = assetManager().getImage("images/pea.png");
	    segments = new ImageLayer[25];
	    for (int ii = 0; ii < segments.length; ii++) {
	      segments[ii] = graphics().createImageLayer(segImage);
	      segments[ii].setDepth(50);
	      layer.add(segments[ii]);
	    }
	    
	    final Button pauseButton = new Button(10, 400, "images/pause_medium.png");
	    pauseButton.addCallback(new ButtonCallback() {
			
			@Override
			public void error(Throwable err) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void done() {
				// TODO Auto-generated method stub
				pauseButton.setLayerDepth(70);
				layer.add(pauseButton.getLayer());
				buttonList.add(pauseButton);
			}
		});
	    
	    pauseButton.setEventListener(new ButtonEventListener() {
			@Override
			public void onClick(Event event) {
				if (gamePaused) {
					gamePaused = false;
				}
				else {
					gamePaused = true;
				}
			}
		});
	     
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		firePointerEndEvent(event);		
	    	}
	    });
	  }
	
	private synchronized void firePointerEndEvent(Pointer.Event event) {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).clicked(event);
		}
	}
	
	  @Override
	  public void update(float delta) {
		if (gamePaused == false) {
		    // the tail segments play follow the leader
		    for (int ii = segments.length-1; ii > 0; ii--) {
		      ImageLayer cur = segments[ii], prev = segments[ii-1];
		      Transform t1 = cur.transform(), t2 = prev.transform();
		      t1.setTx(t2.tx());
		      t1.setTy(t2.ty());
		      t1.setUniformScale(t2.uniformScale());
		      cur.setDepth(prev.depth());
		    }
	
		    // and the head segment leads the way
		    ImageLayer first = segments[0];
		    Transform t = first.transform();
		    float nx = t.tx() + dx, ny = t.ty() + dy, nd = first.depth() + dd;
		    if (nx < 0 || nx > graphics().width()) {
		      dx *= -1;
		      nx += dx;
		    }
		    if (ny < 0 || ny > graphics().height()) {
		      dy *= -1;
		      ny += dy;
		    }
		    if (nd < 25 || nd > 125) {
		      dd *= -1;
		      nd += dd;
		    }
		    t.setTx(nx);
		    t.setTy(ny);
		    t.setUniformScale(nd/50f);
		    first.setDepth(nd);
		}
	  }

	  @Override
	  public void shutdown() {
	    segments = null;
	    layer.destroy();
	    layer = null;
	    
	  }
}
