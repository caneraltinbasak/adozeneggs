package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledGroup extends ScrollableGroupEntity {

	private Image bgImage;
	private ImageLayer imageLayer;
	private GroupLayer gLayer;
	private Vect2d position = new Vect2d(0, 0);

	public BGScrolledGroup(String imagePath0,String imagePath1,String imagePath2){
		this.gLayer = graphics().createGroupLayer();
	    this.imageLayer = graphics().createImageLayer();
		bgImage = (Image)CachedResource.getInstance().getResource(imagePath0);
		imageLayer.setImage(bgImage);
		gLayer.add(imageLayer);
	}

	@Override
	public void paint(float alpha) {
		imageLayer.setTranslation(position.x, position.y);
	}

	@Override
	public void update(float delta) {
		
	}


	@Override
	public Vect2d getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vect2d position) {
		this.position = position;
	}

	@Override
	public void scrollTo(float scrollPosition) {
		
	}

	@Override
	public GroupLayer getGroupLayer() {
		return gLayer;
	}

}
