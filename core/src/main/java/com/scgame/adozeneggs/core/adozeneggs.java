package com.scgame.adozeneggs.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;

public class adozeneggs implements Game {
	  GroupLayer layer;

  @Override
  public void init() {
	  
	    // create a group layer to hold everything
	    layer = graphics().createGroupLayer();
	    graphics().rootLayer().add(layer);
	    
    // create and add background image layer
    Image bgImage = assetManager().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    layer.add(bgLayer);
    // add a listener for pointer (mouse, touch) input
    pointer().setListener(new Pointer.Adapter() {
      @Override
      public void onPointerEnd(Pointer.Event event) {
        Egg egg = new Egg(layer, event.x(), event.y());
      }
    });
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public int updateRate() {
    return 25;
  }
}
