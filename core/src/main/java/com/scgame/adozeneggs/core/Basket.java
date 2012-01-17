package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;


public class Basket extends DoubleLayerGraphicsEntity{
	private static final String BASKET_HIGH_TOP_IMAGE_PATH = "images/basket_high.png";
	private static final String BASKET_MEDIUM_TOP_IMAGE_PATH = "images/basket_medium.png";
	private static final String BASKET_LOW_TOP_IMAGE_PATH = "images/basket_medium.png";
	private static final String BASKET_HIGH_BOTTOM_IMAGE_PATH = "images/basket_high.png";
	private static final String BASKET_MEDIUM_BOTTOM_IMAGE_PATH = "images/basket_medium.png";
	private static final String BASKET_LOW_BOTTOM_IMAGE_PATH = "images/basket_medium.png";
	
	private static final float TOP_LAYER = 200f;
	private static final float BOTTOM_LAYER = 0f;
	private Image topBasketImage = null; // image
	private Image bottomBasketImage = null; // image
	private float width;  // pixel
	private float height; // pixel
	private float velocity = 0.0f; // pixels/milliseconds
	private Vect2d position = new Vect2d(0, 0); //pixel
	private Vect2d startPosition = new Vect2d(0, 0); //pixel
	private Vect2d endPosition = new Vect2d(0, 0); //pixel
	private ImageLayer topImageLayer = null;
	private ImageLayer bottomImageLayer = null;
	private boolean initComplete = false;
	private OnScreenCheckInterface parentRect;


	
	public Basket() {
	    if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.HIGH){
	    	topBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_HIGH_TOP_IMAGE_PATH);
	    	bottomBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_HIGH_BOTTOM_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.MEDIUM){
	    	topBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_MEDIUM_TOP_IMAGE_PATH);
	    	bottomBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_MEDIUM_BOTTOM_IMAGE_PATH);
	    }else if(GameConstants.ScreenProperties.gQuality == GameConstants.ScreenProperties.LOW){
	    	topBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_LOW_TOP_IMAGE_PATH);
	    	bottomBasketImage = (Image)CachedResource.getInstance().getResource(BASKET_LOW_BOTTOM_IMAGE_PATH);
	    }
	    this.topImageLayer = graphics().createImageLayer();
	    topImageLayer.setImage(topBasketImage);
	    topImageLayer.setDepth(TOP_LAYER);
	    this.bottomImageLayer = graphics().createImageLayer();
	    bottomImageLayer.setImage(bottomBasketImage);
	    bottomImageLayer.setDepth(BOTTOM_LAYER);
		width = topBasketImage.width();
		height = topBasketImage.height();
		type = eEntity.BASKET;
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
	public ImageLayer getTopImageLayer() {
		return topImageLayer;
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
				// TODO: Commented out this part for easier testing. Comment in for real application
				// TODO: Implement star rating
				if(egg.position.x >= position.x - 30 && egg.position.x - 30 <= position.x + width)
				{
					return 1;
				}
			}
		}
		return 0;
	}
	public void paint(float alpha) {
		if(initComplete)
		{
			topImageLayer.setTranslation(position.x+10, position.y+10); // TODO: remove this +10 translation
			bottomImageLayer.setTranslation(position.x, position.y);	
		}
	}
	public void update(float delta) { // delta in milliseconds.
		if(initComplete)
		{
			position.x += velocity * delta;
			if(position.x + width > getEndPosition().x || position.x < getStartPosition().x) // return back if reached the end, or start
			{
				velocity = - velocity;
			}
		}
		switch(getParentRect().isInRect(this))
		{
		case TOP_IS_UNDER_VISIBLE_AREA:
			position.y-=GameConstants.ScreenProperties.height*2;
			break;
		default:
			break;
		}
	}

	@Override
	public ImageLayer getBottomImageLayer() {
		return bottomImageLayer;
	}

	public Vect2d getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Vect2d startPosition) {
		this.startPosition = startPosition;
	}

	public Vect2d getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Vect2d endPosition) {
		this.endPosition = endPosition;
	}
	public void initializeProperties(float velocity, Vect2d startPosition, Vect2d endPosition)
	{
		this.startPosition=startPosition;
		this.position = startPosition.copy();
		this.endPosition = endPosition;
		this.velocity =velocity;
		initComplete = true;
	}

	@Override
	public float getHeight() {
		return this.height;
	}

	public OnScreenCheckInterface getParentRect() {
		return parentRect;
	}

	public void setParentRect(OnScreenCheckInterface parentRect) {
		this.parentRect = parentRect;
	}
}
