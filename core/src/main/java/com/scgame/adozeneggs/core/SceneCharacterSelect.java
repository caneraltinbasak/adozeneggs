package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;
import static playn.core.PlayN.storage;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Pointer;
import playn.core.ResourceCallback;

public class SceneCharacterSelect extends Scene {
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneCharacterSelect.json";
	private int depth = 0;
	private ArrayList<ImageLayer> characterLayers = new ArrayList<ImageLayer>();
	private ImageLayer activeCharacterLayer = null;
	private int activeChIndex = 0;
	
	public SceneCharacterSelect() {
		initLayout();
	    gLayer.setVisible(false);
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
	
	private void initLayout() {    
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
	    	    	  bgImage = (Image)CachedResource.getInstance().getResource(bgImagePath);
	    	    	  ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    	    	  bgLayer.setDepth(depth);
					  gLayer.add(bgLayer);

					  depth++; // character images have same depth
	    	    	  
					 
					  int x, y;
					  String path;
					  
					  // Reading character1
					  Json.Object objChImage1 = resolution.getObject("character1_image");
					  String chImage1Path;
					  if (storage().getItem(SAConstants.CHARACTER1_LOCK).equals(SAConstants.UNLOCKED)) {
						  chImage1Path = objChImage1.getString("path");
					  }  
					  else {
						  chImage1Path = objChImage1.getString("path_locked");
					  }
					  
					  Image chImage1 = (Image) CachedResource.getInstance().getResource(chImage1Path);
					  ImageLayer chImage1Layer = graphics().createImageLayer(chImage1);
					  x = objChImage1.getInt("x");
					  y = objChImage1.getInt("y");
					  chImage1Layer.setTranslation(x, y);
					  chImage1Layer.setVisible(true);
					  activeCharacterLayer = chImage1Layer;
					  characterLayers.add(chImage1Layer);
					  gLayer.add(chImage1Layer);
					  
					  // Reading character2
					  Json.Object objChImage2 = resolution.getObject("character2_image");
					  String chImage2Path;
					  if (storage().getItem(SAConstants.CHARACTER2_LOCK).equals(SAConstants.UNLOCKED)) {
						  chImage2Path = objChImage2.getString("path");
					  }
					  else {
						  chImage2Path = objChImage2.getString("path_locked");
					  }
					  Image chImage2 = (Image) CachedResource.getInstance().getResource(chImage2Path);
					  ImageLayer chImage2Layer = graphics().createImageLayer(chImage2);
					  x = objChImage2.getInt("x");
					  y = objChImage2.getInt("y");
					  chImage2Layer.setTranslation(x, y);
					  chImage2Layer.setVisible(false);
					  characterLayers.add(chImage2Layer);
					  gLayer.add(chImage2Layer);
					  
					  // Reading character3
					  Json.Object objChImage3 = resolution.getObject("character3_image");
					  String chImage3Path;
					  if (storage().getItem(SAConstants.CHARACTER3_LOCK).equals(SAConstants.UNLOCKED)) {
						  chImage3Path = objChImage3.getString("path");
					  }
					  else {
						  chImage3Path = objChImage3.getString("path_locked");
					  }
					  Image chImage3 = (Image) CachedResource.getInstance().getResource(chImage3Path);
					  ImageLayer chImage3Layer = graphics().createImageLayer(chImage3);
					  x = objChImage3.getInt("x");
					  y = objChImage3.getInt("y");
					  chImage3Layer.setTranslation(x, y);
					  chImage3Layer.setVisible(false);
					  characterLayers.add(chImage3Layer);
					  gLayer.add(chImage3Layer);
					  
					  // previous button
					  Json.Object objPrevious = resolution.getObject("previous_button");
    	    		  x = objPrevious.getInt("x");
    	    		  y = objPrevious.getInt("y");
    	    		  path = objPrevious.getString("path");
    	    		  Button btnPrevious = new Button(x, y, path);
    	    		  btnPrevious.setLayerDepth(depth);
    	    		  final ImageLayer btnPreviousLayer = btnPrevious.getLayer();
    	    		  gLayer.add(btnPreviousLayer);
    	    		  buttonList.add(btnPrevious);
    	    		  
    	    		  btnPrevious.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick(Vect2d pointer) {
							
							
						}
    	    		  });
    	    		  
    	    		  // next button
					  Json.Object objNext = resolution.getObject("next_button");
    	    		  x = objNext.getInt("x");
    	    		  y = objNext.getInt("y");
    	    		  path = objNext.getString("path");
    	    		  Button btnNext = new Button(x, y, path);
    	    		  btnNext.setLayerDepth(depth);
    	    		  final ImageLayer btnNextLayer = btnNext.getLayer();
    	    		  gLayer.add(btnNextLayer);
    	    		  buttonList.add(btnNext);
    	    		  
    	    		  btnNext.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick(Vect2d pointer) {
						
						}
    	    		  });
    	    		  
    	    		  // play button
					  Json.Object objPlay = resolution.getObject("play_button");
    	    		  x = objPlay.getInt("x");
    	    		  y = objPlay.getInt("y");
    	    		  path = objPlay.getString("path");
    	    		  Button btnPlay = new Button(x, y, path);
    	    		  btnPlay.setLayerDepth(depth);
    	    		  final ImageLayer btnPlayLayer = btnPlay.getLayer();
    	    		  gLayer.add(btnPlayLayer);
    	    		  buttonList.add(btnPlay);
    	    		  
    	    		  btnPlay.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick(Vect2d pointer) {
							SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
							
						}
    	    		  });
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("Error in loading Character Select scene! ", err);
	      }
	    });
	    
	    
 
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
