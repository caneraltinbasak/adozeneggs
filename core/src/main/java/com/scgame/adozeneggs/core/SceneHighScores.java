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

public class SceneHighScores extends Scene {
	private List<Button> buttonList = new ArrayList<Button>();
	private Image bgImage;
	private GroupLayer gLayer = null;
	private Layer localScoresLayer = null;
	private Layer globalScoresLayer = null;
	private String jsonPath = "layouts/SceneHighScores.json";
	
	public SceneHighScores () {
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

					  gLayer.add(bgLayer);
	    	    	  
					  // Reading buttons
					  int x, y;
					  String path;
					  
					  // Adding local scores button
					  Json.Object objLocal = resolution.getObject("local_button");
    	    		  x = objLocal.getInt("x");
    	    		  y = objLocal.getInt("y");
    	    		  path = objLocal.getString("path");
    	    		  Button btnLocal = new Button(x, y, path);
    	    		  final ImageLayer btnLocalLayer = btnLocal.getLayer();
    	    		  gLayer.add(btnLocalLayer);
    	    		  buttonList.add(btnLocal);
    	    		  
    	    		  // Adding event listener for local scores button
    	    		  btnLocal.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick() {
    	    				  globalScoresLayer.setVisible(false);
    	    				  localScoresLayer.setVisible(true);
    	    			  }
    	    		  });
    	    		  
    	    		  // Adding global scores button
					  Json.Object objGlobal = resolution.getObject("global_button");
    	    		  x = objGlobal.getInt("x");
    	    		  y = objGlobal.getInt("y");
    	    		  path = objGlobal.getString("path");
    	    		  Button btnGlobal = new Button(x, y, path);
    	    		  final ImageLayer btnGlobalLayer = btnGlobal.getLayer();
    	    		  gLayer.add(btnGlobalLayer);
    	    		  buttonList.add(btnGlobal);
    	    		  
    	    		  // Adding event listener for global scores button
    	    		  btnGlobal.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick() {
    	    				  localScoresLayer.setVisible(false);
    	    				  globalScoresLayer.setVisible(true);
    	    			  }
    	    		  });
    	    		  
    	    		  // Reading back button
	    	    	  Json.Object backButton = resolution.getObject("back_button");
	    	    	  x = backButton.getInt("x");
    	    		  y = backButton.getInt("y");
    	    		  path = backButton.getString("path");
    	    		  Button button = new Button(x, y, path);
    	    		  gLayer.add(button.getLayer());
    	    		  buttonList.add(button);
    	    		  
    	    		  button.setEventListener(new ButtonEventListener() {
    	    			  @Override
    	    			  public void onClick() {
    	    				  SceneNavigator.getInstance().runScene(eScenes.MENU,null);
    	    			  }
    	    		  });
    	    		  
    	    		  localScoresLayer = createLocalScoresLayer();
    	    		  globalScoresLayer = createGlobalScoresLayer();
    	    		  
    	    		  localScoresLayer.setVisible(true);
    	    		  globalScoresLayer.setVisible(false);
    	    		  
    	    		  gLayer.add(localScoresLayer);
    	    		  gLayer.add(globalScoresLayer);
	    	      }
	    	  }
	      }

	      @Override
	      public void error(Throwable err) {
	    	  log().error("Error in loading Scene Menu! ", err);
	      }
	    });
	}
	
	private Layer createLocalScoresLayer() {
		long highScore = SAHandler.getInstance().getLocalHighScore();
		
		Font font = graphics().createFont("Helvetica", Font.Style.BOLD_ITALIC, 16);
        TextFormat format = new TextFormat().withFont(font);
        TextLayout layout = graphics().layoutText("Your high score : " + highScore, format);
        Layer layer = createTextLayer(layout);
        layer.setTranslation(20, 100);
        return layer;
	}
	
	private Layer createGlobalScoresLayer() {
		Score scoreToday = SAHandler.getInstance().getHighScoreToday();
		Score scoreWeek = SAHandler.getInstance().getHighScoreWeek();
		Score scoreAllTime = SAHandler.getInstance().getHighScoreAllTime();
		
		Font font = graphics().createFont("Helvetica", Font.Style.BOLD_ITALIC, 16);
        TextFormat format = new TextFormat().withFont(font);
        TextLayout layout = graphics().layoutText("Today : " + scoreToday.score + " (" + scoreToday.user + ")\n\n"
        		+ "Week : " + scoreWeek.score + " (" + scoreWeek.user + ")\n\n"
        		+ "All Time : " + scoreAllTime.score + " (" + scoreAllTime.user + ")" , format);
        Layer layer = createTextLayer(layout);
        layer.setTranslation(20, 100);
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
