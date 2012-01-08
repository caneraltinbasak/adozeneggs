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
	private float scrollingSpeed = 0.0f;
	private float scrollPosition = 0.0f;
	private Egg egg;


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
					basket.initializeProperties(/*i*0.1f+0.1f*/0, new Vect2d(0, basketYinPixel), new Vect2d(GameConstants.ScreenProperties.width, basketYinPixel));
					basketYinPixel = basketYinPixel - basketGapinPixel;
					break;
				default:
					log().error("[GameForeground::update] Undefined Entity type\n");
				}

		}
	}

	private void scrollToBottom() {
		position.assign(0, 0);
		groupLayer.setTranslation(position.x, position.y);
		scrollingSpeed=0;
		scrollPosition=0;
	}
	@Override
	public void paint(float alpha) {
		// ENTITY PAINT
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).paint(alpha);
		
		// SCROLL PAINT
		if(scrollingSpeed != 0.0)
			groupLayer.setTranslation(position.x, position.y);

		// GAMESCORE PAINT
		SAHandler.getInstance().paint(alpha);

	}
	@Override
	public void update(float delta) {
		// SCROLL
		if(scrollingSpeed != 0.0f)
		{
			position.y = position.y + GameConstants.PhysicalProperties.verticalInPixels(scrollingSpeed) * delta / 1000;
			if(scrollPosition <= position.y)
			{
				scrollingSpeed = 0;
			}
		}
		
		// UPDATE POSITIONS
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).update(delta);
		
		// GAMESCORE UPDATE
		SAHandler.getInstance().update(delta);
		
		// update score when egg is on basket
		if (egg.getCurrentBasket() != null) {
			SAHandler.getInstance().updateLiveScoreWithUpdate(delta);
		}
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
		if(scrollingSpeed == 0)
			scrollingSpeed = GameConstants.PhysicalProperties.ForegroundScrollSpeed;
		else
			scrollingSpeed *= 2;
		if(scrollPosition < 0)
			scrollingSpeed = -scrollingSpeed;
		this.scrollPosition = scrollPosition;
	}
	@Override
	public void onEggJump(Basket basket, int stars) {
		log().debug("[GameForeground::onEggJump]\n");
		scrollTo(-basket.getPosition().y + GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET));
	}
	@Override
	public void onEggFall() {
		log().debug("EGG is falling\n");
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
}
