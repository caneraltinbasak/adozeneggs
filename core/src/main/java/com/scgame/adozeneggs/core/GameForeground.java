package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;

public class GameForeground extends GroupEntity implements EggEventListener {
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
		float basketYinPixel = GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET);
		float basketGapinPixel = GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.BASKET_GAP);
		for (int i = 0; i < NUMBER_OF_BASKETS;i++){
			Basket basket = new Basket(i*0.1f, new Vect2d(0, basketYinPixel), new Vect2d(GameConstants.ScreenProperties.width, basketYinPixel));
			groupLayer.add(basket.getBottomImageLayer());
			groupLayer.add(basket.getTopImageLayer());
			entities.add(basket);
			if(i == 0)
				egg.setCurrentBasket(basket);
			egg.addTargetBasket(basket);
			basketYinPixel = basketYinPixel - basketGapinPixel;
		}
		groupLayer.add(egg.getTopImageLayer()); // TODO: Move most of these to initGame()
		entities.add(egg);
	}
	public void initGame() {
	}

	@Override
	public void paint(float alpha) {
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).paint(alpha);
		if(scrollingSpeed != 0.0)
			groupLayer.setTranslation(position.x, position.y);
	}
	@Override
	public void update(float delta) {
		if(scrollingSpeed != 0.0f)
		{
			position.y = position.y + GameConstants.PhysicalProperties.verticalInPixels(scrollingSpeed) * delta / 1000;
			if(scrollPosition <= position.y)
			{
				scrollingSpeed = 0;
			}
		}
		for(int i = 0 ; i <entities.size(); i++)
			entities.get(i).update(delta);
		
		for(int i = 0 ; i <entities.size(); i++)
		{
			if(entities.get(i).isInRect(0, GameConstants.ScreenProperties.height - position.y, GameConstants.ScreenProperties.width, GameConstants.ScreenProperties.height - position.y)!=true)
			{
				switch(entities.get(i).type){
				case EGG:
					// game over
					log().debug("GAME OVER\n");
					break;
				case BASKET:
					// reinitialize this basket
					log().debug("Reinitilalizing the basket\n");
					entities.get(i).getPosition().y-=GameConstants.ScreenProperties.height*2;
					break;
				default:
					log().error("[GameForeground::update] Undefined Entity type\n");
				}
			}
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
	public void onEggJump(JumpEvent event) {
		scrollTo(-event.getBasket().getPosition().y + GameConstants.ScreenProperties.height - GameConstants.PhysicalProperties.verticalInPixels(GameConstants.GameProperties.FIRST_BASKET_Y_OFFSET));
	}
	@Override
	public void onEggFall(JumpEvent event) {
		
	}

	public void clicked(Vect2d pointer) {
		egg.jump();
	}
}
