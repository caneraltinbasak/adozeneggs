package com.scgame.adozeneggs.core;
import static playn.core.PlayN.storage;

public class Scores {
	private long localHighScore;
	
	public void setLocalHighScore(long score) {
		String strScore = String.valueOf(score);
		storage().setItem("localHighScore", strScore);
	}
	

}
