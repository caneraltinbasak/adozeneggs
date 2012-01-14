package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Layer;
import playn.core.Pointer;
import playn.core.ResourceCallback;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class SceneMenu extends Scene {
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private String jsonPath = "layouts/SceneMenu.json";
	private int depth = 0;
	
	public SceneMenu () {
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

					  depth++; // Buttons have same depth
	    	    	  
					  // Reading buttons
					  int x, y;
					  String path;
					  
					  // Adding new game button
					  Json.Object objNewGame = resolution.getObject("newgame_button");
    	    		  x = objNewGame.getInt("x");
    	    		  y = objNewGame.getInt("y");
    	    		  path = objNewGame.getString("path");
    	    		  Button btnNewGame = new Button(x, y, path);
    	    		  btnNewGame.setLayerDepth(depth);
    	    		  final ImageLayer btnNewGameLayer = btnNewGame.getLayer();
    	    		  gLayer.add(btnNewGameLayer);
    	    		  buttonList.add(btnNewGame);
    	    		  
    	    		  // Adding event listener for newGame button
    	    		  btnNewGame.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick(Vect2d pointer) {
    	    				  SceneNavigator.getInstance().runScene(eScenes.CHARACTER_SELECT, null);
    	    			  }
    	    		  });
    	    
    	    		  // Adding high scores button
					  Json.Object objHighScores = resolution.getObject("highscores_button");
    	    		  x = objHighScores.getInt("x");
    	    		  y = objHighScores.getInt("y");
    	    		  path = objHighScores.getString("path");
    	    		  Button btnHighScores = new Button(x, y, path);
    	    		  btnHighScores.setLayerDepth(depth);
    	    		  final ImageLayer btnHighScoresLayer = btnHighScores.getLayer();
    	    		  gLayer.add(btnHighScoresLayer);
    	    		  buttonList.add(btnHighScores);
    	    		  
    	    		  // Adding event listener for High Scores button
    	    		  btnHighScores.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick(Vect2d pointer) {
    	    				  SceneNavigator.getInstance().runScene(eScenes.HIGH_SCORES, null);
    	    			  }
    	    		  });
    	    		  
    	    		  // Adding achievements button
					  Json.Object objAchievements = resolution.getObject("achievements_button");
    	    		  x = objAchievements.getInt("x");
    	    		  y = objAchievements.getInt("y");
    	    		  path = objAchievements.getString("path");
    	    		  Button btnAchievements = new Button(x, y, path);
    	    		  btnAchievements.setLayerDepth(depth);
    	    		  final ImageLayer btnAchievementsLayer = btnAchievements.getLayer();
    	    		  gLayer.add(btnAchievementsLayer);
    	    		  buttonList.add(btnAchievements);
    	    		  
    	    		  // Adding event listener for High Scores button
    	    		  btnAchievements.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick(Vect2d pointer) {
    	    				  
    	    			  }
    	    		  });
    	    		  
    	    		  // Adding sound buttons
	    	    	  Json.Object objSoundOn = resolution.getObject("sound_on_button");
	    	    	  Json.Object objSoundOff = resolution.getObject("sound_off_button");
	    	    	  x = objSoundOn.getInt("x");
    	    		  y = objSoundOn.getInt("y");
    	    		  String soundOnPath = objSoundOn.getString("path");
    	    		  String soundOffPath = objSoundOff.getString("path");
    	    		  
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
    	    		  
    	    		  // Adding sound buttons
	    	    	  Json.Object objMusicOn = resolution.getObject("music_on_button");
	    	    	  Json.Object objMusicOff = resolution.getObject("music_off_button");
	    	    	  x = objMusicOn.getInt("x");
    	    		  y = objMusicOn.getInt("y");
    	    		  String musicOnPath = objMusicOn.getString("path");
    	    		  String musicOffPath = objMusicOff.getString("path");
    	    		  
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
    	    		  
    	    		  Layer layer = createGlobalScoresLayer();
    	    		  layer.setDepth(depth);
    	    		  gLayer.add(layer);
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("Error in loading Scene Menu! ", err);
	      }
	    });
	    
	    
	    if (SoundControl.getInstance().isMusicOn()) {
	    	SoundControl.getInstance().playGameMusic();
	    }
 
	}
	
	private Layer createGlobalScoresLayer() {
		Score scoreAllTime = SAHandler.getInstance().getHighScoreAllTime();
		
		Font font = graphics().createFont("Helvetica", Font.Style.BOLD_ITALIC, 16);
        TextFormat format = new TextFormat().withFont(font);
        TextLayout layout = graphics().layoutText(scoreAllTime.user + " has highest score of all time\n              " + scoreAllTime.score + "\n\n Can you beat him?" , format);
        Layer layer = createTextLayer(layout);
        return layer;
	}
	
	private Layer createTextLayer(TextLayout layout) {
		CanvasLayer layer = graphics().createCanvasLayer((int)Math.ceil(layout.width()), (int)Math.ceil(layout.height()));
		layer.canvas().drawText(layout, 0, 0);
		return layer;
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
