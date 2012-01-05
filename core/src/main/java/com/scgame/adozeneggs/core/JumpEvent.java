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

	public JumpEvent(Object source, Basket basket) {
		super(source);
		this.basket = basket;
	}

	public Basket getBasket() {
		return basket;
	}
}
