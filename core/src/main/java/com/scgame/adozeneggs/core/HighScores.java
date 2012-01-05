package com.scgame.adozeneggs.core;
import static playn.core.PlayN.storage;
import static playn.core.PlayN.net;
import playn.core.util.Callback;

public class HighScores {
	private static HighScores instance = null;
	
	private HighScores() {	
	}
	
	public static HighScores getInstance() {
		if (instance == null) {
			instance = new HighScores();
		}
		return instance;
	}
	
	public void setLocalHighScore(long newScore) {
		long highScore = getLocalHighScore();
		if (newScore > highScore) {
			String strScore = String.valueOf(newScore);
			storage().setItem("localHighScore", strScore);
		}

	}
	
	public long getLocalHighScore() {
		if (storage().getItem("localHighScore") == null) {
			return 0;
		}
		else {
			String strScore = storage().getItem("localHighScore");
			return Long.parseLong(strScore);
		}
	}
	
	public void setHighScores(long score) {
		setLocalHighScore(score);
		
		String url = "http://adozeneggs1234.appspot.com/highscores?user=serhat&score=" + score;
		
		net().get(url, new Callback<String>() {
			
			@Override
			public void onSuccess(String result) {
				System.out.println("Success : " + result);
				
			}
			
			@Override
			public void onFailure(Throwable cause) {
				System.out.println("Failure : " + cause.getMessage());
				
			}
		});
	}
}
