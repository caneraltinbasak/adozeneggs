package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledGroup extends ScrollableGroupEntity {
	private GroupLayer gLayer;
	private Vect2d position = new Vect2d(0, 0);
	private List<BGScrolledImage> entities = new ArrayList<BGScrolledImage>();
	private float scrollingSpeed = 0;
	private float scrollPosition = 0;
	private boolean hasScroll = false;

	public BGScrolledGroup(String imagePath0,String imagePath1,String imagePath2, float scrollingSpeed){
		this.gLayer = graphics().createGroupLayer();
		this.scrollingSpeed = scrollingSpeed;
		BGScrolledImage image0 = new BGScrolledImage(imagePath0);
		BGScrolledImage image1 = new BGScrolledImage(imagePath1);
		BGScrolledImage image2 = new BGScrolledImage(imagePath2);
		entities.add(image0);
		entities.add(image1);
		entities.add(image2);
		gLayer.add(image0.getTopImageLayer());
		gLayer.add(image1.getTopImageLayer());
		gLayer.add(image2.getTopImageLayer());
	}
	@Override
	public void init(){
		this.position = new Vect2d(0, 0);
		entities.get(0).setPosition(new Vect2d(0,0));
		entities.get(1).setPosition(new Vect2d(0,- entities.get(0).getTopImageLayer().height() ));
		entities.get(2).setPosition(new Vect2d(0,- entities.get(0).getTopImageLayer().height() - entities.get(1).getTopImageLayer().height()  ));
	}
	@Override
	public void paint(float alpha) {
		gLayer.setTranslation(position.x, position.y);
		for (int i=0;i<entities.size();i++)
			entities.get(i).paint(alpha);
	}

	@Override
	public void update(float delta) {
		if(hasScroll == true)
		{
			position.y = position.y + GameConstants.PhysicalProperties.verticalInPixels(scrollingSpeed) * delta / 1000;
			if(scrollPosition  <= position.y)
			{
				hasScroll = false;
			}
		}
		
		for(int i = 0 ; i <entities.size(); i++)
		{
			if(entities.get(i).isInRect(0, GameConstants.ScreenProperties.height - position.y, GameConstants.ScreenProperties.width, GameConstants.ScreenProperties.height - position.y)!=true)
			{
				entities.get(i).getPosition().y-=entities.get(i).getTopImageLayer().height()*3;
			}
		}
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
		hasScroll = true;
		if(scrollPosition < 0)
			scrollingSpeed = -scrollingSpeed;
		this.scrollPosition = scrollPosition;
	}

	@Override
	public GroupLayer getGroupLayer() {
		return gLayer;
	}


}
