package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;
import static playn.core.PlayN.mouse;
import static playn.core.PlayN.storage;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Pointer;
import playn.core.ResourceCallback;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.Pointer.Event;

public class SceneCharacterSelect extends Scene {
	private List<Button> buttonList = new ArrayList<Button>();
	private List<Button> chButtonList = new ArrayList<Button>();
	
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneCharacterSelect.json";
	private int chIndex = 0;
	private Button ch1Unlocked;
	private Button ch1Locked;
	private Button ch2Unlocked;
	private Button ch2Locked;
	private Button ch3Unlocked;
	private Button ch3Locked;

	
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

	    chButtonList.clear();
	    
	    // Init character buttons as locked/unlocked by checking storage data.
	    if (storage().getItem(SAConstants.CHARACTER1_LOCK).equals(SAConstants.UNLOCKED)) {
	    	chButtonList.add(ch1Unlocked);
	    }
	    else {
	    	chButtonList.add(ch1Locked);
	    }
	    
	    if (storage().getItem(SAConstants.CHARACTER2_LOCK).equals(SAConstants.UNLOCKED)) {
	    	chButtonList.add(ch2Unlocked);
	    }
	    else {
	    	chButtonList.add(ch2Locked);
	    }
	    
	    if (storage().getItem(SAConstants.CHARACTER3_LOCK).equals(SAConstants.UNLOCKED)) {
	    	chButtonList.add(ch3Unlocked);
	    }
	    else {
	    	chButtonList.add(ch3Locked);
	    }
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
					  gLayer.add(bgLayer);

					  /********** CHARACTER 1 **********/
					  // character1 unlocked		
					  ch1Unlocked = createCharacterButton(resolution, "character1_image", SAConstants.UNLOCKED);
					  chButtonList.add(ch1Unlocked);
					  gLayer.add(ch1Unlocked.getLayer());
					  ch1Unlocked.setEventListener(new ButtonEventListener() {
						@Override
						public void onClick() {
							SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
							
						}
					  });
					  
					  // character1 locked button
					  ch1Locked = createCharacterButton(resolution, "character1_image", SAConstants.LOCKED);
					  chButtonList.add(ch1Locked);
					  ch1Locked.setVisible(false);
					  gLayer.add(ch1Locked.getLayer());
					  
					  /*********************************/
					  
					  /********** CHARACTER 2 **********/
					  // character2 unlocked button
					  ch2Unlocked = createCharacterButton(resolution, "character2_image", SAConstants.UNLOCKED);
					  chButtonList.add(ch2Unlocked);
					  ch2Unlocked.setVisible(false);
					  gLayer.add(ch2Unlocked.getLayer());	
					  ch2Unlocked.setEventListener(new ButtonEventListener() {
						@Override
						public void onClick() {
							SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
							
						}
					  });
					  
					  // character2 locked button
					  ch2Locked = createCharacterButton(resolution, "character2_image", SAConstants.LOCKED);
					  chButtonList.add(ch2Locked);
					  ch2Locked.setVisible(false);
					  gLayer.add(ch2Locked.getLayer());	
					  
					  /*********************************/
					  
					  /********** CHARACTER 3 **********/
					  // character3 unlocked button
					  ch3Unlocked = createCharacterButton(resolution, "character3_image", SAConstants.UNLOCKED);
					  chButtonList.add(ch3Unlocked);
					  ch3Unlocked.setVisible(false);
					  gLayer.add(ch3Unlocked.getLayer());	
					  ch3Unlocked.setEventListener(new ButtonEventListener() {
						@Override
						public void onClick() {
							SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
							
						}
					  });
					  
					  // character3 locked
					  ch3Locked = createCharacterButton(resolution, "character3_image", SAConstants.LOCKED);
					  chButtonList.add(ch3Locked);
					  ch3Locked.setVisible(false);
					  gLayer.add(ch3Locked.getLayer());
					  
					  /*********************************/
					  
					  // previous button
					  Json.Object objPrevious = resolution.getObject("previous_button");
					  Button btnPrevious = createButton(objPrevious); 
    	    		  gLayer.add(btnPrevious.getLayer());
    	    		  buttonList.add(btnPrevious);
    	    		  btnPrevious.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick() {
							showPreviousCharacter();
						}
    	    		  });
    	    		  
    	    		  // next button
					  Json.Object objNext = resolution.getObject("next_button");
    	    		  Button btnNext = createButton(objNext);
    	    		  gLayer.add(btnNext.getLayer());
    	    		  buttonList.add(btnNext);
    	    		  btnNext.setEventListener(new ButtonEventListener() {
						
						@Override
						public void onClick() {
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

		for (int i = 0; i < chButtonList.size(); i++) {
			chButtonList.get(i).clicked(pointer);
		}
	}
	
	public Button createButton(Json.Object objButton) {
		int x = objButton.getInt("x");
		int y = objButton.getInt("y");
		String path = objButton.getString("path");
		Button btnPlay = new Button(x, y, path);
		
		return btnPlay;
	}
	
	public Button createCharacterButton(Json.Object resolution, String characterName, String strLock) {
		Json.Object objButton = resolution.getObject(characterName);
		int x = objButton.getInt("x");
		int y = objButton.getInt("y");
		
		String path;
		Button btnPlay;
		
		if (strLock.equals(SAConstants.UNLOCKED)) {
			path = objButton.getString("path");
			btnPlay = new Button(x, y, path);
			btnPlay.enable();
		}
		else {
			path = objButton.getString("path_locked");
			btnPlay = new Button(x, y, path);
			btnPlay.disable();
		}
		
		return btnPlay;
	}
	
	public void showNextCharacter() {
		chIndex++;
		if (chIndex == chButtonList.size()) {
			chIndex = 0;
		}
		
		for (int i = 0; i < chButtonList.size(); i++) {
			chButtonList.get(i).setVisible(false);
		}
		
		chButtonList.get(chIndex).setVisible(true);
	}
	
	public void showPreviousCharacter() {
		chIndex--;
		if (chIndex < 0) {
			chIndex = chButtonList.size()-1;
		}
		
		for (int i = 0; i < chButtonList.size(); i++) {
			chButtonList.get(i).setVisible(false);
		}
		
		chButtonList.get(chIndex).setVisible(true);
	}
	
	@Override
	public void shutdown() {	
		pointer().setListener(null);
		if (gLayer != null) {
			gLayer.setVisible(false);
		}
	}
}
