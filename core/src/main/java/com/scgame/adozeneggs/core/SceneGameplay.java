
package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Json;
import playn.core.Pointer;
import playn.core.ResourceCallback;

public class SceneGameplay extends Scene  implements EggEventListener {

	private static final float BASKET_VERTICAl_DISTANCE = 2.5f;
	protected static final float BASKET_START_POSITION = 0.5f;
	private GroupLayer sceneRootLayer;

	private boolean gamePaused =  false;
	private List<Button> buttonList = new ArrayList<Button>();
	private GameForeground foreGround;
	private GameBackground backGround;
	private GamePauseScreen pauseScreen;
	private AchievementScreen achievementScreen;
	private String LevelDataPath = "levels/level1.json";
	private List<Vect2d> pointerEventList = new ArrayList<Vect2d>();

	public SceneGameplay () {
		initLayout();
		sceneRootLayer.setVisible(false);
	}
	
	@Override
	public void init(Object data) {
		// add a listener for pointer (mouse, touch) input
	    pointer().setListener(new Pointer.Adapter() {
	    	@Override
	    	public void onPointerEnd(Pointer.Event event) {
	    		Vect2d pointer = new Vect2d(event.x(), event.y());
	    		pointerEventList.add(pointer);
	    	}
	    });
	    String sData= (String)data;
	    if(sData == "restart" || sData == "newgame")
	    {
	    	foreGround.init();
	    	backGround.init();
	    }
	    
	    pauseScreen.hide();
	    gamePaused = false;
	    sceneRootLayer.setVisible(true);
	}

	public void initLayout() {
		// the top layer which doesn't move, static elements will be inside this layer.
		sceneRootLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(sceneRootLayer);
		
		foreGround = new GameForeground();
		foreGround.getGroupLayer().setDepth(50);
		foreGround.addEventListener(this);
		sceneRootLayer.add(foreGround.getGroupLayer());
		
		backGround = new GameBackground();
		backGround.getGroupLayer().setDepth(40);
		sceneRootLayer.add(backGround.getGroupLayer());
		
		pauseScreen = new GamePauseScreen();
		sceneRootLayer.add(pauseScreen.getGroupLayer());
		pauseScreen.hide();
		
		achievementScreen = new AchievementScreen();
		sceneRootLayer.add(achievementScreen.getGroupLayer());
		achievementScreen.hide();
		
		SAHandler.getInstance().setAchievementScreen(achievementScreen);
		


		assetManager().getText((String) LevelDataPath, new ResourceCallback<String>() {
			@Override
			public void done(String resource) {
				Json.Object document = json().parse(resource);
				
				// Add static elements to the sceneRootLayer;
				Json.Object layout = document.getObject("RootScreenlayout");
				Json.Array resArray = layout.getArray("resolution");
				for( int i = 0; i < resArray.length(); i++ ){
					Json.Object resolution = resArray.getObject(i);
					int width = resolution.getInt("width");
					int height = resolution.getInt("height");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						Json.Object pauseButton = resolution.getObject("pause_button");
						String path = pauseButton.getString("path");
						int x = pauseButton.getInt("x");
						int y = pauseButton.getInt("y");
						Button pButton = new Button(x, y, path);
						pButton.setLayerDepth(70);
						sceneRootLayer.add(pButton.getLayer());
						buttonList.add(pButton);
						pButton.setEventListener(new ButtonEventListener() {
							@Override
							public void onClick() {
								if (gamePaused) {
									gamePaused = false;
									pauseScreen.hide();
								}
								else {
									gamePaused = true;
									pauseScreen.show();
								}
							}
						});
					}
				}
				
				// ***********Add Pause Screen Elements ***********
				Json.Object jPauseScreen = document.getObject("PauseScreenLayout");
				Json.Array jarrResolution;
				jarrResolution = jPauseScreen.getArray("resolution");
				for (int i = 0; i < jarrResolution.length(); i++) {
					Json.Object bgRes = jarrResolution.getObject(i);
					int width = bgRes.getInt("width");
					int height = bgRes.getInt("height");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						String bgPath = bgRes.getString("bg_image_path");
						int px = bgRes.getInt("x");
						int py = bgRes.getInt("y");
						Vect2d pos = new Vect2d(px, py);
						pauseScreen.setPosition(pos);
						pauseScreen.setBackground(bgPath);
						Json.Array jarrButtons = bgRes.getArray("buttons");
						for (int j = 0; j < jarrButtons.length(); j++) {
							Json.Object jButton = jarrButtons.getObject(j);
							int x = jButton.getInt("x");
							int y = jButton.getInt("y");
							String id = jButton.getString("id");
							String path = jButton.getString("image_path");
							
							// adding buttons to Game Pause Screen
							if (id.equals("main")) {
								pauseScreen.addMainButton(x, y, path);
							}
							if (id.equals("restart")) {
								pauseScreen.addRestartButton(x, y, path);
							}
							if (id.equals("resume")) {
								pauseScreen.addResumeButton(x, y, path);
							}
						}
					}
				}
				
				// ***********Add Achievement Screen Elements ***********
				Json.Object jAchievementScreen = document.getObject("AchievementScreenLayout");
				jarrResolution = jAchievementScreen.getArray("resolution");
				for (int i = 0; i < jarrResolution.length(); i++) {
					Json.Object bgRes = jarrResolution.getObject(i);
					int width = bgRes.getInt("width");
					int height = bgRes.getInt("height");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						int px = bgRes.getInt("x");
						int py = bgRes.getInt("y");
						Vect2d pos = new Vect2d(px, py);
						achievementScreen.setPosition(pos);
					}
				}
			}

			@Override
			public void error(Throwable err) {
				log().error(" Error in " + LevelDataPath + "\n" , err);				
			}
		});
	}

	private void firePointerEndEvent(Vect2d pointer) {
		boolean handled = false; 
		
		// if game is paused, pointer end event is sent to buttons on pause screen
		// egg should not take any event when the game is paused
		if (gamePaused) {
			pauseScreen.firePointerEndEvent(pointer);
		}
		else {
			for (int i = 0; i < buttonList.size(); i++) {
				handled = buttonList.get(i).clicked(pointer);
			}
			if(!handled) {
				foreGround.clicked(pointer);
			}
		}
	}

	public void update(float delta) {
		while(pointerEventList.size() > 0)
		{
			firePointerEndEvent(pointerEventList.get(0));
			pointerEventList.remove(0);
		}
		
		if (gamePaused == false) {
			foreGround.update(delta);
			backGround.update(delta);

		}
	}
	
	public void paint(float alpha) {
		if (gamePaused == false) {
			foreGround.paint(alpha);
			backGround.paint(alpha);
			
		}
	}

	@Override
	public void shutdown() {
		pointer().setListener(null);
		if (sceneRootLayer != null) {
			sceneRootLayer.setVisible(false);
		}
	}

	@Override
	public void onEggJump(Basket basket, int stars) {
		SAHandler.getInstance().jumped(3);
		log().debug("[SceneGameplay::onEggJump]\n");
		backGround.scrollTo(-basket.getPosition().y + GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET));
		foreGround.scrollTo(-basket.getPosition().y + GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET));
	}

	@Override
	public void onEggFall(float y) {
		backGround.scrollTo(- y + GameConstants.ScreenProperties.height );

		log().debug("[SceneGameplay::onFall]\n");

	}
}
