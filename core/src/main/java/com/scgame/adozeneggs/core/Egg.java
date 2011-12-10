package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;

import com.scgame.adozeneggs.core.SpriteLoader;


public class Egg {
	  public static String IMAGE = "sprites/peasprites.png";
	  public static String JSON = "sprites/peasprite.json";
	  public static String JSON_WITH_IMAGE = "sprites/peasprite2.json";
	  private Sprite sprite;
	  private int spriteIndex = 1;
	  private float angle;
	  private boolean hasLoaded = false; // set to true when resources have loaded and we can update
	  public Egg(final GroupLayer eggLayer, final float x, final float y) {
		    // Sprite method #2: use json data describing the sprites and containing the image urls
		     sprite = SpriteLoader.getSprite(JSON_WITH_IMAGE);
		     // Add a callback for when the image loads.
		     // This is necessary because we can't use the width/height (to center the
		     // image) until after the image has been loaded
		     sprite.addCallback(new ResourceCallback<Sprite>() {
		       @Override
		       public void done(Sprite sprite) {
		         sprite.setSprite(spriteIndex);
		         sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
		         sprite.layer().setTranslation(x, y);
		         eggLayer.add(sprite.layer());
		         hasLoaded = true;
		       }

		       @Override
		       public void error(Throwable err) {
		         log().error("Error loading image!", err);
		       }
		     });
	  }
	  public void update(float delta) {
		    if (hasLoaded) {
		      if (Math.random() > 0.95) {
		        spriteIndex = (spriteIndex + 1) % sprite.numSprites();
		        sprite.setSprite(spriteIndex);
		      }
		      angle += delta;
		      sprite.layer().setRotation(angle);
		    }
		  }
}
