package com.scgame.adozeneggs.core;

enum eScore {
	DAY, WEEK, ALL_TIME
}
public class Score {
	public eScore type;
	public String user;
	public int score;
	public int dayOfMonth;
	public int month;
	public int year;
	
	public Score(eScore type) {
		this.type = type;
	}
}
