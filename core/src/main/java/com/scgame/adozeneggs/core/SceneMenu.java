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

public class SceneMenu extends Scene {
	private SceneNavigator sceneNavigator;
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneMenu.json";
	private int depth = 1;
	
	public SceneMenu (SceneNavigator sceneNavigator) {
	    this.sceneNavigator = sceneNavigator;   
	    initImageLayouts();
	    gLayer.setVisible(false);
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initImageLayouts() {
		gLayer = graphics().createGroupLayer();
	    graphics().rootLayer().add(gLayer);

	    // parsing json for x/y coordinates of all the images on screen
	    assetManager().getText(jsonPath, new ResourceCallback<String>() {
	      @Override
	      public void done(String json) {
	    	  Json.Object document = json().parse(json);
	    	  Json.Object imgLocation = document.getObject("image_layout");
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
	    	    	  bgImage = assetManager().getImage(bgImagePath);
	    	    	  bgImage.addCallback(new ResourceCallback<Image>() {
						@Override
						public void done(Image resource) {
						    // create and add background image layer
						    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
						    bgLayer.setDepth(0);
						    gLayer.add(bgLayer);
						    
						}
						@Override
						public void error(Throwable err) {
							log().error("SceneMenu.initImageLayouts : Error loading backgroung image!", err);
						}
	    	    	  });
	    	    	 
	    	    	  // Reading buttons
	    	    	  
	    	    	  Json.Array arrButton = resolution.getArray("button");
	    	    	  for (int j = 0; j < arrButton.length(); j++) {
	    	    		  Json.Object objButton = arrButton.getObject(j);
	    	    		  // id is to understand which button it is
	    	    		  String id = objButton.getString("id");
	    	    		  int x = objButton.getInt("x");
	    	    		  int y = objButton.getInt("y");
	    	    		  String path = objButton.getString("path");
	    	    		  final Button button = new Button(x, y, path);
	    	    		  button.addCallback(new ButtonCallback() {
							@Override
							public void error(Throwable err) {
								log().error("SceneMenu.initImageLayouts : Error loading button", err);
							}
							@Override
							public void done() {
								depth++;
								button.setLayerDepth(depth);
								gLayer.add(button.getLayer());
								buttonList.add(button);
							}
	    	    		  });
	    	    		  
	    	    		  // Adding click listener for newGame button
	    	    		  if (id.equals("newGame")) {
	    	    			  button.setEventListener(new ButtonEventListener() {
	    	    				  @Override
	    	    				  public void onClick(Event event) {
	    	    					  sceneNavigator.runScene(eScenes.LEVELS);
	    	    				  }
	    	    			  });
	    	    		  }	
	    	    		  
	    	    		  // Adding click listener for options button
	    	    		  if (id.equals("options")) {
	    	    			  
	    	    		  }
	    	    		 
	    	    	  }
	    	    	  break; // resolution is found
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("Error in loading menu screen! ", err);
	      }
	    });
	}
	
	@Override
	public void init() {	
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
