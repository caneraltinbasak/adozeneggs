package com.scgame.adozeneggs.core;

public class SAConstants {
	public static String LOCAL_HIGH_SCORE = "localHighScore"; 
	public static String TODAY = "today"; 
	public static String WEEK = "week";
	public static String ALL_TIME = "alltime";
	public static String USER = "user";
	public static String SCORE = "score";
	public static String DAY_OF_MONTH = "dayOfMonth";
	public static String MONTH = "month";
	public static String YEAR = "year";
	
	public static int JUMP_SCORE = 100; // Score that user gets with each jump of egg
	public static int WAITING_NEGATIVE_SCORE = -1; // Score that user losses while egg is waiting on basket
	public static float FAST_JUMP_INTERVAL = 1000f; // in millisecond - if egg waits less then 1000 ms on the basket before jumping, it means fast jump
	public static float ACHIEVEMENT_INTERVAL = 2000f; // in millisecond - duration that achievement notice will reside on screen
	
	public static String LOCKED = "locked";
	public static String UNLOCKED = "unlocked";
	public static String ACH_FIRST_FAST_JUMP = "achFirstFastJump";
	public static String ACH_FIRST_THREE_STAR_JUMP = "achFirstThreeStarJump";
	
	public static String IMG_FIRST_FAST_JUMP_PATH = "images/first_fast_jump.png";  // Image path for First Fast Jump achievement
	public static String IMG_FIRST_THREE_STAR_JUMP_PATH = "images/first_three_star_jump.png";  // Image path for First Three Star Jump achievement
	
	public static String CHARACTER1_LOCK = "character1Lock";
	public static String CHARACTER2_LOCK = "character2Lock";
	public static String CHARACTER3_LOCK = "character3Lock";
	public static String CHARACTER4_LOCK = "character4Lock";
	public static String CHARACTER5_LOCK = "character5Lock";
	public static String CHARACTER6_LOCK = "character6Lock";
}
