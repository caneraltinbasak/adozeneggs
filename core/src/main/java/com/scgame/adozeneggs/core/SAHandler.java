package com.scgame.adozeneggs.core;
import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;
import static playn.core.PlayN.net;
import static playn.core.PlayN.storage;

import java.util.ArrayList;

import playn.core.Json;
import playn.core.ResourceCallback;
import playn.core.util.Callback;

/*
 * Score and Achievement Handler class
 */
public class SAHandler {
	private static SAHandler instance = null;
	private AchievementScreen achievementScreen = null;
	private Score highScoreToday = new Score(eScore.DAY);
	private Score highScoreWeek = new Score(eScore.WEEK);
	private Score highScoreAllTime = new Score(eScore.ALL_TIME);
	
	private long liveScore = 0;
	private float timeElapsedForAch = 0;
	private float timeElapsedOnBasket = 0; // in millisecond - to decrease live score while the egg is waiting on the basket
	private long totalJumpCount = 0; // total jump count of egg
	private long fastJumpCount = 0;  
	private long threeStarJumpCount = 0; 
	private boolean achScreenActive = false;
	private ArrayList<String> achToShow = new ArrayList<String>();
	
	private SAHandler() {
		storage().setItem(SAConstants.ACH_FIRST_FAST_JUMP, SAConstants.LOCKED);
		storage().setItem(SAConstants.ACH_FIRST_THREE_STAR_JUMP, SAConstants.LOCKED);
		storage().setItem(SAConstants.CHARACTER1_LOCK, SAConstants.UNLOCKED);
		
		
		// Initializing all achievements as locked, if they were not initialized before
		if (storage().getItem(SAConstants.ACH_FIRST_FAST_JUMP) == null) {
			storage().setItem(SAConstants.ACH_FIRST_FAST_JUMP, SAConstants.LOCKED);
		}
		if (storage().getItem(SAConstants.ACH_FIRST_THREE_STAR_JUMP) == null) {
			storage().setItem(SAConstants.ACH_FIRST_THREE_STAR_JUMP, SAConstants.LOCKED);
		}
		if (storage().getItem(SAConstants.CHARACTER1_LOCK) == null) {
			storage().setItem(SAConstants.CHARACTER1_LOCK, SAConstants.LOCKED);
		}
		if (storage().getItem(SAConstants.CHARACTER2_LOCK) == null) {
			storage().setItem(SAConstants.CHARACTER2_LOCK, SAConstants.LOCKED);
		}
		if (storage().getItem(SAConstants.CHARACTER3_LOCK) == null) {
			storage().setItem(SAConstants.CHARACTER3_LOCK, SAConstants.LOCKED);
		}
	}
	
	public static SAHandler getInstance() {
		if (instance == null) {
			instance = new SAHandler();
		}
		return instance;
	}
	
	public void setAchievementScreen(AchievementScreen achievementScreen) {
		this.achievementScreen = achievementScreen;
	}
	
	public void update(float delta) {
		if (achScreenActive == true) {
			timeElapsedForAch += delta;
		}
	}
	
	public void paint(float alpha) {
		if (achScreenActive == false) {
			// Check if there is any achievement to show in queue. 
			// (We used queue here, because there may be more than one achievement at the same time)
			if (achToShow.size() > 0) {
				String achStr = achToShow.get(0);
				achievementScreen.setAchievement(achStr);
				achScreenActive = true;
				timeElapsedForAch = 0;
				achToShow.remove(achStr);
			}
		}
		else {
			// check for achievement screen, hide after elapsed time is ACHIEVEMENT_INTERVAL
			if (timeElapsedForAch > SAConstants.ACHIEVEMENT_INTERVAL) {
				achievementScreen.hide();
				achScreenActive = false;
				timeElapsedForAch = 0;
			}
		}
	}
		
	public void resetLiveScore() {
		liveScore = 0;
	}
	
	public void updateLiveScoreWithJump(int star) {
		liveScore += star * SAConstants.JUMP_SCORE;
	}
	
	public void updateLiveScoreWithUpdate(float delta) {
		timeElapsedOnBasket += delta;
		
		if (timeElapsedOnBasket > 100 && liveScore != 0) {
			liveScore += SAConstants.WAITING_NEGATIVE_SCORE;
		}
	}
	
	public long getLiveScore() {
		return liveScore;
	}
	
	// Stores user high score in local storage
	public void setLocalHighScore(long newScore) {
		long highScore = getLocalHighScore();
		if (newScore > highScore) {
			String strScore = String.valueOf(newScore);
			storage().setItem(SAConstants.LOCAL_HIGH_SCORE, strScore);
		}
	}
	
	public long getLocalHighScore() {
		if (storage().getItem(SAConstants.LOCAL_HIGH_SCORE) == null) {
			return 0;
		}
		else {
			String strScore = storage().getItem(SAConstants.LOCAL_HIGH_SCORE);
			return Long.parseLong(strScore);
		}
	}
	
	public Score getHighScoreToday() {
		return highScoreToday;
	}
	
	public Score getHighScoreWeek() {
		return highScoreWeek;
	}
	
	public Score getHighScoreAllTime() {
		return highScoreAllTime;
	}
	
	// sets high scores on app engine by "user" and "score" params in URL
	public void setHighScores(final long score) {
		setLocalHighScore(score);
		
		assetManager().getText("Score.json", new ResourceCallback<String>() {

			@Override
			public void done(String resource) {
				Json.Object document = json().parse(resource);
				Json.Object objScore = document.getObject("score");
		   	  	String url = objScore.getString("url");
		   	  	url = url + "?user=serhat&score=" + score;
		   	  	
		   	  	net().get(url, new Callback<String>() {
					
		   	  		@Override
					public void onSuccess(String result) {
						parseResponse(result);
					}
					
					@Override
					public void onFailure(Throwable cause) {
						System.out.println("Failure : " + cause.getMessage());
						log().error("Error accessing scores : ", cause);
					}
				});
			}

			@Override
			public void error(Throwable err) {
				log().error(" Error in Score.json \n" , err);	
			}
		});
	}
	
	public void parseResponse(String response) {
		Json.Object document = json().parse(response);
		Json.Object objToday = document.getObject(SAConstants.TODAY);
		Json.Object objWeek = document.getObject(SAConstants.WEEK);
		Json.Object objAll = document.getObject(SAConstants.ALL_TIME);
		
		/*
		System.out.println("today");
		System.out.println("user:" + objToday.getString("user") + 
							" score:" +  objToday.getInt("score") +
							" dayOfMonth:" + objToday.getInt("dayOfMonth") + 
							" month:" + objToday.getInt("month") + 
							" year:"  + objToday.getInt("year"));
		
		System.out.println("week");
		System.out.println("user:" + objWeek.getString("user") + 
				" score:" +  objWeek.getInt("score") +
				" dayOfMonth:" + objWeek.getInt("dayOfMonth") + 
				" month:" + objWeek.getInt("month") + 
				" year:"  + objWeek.getInt("year"));
		
		System.out.println("alltime");
		System.out.println("user:" + objAll.getString("user") + 
				" score:" +  objAll.getInt("score") +
				" dayOfMonth:" + objAll.getInt("dayOfMonth") + 
				" month:" + objAll.getInt("month") + 
				" year:"  + objAll.getInt("year"));
		*/
		
		highScoreToday.user = objToday.getString(SAConstants.USER);
		highScoreToday.score = objToday.getInt(SAConstants.SCORE);
		highScoreToday.dayOfMonth = objToday.getInt(SAConstants.DAY_OF_MONTH);
		highScoreToday.month = objToday.getInt(SAConstants.MONTH);
		highScoreToday.year = objToday.getInt(SAConstants.YEAR);
		
		highScoreWeek.user = objWeek.getString(SAConstants.USER);
		highScoreWeek.score = objWeek.getInt(SAConstants.SCORE);
		highScoreWeek.dayOfMonth = objWeek.getInt(SAConstants.DAY_OF_MONTH);
		highScoreWeek.month = objWeek.getInt(SAConstants.MONTH);
		highScoreWeek.year = objWeek.getInt(SAConstants.YEAR);
		
		highScoreAllTime.user = objAll.getString(SAConstants.USER);
		highScoreAllTime.score = objAll.getInt(SAConstants.SCORE);
		highScoreAllTime.dayOfMonth = objAll.getInt(SAConstants.DAY_OF_MONTH);
		highScoreAllTime.month = objAll.getInt(SAConstants.MONTH);
		highScoreAllTime.year = objAll.getInt(SAConstants.YEAR);
	}
	
	public void jumped(int star) {
		totalJumpCount++;
		updateLiveScoreWithJump(star);
		
		if (timeElapsedOnBasket < SAConstants.FAST_JUMP_INTERVAL) {
			System.out.println(timeElapsedOnBasket + " < " + SAConstants.FAST_JUMP_INTERVAL);
			fastJumpCount++;
		}
		
		if (star == 3) {
			threeStarJumpCount++;
		}
		
		checkForAchievements();
		
		timeElapsedOnBasket = 0;
	}
	
	public void checkForAchievements() {
		if (storage().getItem(SAConstants.ACH_FIRST_FAST_JUMP).equals(SAConstants.LOCKED) && fastJumpCount > 0) {
			storage().setItem(SAConstants.ACH_FIRST_FAST_JUMP, SAConstants.UNLOCKED);
			achToShow.add(SAConstants.ACH_FIRST_FAST_JUMP);
		}
		
		if (storage().getItem(SAConstants.ACH_FIRST_THREE_STAR_JUMP).equals(SAConstants.LOCKED) && threeStarJumpCount > 0) {
			storage().setItem(SAConstants.ACH_FIRST_THREE_STAR_JUMP, SAConstants.UNLOCKED);
			achToShow.add(SAConstants.ACH_FIRST_THREE_STAR_JUMP);
		}
	}
}
