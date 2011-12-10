package com.scgame.adozeneggs.core;

public class Basket {

	public Basket(Vect2d velocity, Vect2d position) {
		super();
		this.velocity = velocity;
		this.position = position;
	}

	private Vect2d velocity;
	private Vect2d position;

	public Vect2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vect2d velocity) {
		this.velocity = velocity;
	}

	public Vect2d getPosition() {
		return position;
	}

	public void setPosition(Vect2d position) {
		this.position = position;
	}

	public boolean hit(Egg egg, int stars) {
		stars=1;
		return false;
	}

}
