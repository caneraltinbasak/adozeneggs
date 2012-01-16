package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;

public class GameForeground extends ScrollableGroupEntity implements EggEventListener, OnScreenCheckInterface {
	private static final int NUMBER_OF_BASKETS = 8;
	private Vect2d position = new Vect2d(0, 0);
	private List<GraphicsEntity> entities = new ArrayList<GraphicsEntity>();
	private GroupLayer groupLayer;
	private float scrollPosition = 0.0f;
	private Egg egg;
	private GameOverScreen gameOverScreen;


	public GameForeground() {
		groupLayer = graphics().createGroupLayer();	
		egg = new Egg();
		egg.addEventListener(this);
		egg.setParentRect(this);
		float basketYinPixel = GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET);
		float basketGapinPixel = GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.BASKET_GAP);
		for (int i = 0; i < NUMBER_OF_BASKETS;i++){
			Basket basket = new Basket();
			basket.setParentRect(this);
			groupLayer.add(basket.getBottomImageLayer());
			groupLayer.add(basket.getTopImageLayer());
			entities.add(basket);
			egg.addTargetBasket(basket);
			basketYinPixel = basketYinPixel - basketGapinPixel;
		}
		groupLayer.add(egg.getTopImageLayer()); // TODO: Move most of these to initGame()
		entities.add(egg);
		gameOverScreen = new GameOverScreen();
		groupLayer.add(gameOverScreen.getGroupLayer());
	}
	public void init() {
		scrollToBottom();
		float basketYinPixel = GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET);
		float basketGapinPixel = GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.BASKET_GAP);
		for(int i = 0 ; i <entities.size(); i++)
		{

				switch(entities.get(i).type){
				case EGG:
					egg.setCurrentBasket( (Basket)(entities.get(0)));
					log().debug("Assign egg to first basket\n");
					break;
				case BASKET:
					// reinitialize this basket
					log().debug("Reinitilalizing the basket\n");
					Basket basket = (Basket)(entities.get(i));
					basket.initializeProperties((i/6)*0.1f, new Vect2d(0, basketYinPixel), new Vect2d(GameConstants.ScreenProperties.width, basketYinPixel));
					basketYinPixel = basketYinPixel - basketGapinPixel;
					break;
				default:
					log().error("[GameForeground::update] Undefined Entity type\n");
				}

		}
		gameOverScreen.init();
	}

	private void scrollToBottom() {
		position.assign(0, 0);
		groupLayer.setTranslation(position.x, position.y);
		scrollPosition=0;
	}
	@Override
	public void paint(float alpha) {
		// ENTITY PAINT
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).paint(alpha);
		
		// SCROLL PAINT
		groupLayer.setTranslation(position.x, position.y);

		// GAMESCORE PAINT
		SAHandler.getInstance().paint(alpha);
		
		// GAMEOVER PAINT
		gameOverScreen.paint(alpha);

	}
	@Override
	public void update(float delta) {
		// SCROLL
		position.y = position.y + ( scrollPosition -position.y )*GameConstants.ScreenProperties.FRAME_RATE/1000;
		
		// UPDATE POSITIONS
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).update(delta);
		
		// GAMESCORE UPDATE
		SAHandler.getInstance().update(delta);
		
		// update score when egg is on basket
		if (egg.getCurrentBasket() != null) {
			SAHandler.getInstance().updateLiveScoreWithUpdate(delta);
		}
		
		// GAMEOVER UPDATE
		gameOverScreen.update(delta);
	}
	@Override
	public GroupLayer getGroupLayer() {
		return groupLayer;
	}
	@Override
	public Vect2d getPosition() {
		return position;
	}
	@Override
	public void setPosition(Vect2d position) {
		this.position=position;
	}
	@Override
	public void scrollTo(float scrollPosition){
		this.scrollPosition = scrollPosition;
	}
	@Override
	public void onEggJump(Egg egg, int stars) {
		log().debug("[GameForeground::onEggJump]\n");
		scrollTo(-egg.getPosition().y + GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET));
	}
	@Override
	public void onEggFall(Egg egg) {
	}

	public void clicked(Vect2d pointer) {
		egg.jump();
	}
	/**
	 * Removes listener
	 * @param eventListener listener for jump events
	 */
	public synchronized void removeEventListener(EggEventListener eventListener) {
		egg.removeEventListener(eventListener);
	}
	/**
	 * Adds a EggEventListener to listen JumpEvents.
	 * @param eventListener listener for jump events.
	 */
	public synchronized void addEventListener(EggEventListener eventListener) {
		egg.addEventListener(eventListener);
	}
	@Override
	public eOnScreenState isInRect(Entity entity) {
		if( entity.getPosition().y > GameConstants.ScreenProperties.height - position.y){
			return eOnScreenState.TOP_IS_UNDER_VISIBLE_AREA;
		}else if(entity.getPosition().y + entity.getHeight() > GameConstants.ScreenProperties.height - position.y){
			return eOnScreenState.BOTTOM_IS_UNDER_VISIBLE_AREA;
		}

		return eOnScreenState.TOTAL_IS_IN_VISIBLE_AREA;
	}
	@Override
	public float getHeight() {
		// Height of the gamescreen is two times the device screen
		return GameConstants.ScreenProperties.height*2;
	}
	@Override
	public void onEggOnCrashGround() {
		//scrollingSpeed=0; // stop scrolling when crashes ground
		egg.crashOnGround();
		gameOverScreen.show();
	}
	@Override
	public void stopScroll() {
	}
}
