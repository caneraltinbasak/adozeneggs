package com.scgame.adozeneggs.tweener;

import com.scgame.adozeneggs.tweener.KeyFloat;

public interface KeyEvent 
{
	/**
	 * will be called when the time of a key has been reached.
	 * @param keyFloat current keyfloat
	 */
	public void KeyEvent(KeyFloat keyFloat);
	
}
