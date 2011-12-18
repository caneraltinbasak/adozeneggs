package com.scgame.adozeneggs.core;

import static playn.core.PlayN.log;
import playn.core.GroupLayer;
import playn.core.ResourceCallback;

public class LoadingBar {
	private Vect2d position; 	// x coordinate in pixel value
	private Sprite sprite;
	private GroupLayer gLayer = null;
	
	public LoadingBar(int x, int y, String spriteJsonPath){
		this.position= new Vect2d(x, y);
		sprite = SpriteLoader.getSprite(spriteJsonPath);
		sprite.addCallback(new ResourceCallback<Sprite>() {
			
			@Override
			public void error(Throwable err) {
				log().error("LoadingBar::LoadingBar() : Error loading LoadingBar", err);
			}
			
			@Override
			public void done(Sprite resource) {
				gLayer.setTranslation(position.x, position.y);
				gLayer.add(sprite.layer());
			}
		});
	}

	public void onPercentUpdate(float percent) {
		sprite.setSprite((int)percent*10);
	}
	public GroupLayer getLayer () {
		return this.gLayer;
	}
}
