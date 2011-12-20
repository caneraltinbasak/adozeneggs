
package com.scgame.adozeneggs.core;

import playn.core.Keyboard;

public abstract class Scene
{
  /**
   * Initializes this scene. Here is where listeners should be wired up and resources loaded.
   */
  public abstract void init(Object data);

  /**
   * Shuts down this scene. Listeners should be cleared and resources destroyed.
   */
  public abstract void shutdown();


  public void update(float delta) {
  }


  public void paint(float alpha) {
  }
  
  public Keyboard.Listener keyboardListener() {
    return null;
  }
}
