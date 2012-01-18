package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
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
	private GroupLayer gLayer;
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private String jsonPath = "layouts/SceneLevels.json";
	private float depth = 0;
	
	public SceneLevels () {
		initImageLayouts();
		gLayer.setVisible(false);
	}

	private void initImageLayouts() {
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);
	    
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
	    	      
	    	      if ((resX == GameConstants.ScreenProperties.width) && (resY == GameConstants.ScreenProperties.height)) {
	    	    	  Json.Object objBgImage = resolution.getObject("bg_image");
	    	    	  String bgImagePath = objBgImage.getString("path");
	    	    	  // create and add background image layer
	    	    	  
	    	    	  bgImage = (Image)CachedResource.getInstance().getResource(bgImagePath);
	    	    	  ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    	    	  bgLayer.setDepth(depth);
					  gLayer.add(bgLayer);
	    	    	  
					  depth++; 
	    	    	  // Reading level buttons
	    	    	  Json.Array arrButton = resolution.getArray("level_button");
	    	    	  for (int j = 0; j < arrButton.length(); j++) {
	    	    		  Json.Object levelButton = arrButton.getObject(j);
	    	    		  int x = levelButton.getInt("x");
	    	    		  int y = levelButton.getInt("y");
	    	    		  String path = levelButton.getString("path");
	    	    		  Button button = new Button(x, y, path);
	    	    		  button.setLayerDepth(depth);
	    	    		  gLayer.add(button.getLayer());
	    	    		  buttonList.add(button);
	    	    		  
	    	    		  button.setEventListener(new ButtonEventListener() {
	    	    			  @Override
	    	    			  public void onClick() {
	    	    				  SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "levels/level1.json"); // TODO: Implement passing the correct data to start level
	    	    			  }
	    	    		  });
	    	    	  }
	    	    	  
	    	    	  // Reading back button
	    	    	  Json.Object backButton = resolution.getObject("back_button");
	    	    	  int x = backButton.getInt("x");
    	    		  int y = backButton.getInt("y");
    	    		  String path = backButton.getString("path");
    	    		  Button button = new Button(x, y, path);
    	    		  button.setLayerDepth(depth);
    	    		  gLayer.add(button.getLayer());
    	    		  buttonList.add(button);
    	    		  
    	    		  button.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick() {
    	    				  SceneNavigator.getInstance().runScene(eScenes.MENU,null);
    	    			  }
    	    		  });
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("SceneLevels.json file does not exist! ", err);
	      }
	    });
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
