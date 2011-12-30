package com.scgame.adozeneggs.core;

/**
 * @author Caner
 * GameConstants are the constants which either should be defined in initialization or
 * built into the game. Objects should be initialized with these constants.
 */
public class GameConstants {
	/**
	 * @author Caner
	 * ScreenProperties defines the properties of the drawing surface. 
	 * It should be set at main initialization stage. 
	 */
	private GameConstants(){
		throw new AssertionError();
	}
	public static class GameProperties{
		public static final float FIRST_BASKET_Y_OFFSET = 1f;
		public static final float BASKET_GAP = 2.5f;

	}
	public static class ScreenProperties{
		/**
		 * width of screen
		 */
		public static int width;
		/**
		 * height of screen
		 **/
		public static int height;
		/**
		 * screen dot-per-inch if available
		 * 
		 */
		public int dpi;
		/**
		 * high resolution graphics
		 */
		public static final String HIGH = "high";
		/**
		 * medium resolution graphics
		 */
		public static final String MEDIUM = "medium";
		/**
		 * low resolution graphics
		 */
		public static final String LOW = "low";
		/**
		 * Quality of the sprites that will be used. The values should be HIGH(1),MEDIUM(2) or LOW(3)
		 */
		public static String gQuality;
		
		/**
		 * Frame rate
		 */
		public static int FRAME_RATE = 30;
		
		/**
		 * Update period in milliseconds.
		 */
		public static int UPDATE_MS = 1000/FRAME_RATE;
	}

	public static class PhysicalProperties{
		/**
		 * Limit Speed
		 */
		public static final float limitSpeed = 20;
		/**
		 * gravitational acceleration 
		 */
		public static final float gravity = 10; // m/s^2
		/**
		 * Jump speed of the egg
		 */
		public static final float JumpSpeed = - 12f; // m/s original value is 7.5f 
		/**
		 * Virtual width in meters
		 */
		public static final float width = 6.0f; // m
		/** 
		 * virtual height in meters
		 */
		public static final float height = 10.0f; // m
		
		/**
		 * Base scrolling speed of foreground
		 */
		public static final float ForegroundScrollSpeed = 5.0f; // m/s
		
		/**
		 * Converts horizontal physical property(speed, position) to screen pixels.
		 * @param meters Distance in meters
		 * @return Distance in pixels.
		 */
		public static float horizontalInPixel(float meters){
			return (meters/PhysicalProperties.width)*ScreenProperties.width;
		}
		public static float verticalInPixels(float meters){
			return (meters/PhysicalProperties.height)*ScreenProperties.height;
		}
		/**
		 * Converts coordinates in meters to screen pixels. Returns a new
		 * @param posInMeters Position in meters.
		 * @return Position in pixel coordinates.
		 */
		public static Vect2d convertToPixel(final Vect2d posInMeters){
			return new Vect2d(horizontalInPixel(posInMeters.x), verticalInPixels(posInMeters.y));
		}
		
	}
}
