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
import playn.core.ResourceCallback;
import playn.core.Pointer.Event;
import pythagoras.f.Transform;

public class SceneGameplay extends Scene implements EggEventListener {

	private static final float BASKET_VERTICAl_DISTANCE = 2.5f;
	protected static final float BASKET_START_POSITION = 0.5f;
	private GroupLayer sceneRootLayer;

	private boolean gamePaused =  false;
	private List<Button> buttonList = new ArrayList<Button>();
	private GameForeground foreGround;
	private GameBackground backGround;
	protected Egg egg;

	public SceneGameplay () {
	}

	@Override
	public void init(final Object LevelDataPath) {
		// the top layer which doesn't move, static elements will be inside this layer.
		sceneRootLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(sceneRootLayer);
		
		foreGround = new GameForeground();
		foreGround.getGroupLayer().setDepth(50);
		sceneRootLayer.add(foreGround.getGroupLayer());
		
		backGround = new GameBackground();
		backGround.getGroupLayer().setDepth(40);
		sceneRootLayer.add(backGround.getGroupLayer());

		egg = new Egg();
		egg.addEventListener(this);

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
						String onPath = pauseButton.getString("on_path");
						String offPath = pauseButton.getString("off_path");
						int x = pauseButton.getInt("x");
						int y = pauseButton.getInt("y");
						Button pButton = new ToggleButton(x, y, onPath,offPath, eToggle.ON);
						pButton.setLayerDepth(70);
						sceneRootLayer.add(pButton.getLayer());
						buttonList.add(pButton);
						pButton.setEventListener(new ButtonEventListener() {
							@Override
							public void onClick(Event event) {
								log().debug("Clicked pause\n");
								if (gamePaused) {
									gamePaused = false;
								}
								else {
									gamePaused = true;
								}
							}
						});
					}
				}
				
				// ***********Add Foreground Elements ***********
				// add baskets
				Json.Object gameElements = document.getObject("GameElements");
				Json.Object jBasket = gameElements.getObject("basket");
				resArray = jBasket.getArray("resolution");
				String basketImagePath = null;
				for( int i = 0; i < resArray.length(); i++ ){
					Json.Object resolution = resArray.getObject(i);
					int width = resolution.getInt("width");
					int height = resolution.getInt("height");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						basketImagePath = resolution.getString("path");
					}
				}
				Json.Array layArray = jBasket.getArray("layout");
				float startY = BASKET_START_POSITION;
				List<Basket> basketList = new ArrayList<Basket>();
				for( int i = 0; i < layArray.length(); i++ ){
					Json.Object basketLayout = layArray.getObject(i);
					float startYinPixels = GameConstants.PhysicalProperties.verticalInPixels(startY);
					float endX = (float)basketLayout.getNumber("end_x");
					float endXinPixels = GameConstants.PhysicalProperties.horizontalInPixel(endX);
					float startX = (float) basketLayout.getNumber("start_x");
					float startXinPixels = GameConstants.PhysicalProperties.horizontalInPixel(startX);
					float speedX = (float) basketLayout.getNumber("speed");
					float speedXinPixelsPerMs = GameConstants.PhysicalProperties.horizontalInPixel(speedX)/100;
					Basket basket = new Basket(speedXinPixelsPerMs, new Vect2d(startXinPixels, startYinPixels), new Vect2d(endXinPixels, startYinPixels), basketImagePath);
					foreGround.addItsEntity(basket);
					basketList.add(basket);
					startY = startY + BASKET_VERTICAl_DISTANCE; // each basket is 2.5m far away from each other 					
				}
				// Add egg to last basket
				egg.setTargetBaskets(basketList); // add target baskets to egg
				egg.setCurrentBasket(layArray.length() - 1); // set it to bottom element.
				foreGround.addItsEntity(egg); // add egg to foreground list
				foreGround.setPosition(new Vect2d(0, GameConstants.ScreenProperties.height - egg.position.y - egg.sprite.height() * 2));
				
				// ***********Add Background Elements ***********
				Json.Object jbgImage = gameElements.getObject("bg_image");
				Json.Array jresArray = jbgImage.getArray("resolution");
				for( int i = 0; i < jresArray.length(); i++ ){
					Json.Object bgRes = jresArray.getObject(i);
					int width = bgRes.getInt("width");
					int height = bgRes.getInt("height");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						String bgPath = bgRes.getString("path");
						BGScrolledImage bgImage = new BGScrolledImage(bgPath);
						backGround.addItsEntity(bgImage);
					}
				}
			}

			@Override
			public void error(Throwable err) {
				log().error(" Error in " + LevelDataPath + "\n" , err);				
			}

		});




		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				firePointerEndEvent(event);		
			}
		});
	}

	private synchronized void firePointerEndEvent(Pointer.Event event) {
		boolean handled = false; 
		for (int i = 0; i < buttonList.size(); i++) {
			handled = buttonList.get(i).clicked(event);
		}
		if(!handled) {
			egg.jump();
			SoundControl.getInstance().playJump();
		}
			
	}

	public void update(float delta) {
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
		sceneRootLayer.destroy();
		sceneRootLayer = null; 
	}

	@Override
	public void onEggJump(JumpEvent event) {
		foreGround.scrollTo(new Vect2d(0, GameConstants.ScreenProperties.height - egg.position.y - egg.sprite.height() * 2));
	}
}
