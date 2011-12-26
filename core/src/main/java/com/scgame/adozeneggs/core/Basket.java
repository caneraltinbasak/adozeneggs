package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;


public class Basket extends GraphicsEntity{
	protected Image basketImage = null; // image
	private float width;  // pixel
	private float height; // pixel
	private float velocity; // pixels/milliseconds
	private Vect2d position; // pixel
	private Vect2d startPosition; //pixel
	private Vect2d endPosition; //pixel
	protected ImageLayer imageLayer = null;

	
	public Basket( float velocity, Vect2d startPosition, Vect2d endPosition, String imagePath) {
		this.velocity = velocity;
		this.startPosition = startPosition.copy();
		this.position = startPosition.copy();
	    this.imageLayer = graphics().createImageLayer();
	    this.imageLayer.setTranslation(startPosition.x, startPosition.y);
	    this.endPosition = endPosition;
		basketImage = (Image)CachedResource.getInstance().getResource(imagePath);
		imageLayer.setImage(basketImage);
		width = basketImage.width();
		height = basketImage.height();
	}

	/**
	 * Returns basket velocity in pixel/milliseconds.
	 * @return basket velocity
	 */
	public float getVelocity() {
		return velocity;
	}

	/**
	 * Sets basket velocity (pixel/milliseconds)
	 * @param velocity
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	/**
	 * Get Current position in pixels
	 * @return Current position
	 */
	public Vect2d getPosition() {
		return position;
	}

	/**
	 * Set current position in pixels
	 * @param position current position in pixels.
	 */
	public void setPosition(Vect2d position) {
		this.position = position;
	}

	/**
	 * Returns the image layer of the basket.
	 * @return Basket's image layer
	 */
	public ImageLayer getImageLayer() {
		return imageLayer;
	}

	/**
	 * Apply hit test
	 * @param egg object that hit test will be applied
	 * @return returns "0" in case of not hit. If basket is hit,
	 * returns 1,2,3 depending on success of hit.
	 */
	public int hit(Egg egg) {
		if(egg.velocity.y > 0) // if going downwards
		{
			// check if egg is 10 pixel margin of position.y
			if(egg.sprite.height() + egg.position.y > position.y && egg.sprite.height() + egg.position.y < position.y+ 30) 
			{
				if(egg.position.x >= position.x && egg.position.x <= position.x + width)
				{
					return 1;
				}
			}
		}
		return 0;
	}
	public void paint(float alpha) {
		imageLayer.setTranslation(position.x, position.y);
	}
	public void update(float delta) { // delta in milliseconds.
		position.x += velocity * delta;
		if(position.x + width > endPosition.x || position.x < startPosition.x) // return back if reached the end, or start
		{
			velocity = - velocity;
		}
	}
}
