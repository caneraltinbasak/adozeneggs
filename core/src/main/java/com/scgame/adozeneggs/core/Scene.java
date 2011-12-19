
package com.scgame.adozeneggs.core;

import playn.core.Game;
import playn.core.Keyboard;

/**
 * Demonstrates a particular PlayN feature or set of features. This interface is kind of like
 * {@link Game} except that it has lifecycle methods for cleaning up as well as initializing. Demos
 * also all share a predefined update rate (25 fps).
 */
public abstract class Scene
{
  /**
   * Returns the name of this demo.
   */
  public abstract String name();

  /**
   * Initializes this demo. Here is where listeners should be wired up and resources loaded.
   */
  public abstract void init(Object data);

  /**
   * Shuts down this demo. Listeners should be cleared and resources destroyed.
   */
  public abstract void shutdown();

  /**
   * Called every update tick while this demo is active.
   * @param delta the amount of time that has elapsed since the last update call.
   */
  public void update(float delta) {
  }

  /**
   * Called while this demo is active, to paint the demo.
   * @param alpha a value in the range [0,1) that represents the fraction of the update tick that
   * has elapsed since the last call to update.
   */
  public void paint(float alpha) {
  }

  /**
   * Because the showcase uses a few keys to move between demos, a demo must not register a
   * keyboard listener directly, but instead return its listener from this method. This allows the
   * showcase to intercept the keys it needs and to pass on other key events to the demo.
   */
  public Keyboard.Listener keyboardListener() {
    return null;
  }
}
