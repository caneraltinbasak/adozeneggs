package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;


public class Egg  extends GraphicsEntity{
	private static final float IN_BASKET = 100f;
	private static final float ON_TOP = 300f;
	
	private static final String EGG_HIGH_SPRITE_PATH = "sprites/peasprite2.json";
	private static final String EGG_MEDIUM_SPRITE_PATH = "sprites/peasprite2.json";
	private static final String EGG_LOW_SPRITE_PATH = "sprites/peasprite2.json";
	
	private List<EggEventListener> eventListeners = new ArrayList<EggEventListener>();
	public Sprite sprite;
	private int spriteIndex = 1;
	private boolean hasLoaded = false; // set to true when resources have loaded
	private boolean hasInitialized = false; 
	// and we can update
	private Basket currentBasket = null;
	private Basket lastBasket = null;
	private List<Basket>targetBaskets = new ArrayList<Basket>();

	// position
	public Vect2d position;

	// velocity(used when on air, uses currentBasket baskets velocity if not on air)
	public Vect2d velocity= new Vect2d(0, 0); // pixels/ms
	private OnScreenCheckInterface screenRect;


	/**
	 * Egg Constructor
	 * @param eggLayer Layer for egg graphics
	 * @param targetBasket Next basket that egg is targetBasketing
	 */
	public Egg( ) {
		// Sprite method #2: use json data describing the sprites and containing
		// the image urls
		type=eEntity.EGG;
	    if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.HIGH){
	    	sprite = SpriteLoader.getSprite(EGG_HIGH_SPRITE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.MEDIUM){
	    	sprite = SpriteLoader.getSprite(EGG_MEDIUM_SPRITE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.LOW){
	    	sprite = SpriteLoader.getSprite(EGG_LOW_SPRITE_PATH);
	    }
		// Add a callback for when the image loads.
		sprite.addCallback(new ResourceCallback<Sprite>() {
			@Override
			public void done(Sprite sprite) {
				sprite.setSprite(spriteIndex);
				hasLoaded = true;
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}
	public void setCurrentBasket(Basket targetBasket){
		// move it into depth between top and bottom of basket
		sprite.layer().setDepth(IN_BASKET);
		currentBasket = targetBasket;
		position = currentBasket.getPosition();
		hasInitialized =true;
	}
	
	public Basket getCurrentBasket() {
		return currentBasket;
	}
	
	public void paint(float alpha) {
		if (hasLoaded && hasInitialized) {
			spriteIndex = (spriteIndex + 1) % sprite.numSprites();
			sprite.setSprite(spriteIndex);
			sprite.layer().setTranslation(position.x, position.y);
		}
	}
	public void update(float delta) {
		if (hasLoaded && hasInitialized) {
			if(currentBasket==null)
			{
				// newPosition = position + velocity*time if on air
				position=position.add(velocity.multiply(delta));
				// newVelocity = velocity + gravitational accelaration constant * time
				velocity=velocity.add(new Vect2d(0, GameConstants.PhysicalProperties.verticalInPixels(GameConstants.PhysicalProperties.gravity)*delta/(1000*1000)));
				// check if it is in a basket
				int stars=0;
				for (int i = 0; i < getTargetBaskets().size() ; i++){
					if(getTargetBaskets().get(i).hit(this)!=0)
					{
						// generate event and update current basket
						if(getTargetBaskets().get(i) != lastBasket)
						{
							fireJumpEvent(getTargetBaskets().get(i), stars);
							currentBasket=getTargetBaskets().get(i);
						}else{
							currentBasket=lastBasket;
							log().debug("currentBasket is target basket again!!\n");
						}
						sprite.layer().setDepth(IN_BASKET);
					}
				}

			}else{
				// position is equal to currentBaskets position if they are attached.
				position=this.currentBasket.getPosition();
			}
		}
	}

	/**
	 * Use this function to make egg jump. 
	 * Jump generates JumpEvent as a result of failure or a success in landing. 
	 * Use addEventListener and removeEventListener to catch these events.
	 */
	public void jump(){
		if(this.currentBasket != null)
		{
			// Set the layer to TOP
			sprite.layer().setDepth(ON_TOP);
			
			// set y value to predefined jump speed
			velocity.y=GameConstants.PhysicalProperties.verticalInPixels(GameConstants.PhysicalProperties.JumpSpeed) / 1000;
			// set currentBasket basket to null since we are not attached to basket
			lastBasket = currentBasket;
			this.currentBasket=null;
			
			// play jump sound if game sound is on
			if (SoundControl.getInstance().isSoundOn()) {
				SoundControl.getInstance().playJump();
			}
		}
	}

	/**
	 * Removes listener
	 * @param eventListener listener for jump events
	 */
	public void removeEventListener(EggEventListener eventListener) {
		eventListeners.remove(eventListener);
	}
	/**
	 * Adds a EggEventListener to listen JumpEvents.
	 * @param eventListener listener for jump events.
	 */
	public void addEventListener(EggEventListener eventListener) {
		eventListeners.add(eventListener);
	}
	private void fireJumpEvent(Basket basket, int stars) {
		Iterator<EggEventListener> listeners = eventListeners.iterator();
		while( listeners.hasNext() ) {
			( (EggEventListener) listeners.next() ).onEggJump(basket,stars);
		}
	}
	@Override
	public ImageLayer getTopImageLayer() {
		return sprite.layer();
	}
	@Override
	public Vect2d getPosition() {
		return position;
	}
	@Override
	public void setPosition(Vect2d position) {
		this.position = position;
	}
	public List<Basket> getTargetBaskets() {
		return targetBaskets;
	}
	public void addTargetBasket(Basket targetBasket) {
		targetBaskets.add(targetBasket);
	}
	@Override
	public boolean isInRect(float x, float y, float width, float height) {
		if (hasLoaded && hasInitialized) 
			return  position.y + sprite.height() < height;
		else
			return super.isInRect(x, y, width, height);
	}
}
