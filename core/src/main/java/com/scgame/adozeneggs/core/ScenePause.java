package com.scgame.adozeneggs.core;

import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Pointer;

public class ScenePause extends Scene {
	
	private GroupLayer gLayer;
	private List<Button> buttonList = new ArrayList<Button>();
	
	public ScenePause() {
		initImageLayouts();
		gLayer.setVisible(false);
	}
	
	private void initImageLayouts() {
	}
	
	@Override
	public void init(Object data) {
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
