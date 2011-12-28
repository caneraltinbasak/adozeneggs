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
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneMenu.json";
	private int depth = 0;
	
	public SceneMenu () {
	    initImageLayouts();
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
	    	    	  bgImage = (Image)CachedResource.getInstance().getResource(bgImagePath);
	    	    	  ImageLayer bgLayer = graphics().createImageLayer(bgImage);
	    	    	  bgLayer.setDepth(depth);
					  gLayer.add(bgLayer);

					  depth++; // Buttons have same depth
	    	    	  // Reading buttons
	    	    	  Json.Array arrButton = resolution.getArray("button");
	    	    	  for (int j = 0; j < arrButton.length(); j++) {
	    	    		  Json.Object objButton = arrButton.getObject(j);
	    	    		  // id is to understand which button it is
	    	    		  String id = objButton.getString("id");
	    	    		  int x = objButton.getInt("x");
	    	    		  int y = objButton.getInt("y");
	    	    		  String path = objButton.getString("path");
	    	    		  Button button = new Button(x, y, path);
	    	    		  button.setLayerDepth(depth);
	    	    		  final ImageLayer layer = button.getLayer();
	    	    		  gLayer.add(layer);
	    	    		  buttonList.add(button);
	    	    		  
	    	    		  // Adding click listener for newGame button
	    	    		  if (id.equals("newGame")) {
	    	    			  button.setEventListener(new ButtonEventListener() {
	    	    				  @Override
	    	    				  public void onClick(Vect2d pointer) {
	    	    					  SceneNavigator.getInstance().runScene(eScenes.GAMEPLAY, "newgame");
	    	    				  }
	    	    			  });
	    	    		  }	
	    	    		  
	    	    		  // Adding click listener for options button
	    	    		  if (id.equals("options")) {
	    	    			  button.setEventListener(new ButtonEventListener() {
	    	    				  @Override
	    	    				  public void onClick(Vect2d pointer) {
	    	    					 
	    	    				  }
	    	    			  });
	    	    		  }
	    	    	  }
	    	    	  
	    	    	  int x, y;
	    	    	  // Reading sound buttons
	    	    	  Json.Object soundOnButton = resolution.getObject("sound_on_button");
	    	    	  Json.Object soundOffButton = resolution.getObject("sound_off_button");
	    	    	  x = soundOnButton.getInt("x");
    	    		  y = soundOnButton.getInt("y");
    	    		  String soundOnPath = soundOnButton.getString("path");
    	    		  String soundOffPath = soundOffButton.getString("path");
    	    		  
    	    		  int soundToggle;
    	    		  if (SoundControl.getInstance().isSoundOn()) {
    	    			  // if game music is set to ON by the player 
    	    			  soundToggle = Toggle.ON;
    	    		  }
    	    		  else {
    	    			  // if game music is set to OFF by the player
    	    			  soundToggle = Toggle.OFF;
    	    		  }
    	    		  
    	    		  final ToggleButton btnSound = new ToggleButton(x, y, soundOnPath, soundOffPath, soundToggle);
    	    		  btnSound.setLayerDepth(depth);
    	    		  final ImageLayer btnSoundLayer = btnSound.getLayer();
    	    		  gLayer.add(btnSoundLayer);
    	    		  buttonList.add(btnSound);
    	    		  
    	    		  btnSound.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick(Vect2d pointer) {
    	    				  // Stop or Play game music
    	    				  if (btnSound.getToggle() == Toggle.OFF) {
    	    					  SoundControl.getInstance().setSoundOff();
    	    				  }
    	    				  else {
    	    					  SoundControl.getInstance().setSoundOn();
    	    				  }
    	    			  }
    	    		  });
    	    		  
    	    		  // Reading sound buttons
	    	    	  Json.Object musicOnButton = resolution.getObject("music_on_button");
	    	    	  Json.Object musicOffButton = resolution.getObject("music_off_button");
	    	    	  x = musicOnButton.getInt("x");
    	    		  y = musicOnButton.getInt("y");
    	    		  String musicOnPath = musicOnButton.getString("path");
    	    		  String musicOffPath = musicOffButton.getString("path");
    	    		  
    	    		  int musicToggle;
    	    		  if (SoundControl.getInstance().isMusicOn()) {
    	    			  // if game music is set to ON by the player 
    	    			  musicToggle = Toggle.ON;
    	    		  }
    	    		  else {
    	    			// if game music is set to OFF by the player
    	    			  musicToggle = Toggle.OFF;
    	    		  }
    	    		  final ToggleButton btnMusic = new ToggleButton(x, y, musicOnPath, musicOffPath, musicToggle);
    	    		  btnMusic.setLayerDepth(depth);
    	    		  final ImageLayer btnMusicLayer = btnMusic.getLayer();
    	    		  gLayer.add(btnMusicLayer);
    	    		  buttonList.add(btnMusic);
    	    		  
    	    		  btnMusic.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick(Vect2d pointer) {
    	    				  // Stop or Play game music
    	    				  if (btnMusic.getToggle() == Toggle.OFF) {
    	    					  SoundControl.getInstance().stopGameMusic();
    	    					  SoundControl.getInstance().setMusicOff();
    	    				  }
    	    				  else {
    	    					  SoundControl.getInstance().playGameMusic();
    	    					  SoundControl.getInstance().setMusicOn();
    	    				  }
    	    			  }
    	    		  });
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("Error in loading menu screen! ", err);
	      }
	    });
	    
	    
	    if (SoundControl.getInstance().isMusicOn()) {
	    	SoundControl.getInstance().playGameMusic();
	    }
	    
	    
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
