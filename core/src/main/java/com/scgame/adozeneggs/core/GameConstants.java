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
	
	public static class ScreenProperties{
		/**
		 * width of screen
		 */
		public static int width = 320;
		/**
		 * height of screen
		 **/
		public static int height = 480;
		/**
		 * screen dot-per-inch if available
		 * 
		 */
		public int dpi;
		/**
		 * high resolution graphics
		 */
		public static final int HIGH = 1;
		/**
		 * medium resolution graphics
		 */
		public static final int MEDIUM = 2;
		/**
		 * low resolution graphics
		 */
		public static final int LOW = 3;
		/**
		 * Quality of the sprites that will be used. The values should be HIGH(1),MEDIUM(2) or LOW(3)
		 */
		public int gQuality;
	}
	public class PhysicalProperties{
		/**
		 * gravity is defined by pixels/miliseconds.
		 */
		public static final float gravity = 2;
		public static final float JumpSpeed = 5;
	}
}
