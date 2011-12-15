package com.scgame.adozeneggs.core;

/**
 * Callback interface used to notify completion or failure of a button request.
 */
public interface ButtonCallback {

  /**
   * Called when the button is successfully created.
   */
  void done();

  /**
   * Called when the resource fails to create.
   */
  void error(Throwable err);
}
