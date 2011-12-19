package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.ResourceCallback;

import com.scgame.adozeneggs.core.SpriteLoader;


public class Egg{
	public static String IMAGE = "sprites/peasprites.png";
	public static String JSON = "sprites/peasprite.json";
	public static String JSON_WITH_IMAGE = "sprites/peasprite2.json";
	private List<EggEventListener> eventListeners = new ArrayList<EggEventListener>();
	private Sprite sprite;
	private int spriteIndex = 1;
	private boolean hasLoaded = false; // set to true when resources have loaded
	// and we can update
	private Basket currentBasket;
	private Basket targetBasket;

	// position
	private Vect2d position;

	// velocity(used when on air, uses currentBasket baskets velocity if not on air)
	private Vect2d velocity;


	/**
	 * Egg Constructor
	 * @param eggLayer Layer for egg graphics
	 * @param currentBasket currentBasket basket the egg is in
	 * @param targetBasket Next basket that egg is targetBasketing
	 */
	public Egg(final GroupLayer eggLayer, final Basket currentBasket, final Basket targetBasket) {
		// Sprite method #2: use json data describing the sprites and containing
		// the image urls
		this.targetBasket=targetBasket;
		this.currentBasket=currentBasket;
		sprite = SpriteLoader.getSprite(JSON_WITH_IMAGE);
		// Add a callback for when the image loads.
		sprite.addCallback(new ResourceCallback<Sprite>() {
			@Override
			public void done(Sprite sprite) {
				sprite.setSprite(spriteIndex);
				eggLayer.add(sprite.layer());
				hasLoaded = true;
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}
	public void paint(float alpha) {
		if (hasLoaded) {
			spriteIndex = (spriteIndex + 1) % sprite.numSprites();
			sprite.setSprite(spriteIndex);
			sprite.layer().setTranslation(50, 50);
		}
	}
	public void update(float delta) {
		if(currentBasket==null)
		{
			// newPosition = position + velocity*time if on air
			position=position.add(velocity.multiply(delta));
			// newVelocity = velocity + gravitational accelaration constant * time
			velocity=velocity.add(new Vect2d(0, GameConstants.PhysicalProperties.gravity*delta));
			int stars=0;
			if(targetBasket.hit(this, stars))
			{
				// generate event and update current basket
				fireJumpEvent(targetBasket, stars);
				currentBasket=targetBasket;
			}
		}else{
			// position is equal to currentBaskets position if they are attached.
			position=this.currentBasket.getPosition();
		}
		
	}

	/**
	 * Use this function to make egg jump. 
	 * Jump generates JumpEvent as a result of failure or a success in landing. 
	 * Use addEventListener and removeEventListener to catch these events.
	 */
	public void jump(){
		// get currentBasket baskets velocity. y value is expected to be "0"
		this.velocity=this.currentBasket.getVelocity();
		// set y value to predefined jump speed
		velocity.y=GameConstants.PhysicalProperties.JumpSpeed;
		// set currentBasket basket to null since we are not attached to basket
		this.currentBasket=null;
	}
	/**
	 * Adds a new targetBasket. Game should add a new targetBasket after each JumpEvent handler.
	 * @param targetBasket New targetBasket for the jump.
	 */
	public void addNewtargetBasket(Basket targetBasket){
		this.targetBasket=targetBasket;
	}
	/**
	 * Removes listener
	 * @param eventListener listener for jump events
	 */
	public synchronized void removeEventListener(EggEventListener eventListener) {
		eventListeners.remove(eventListener);
	}
	/**
	 * Adds a EggEventListener to listen JumpEvents.
	 * @param eventListener listener for jump events.
	 */
	public synchronized void addEventListener(EggEventListener eventListener) {
		eventListeners.add(eventListener);
	}
	private synchronized void fireJumpEvent(Basket basket, int stars) {
		JumpEvent event = new JumpEvent( this, basket, stars);
		Iterator<EggEventListener> listeners = eventListeners.iterator();
		while( listeners.hasNext() ) {
			( (EggEventListener) listeners.next() ).onEggJump(event);
		}
	}
}
