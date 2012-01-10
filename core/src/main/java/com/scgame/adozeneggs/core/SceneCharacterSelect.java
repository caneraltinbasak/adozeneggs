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
	private List<Button> buttonPlayList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneCharacterSelect.json";
	
	private ArrayList<GroupLayer> characterLayers = new ArrayList<GroupLayer>();
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
	    	    	  //bgLayer.setDepth(depth);
					  gLayer.add(bgLayer);

					  //depth++; // character images have same depth
					  
					  gLayer.add(createCharacter(resolution, "character1_image", SAConstants.CHARACTER1_LOCK));
					  gLayer.add(createCharacter(resolution, "character2_image", SAConstants.CHARACTER2_LOCK));
					  gLayer.add(createCharacter(resolution, "character3_image", SAConstants.CHARACTER3_LOCK));
					  
					  characterLayers.get(0).setVisible(true);
					  

					  // previous button
					  Json.Object objPrevious = resolution.getObject("previous_button");
					  Button btnPrevious = createButton(objPrevious);
					  final ImageLayer btnPreviousLayer = btnPrevious.getLayer();
    	    		  gLayer.add(btnPreviousLayer);
    	    		  buttonList.add(btnPrevious);
    	    		  
    	    		  btnPrevious.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick(Vect2d pointer) {
							showPreviousCharacter();
						}
    	    		  });
    	    		  
    	    		  // next button
					  Json.Object objNext = resolution.getObject("next_button");
    	    		  Button btnNext = createButton(objNext);
    	    		  final ImageLayer btnNextLayer = btnNext.getLayer();
    	    		  gLayer.add(btnNextLayer);
    	    		  buttonList.add(btnNext);
    	    		  
    	    		  btnNext.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick(Vect2d pointer) {
							showNextCharacter();
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
	
		if (buttonPlayList.get(activeChIndex) != null) {
			buttonPlayList.get(activeChIndex).clicked(pointer);
		}
		
	}
	
	public Button createButton(Json.Object objButton) {
		int x = objButton.getInt("x");
		int y = objButton.getInt("y");
		String path = objButton.getString("path");
		Button btnPlay = new Button(x, y, path);
		
		return btnPlay;
	}
	
	public GroupLayer createCharacter(Json.Object resolution, String characterName, String strLock) {
		GroupLayer gChLayer = graphics().createGroupLayer();
		ImageLayer btnPlayLayer = null;
		String chImagePath;
		  
		Json.Object objCharacter = resolution.getObject(characterName);
			
		if (storage().getItem(strLock).equals(SAConstants.UNLOCKED)) {
			chImagePath = objCharacter.getString("path");
			Json.Object objButton = resolution.getObject("play_button");
			Button btnPlay = createButton(objButton);
			
			btnPlayLayer = btnPlay.getLayer();
			buttonPlayList.add(btnPlay);
			
			btnPlay.setEventListener(new ButtonEventListener() {
				
				@Override
				public void onClick(Vect2d pointer) {
					SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
				}
			});
		}  
		else {
			chImagePath = objCharacter.getString("path_locked");
			buttonPlayList.add(null);
		}
		  
		Image chImage = (Image) CachedResource.getInstance().getResource(chImagePath);
		ImageLayer chImageLayer = graphics().createImageLayer(chImage);
		int x = objCharacter.getInt("x");
		int y = objCharacter.getInt("y");
		chImageLayer.setTranslation(x, y);
		
		gChLayer.add(chImageLayer);
		if (btnPlayLayer != null) {
			gChLayer.add(btnPlayLayer);
		}
		gChLayer.setVisible(false);
		characterLayers.add(gChLayer);
		return gChLayer;
	}
	
	public void showPreviousCharacter() {
		for (int i = 0; i < characterLayers.size(); i++) {
			characterLayers.get(i).setVisible(false);
		}
		
		activeChIndex--;
		if (activeChIndex < 0) {
			activeChIndex = characterLayers.size() - 1;
		}
		characterLayers.get(activeChIndex).setVisible(true);
	}
	
	public void showNextCharacter() {
		for (int i = 0; i < characterLayers.size(); i++) {
			characterLayers.get(i).setVisible(false);
		}
		
		activeChIndex++;
		if (activeChIndex == characterLayers.size()) {
			activeChIndex = 0;
		}
		characterLayers.get(activeChIndex).setVisible(true);
	}
	
	@Override
	public void shutdown() {	
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.setVisible(false);
		}
	}

}
