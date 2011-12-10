package com.scgame.adozeneggs.core;

import java.util.EventObject;

public class JumpEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5326055228052962956L;
	/**
	 * basket shows the next basket that egg has landed if egg lands on a basket successfully.
	 * If eggs falls on ground the value is NULL.
	 */
	private Basket basket;
	/**
	 * if there is an Basket object in basket variable, check for the stars for how good the landing is.
	 */
	private int stars;

	public JumpEvent(Object source, Basket basket, int stars) {
		super(source);
		this.basket = basket;
		this.stars = stars;
	}

	public Basket getBasket() {
		return basket;
	}
	public int getStars() {
		return stars;
	}
}
