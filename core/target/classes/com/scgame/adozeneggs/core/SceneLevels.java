package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.ResourceCallback;

public class SceneLevels extends Scene {
	private final adozeneggs adozeneggs;
	private GroupLayer gLayer;
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private String jsonPath = "layouts/SceneLevels.json";
	private int screenWidth;
	private int screenHeight;
	
	public SceneLevels (adozeneggs adozeneggs) {
		this.adozeneggs = adozeneggs;
		this.screenHeight = adozeneggs.getScreenHeight();
	    this.screenWidth = adozeneggs.getScreenWidth();
	    
	    initImageLocations();
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initImageLocations() {
	    // parsing json for x/y coordinates of all the images on screen
	    assetManager().getText(jsonPath, new ResourceCallback<String>() {
	      @Override
	      public void done(String json) {
	    	  Json.Object document = json().parse(json);
	    	  Json.Object imgLocation = document.getObject("image_location");
	    	  // parse resolutions
	    	  Json.Array arrRes = imgLocation.getArray("resolution");
	    	  
	    	  for (int i = 0; i < arrRes.length(); i++) {
	    		  Json.Object resolution = arrRes.getObject(i);
	    	      int resX = resolution.getInt("x");
	    	      int resY = resolution.getInt("y");
	    	      
	    	      if ((resX == screenWidth) && (resY == screenHeight)) {
	    	    	  Json.Object objBgImage = resolution.getObject("bg_image");
	    	    	  String bgImagePath = objBgImage.getString("path");
	    	    	  // create and add background image layer
	    	    	  bgImage = assetManager().getImage(bgImagePath);
	    	    	  
	    	    	  // Reading buttons
	    	    	  Json.Array arrButton = resolution.getArray("button");
	    	    	  for (int j = 0; j < arrButton.length(); j++) {
	    	    		  Json.Object objButton = arrButton.getObject(j);
	    	    		  // id is to understand which button it is
	    	    		  final String id = objButton.getString("id");
	    	    		  int x = objButton.getInt("x");
	    	    		  int y = objButton.getInt("y");
	    	    		  String path = objButton.getString("path");
	    	    		  Button button = new Button(x, y, path);
	    	    		  
	    	    		  button.setEventListener(new ButtonEventListener() {
	    	    			  @Override
	    	    			  public void onClick(Event event) {
	    	    				  adozeneggs.runSceneGameplay(id);
	    	    			  }
	    	    		  });
	    	    		  buttonList.add(button);
	    	    	  }	 
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	// TODO Should be error log here
	      }
	    });
	}
	
	@Override
	public void init() {
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
	    // create and add background image layer
	    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    gLayer.add(bgLayer);
	    
	    for (int i = 0; i < buttonList.size(); i++) {
	    	gLayer.add(buttonList.get(i).getLayer());
	    }
	
		// add a listener for pointer (mouse, touch) input
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
	public void shutdown() {
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.destroy();
			gLayer = null;
		}
	}

}
