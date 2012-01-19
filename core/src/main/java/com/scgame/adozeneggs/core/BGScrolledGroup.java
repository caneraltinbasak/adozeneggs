package com.scgame.adozeneggs.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;


import java.util.ArrayList;
import java.util.List;

import com.scgame.adozeneggs.tweener.Circ;
import com.scgame.adozeneggs.tweener.Cubic;
import com.scgame.adozeneggs.tweener.Quart;
import com.scgame.adozeneggs.tweener.Tweener;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

public class BGScrolledGroup extends ScrollableGroupEntity implements OnScreenCheckInterface {
	private GroupLayer gLayer;
	private Vect2d position = new Vect2d(0, 0);
	private List<BGScrolledImage> entities = new ArrayList<BGScrolledImage>();
	private float scrollPosition = 0;
	private float scrollingSpeedFactor;
	private Tweener scrollTween=new Tweener(new Quart());
	enum ScrollingDirection {
		SCROLLING_UP,
		SCROLLING_DOWN,
		NOT_SCROLLING
	}

	public BGScrolledGroup(String imagePath0,String imagePath1,String imagePath2, float scrollingSpeed){
		this.gLayer = graphics().createGroupLayer();
		this.scrollingSpeedFactor = scrollingSpeed;
		BGScrolledImage image0 = new BGScrolledImage(imagePath0);
		BGScrolledImage image1 = new BGScrolledImage(imagePath1);
		BGScrolledImage image2 = new BGScrolledImage(imagePath2);
		image0.setParentRect(this);
		image1.setParentRect(this);
		image2.setParentRect(this);
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
		scrollPosition = 0;
	}
	@Override
	public void paint(float alpha) {
		gLayer.setTranslation(position.x, position.y);
		for (int i=0;i<entities.size();i++)
			entities.get(i).paint(alpha);
	}

	@Override
	public void update(float delta) {
		position.y = scrollTween.getValue();

		for (int i=0;i<entities.size();i++)
			entities.get(i).update(delta);
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
		scrollTween.clear();
		scrollTween.add(0, this.position.y, Tweener.EASING_OUT);
		scrollTween.add(1f, scrollPosition);

		this.scrollPosition = scrollPosition;
	}

	@Override
	public GroupLayer getGroupLayer() {
		return gLayer;
	}
	@Override
	public float getHeight() {
		float height=0;
		for (int i=0;i<entities.size();i++)
			height+=entities.get(i).getHeight();
		return height;
	}
	@Override
	public eOnScreenState isInRect(Entity entity) {
		switch(getScrollingDirection())
		{
		case SCROLLING_UP:
			if( entity.getPosition().y > GameConstants.ScreenProperties.height - position.y){
				return eOnScreenState.TOP_IS_UNDER_VISIBLE_AREA;
			}else if(entity.getPosition().y + entity.getHeight() > GameConstants.ScreenProperties.height - position.y){
				return eOnScreenState.BOTTOM_IS_UNDER_VISIBLE_AREA;
			}
			break;
		case SCROLLING_DOWN:
			if(entity.getPosition().y + entity.getHeight() <  position.y){
				return eOnScreenState.BOTTOM_IS_OVER_VISIBLE_AREA;
			}else if(entity.getPosition().y < position.y ){
				return eOnScreenState.TOP_IS_OVER_VISIBLE_AREA;
			}
			break;
		case NOT_SCROLLING:
			break;
		}


		return eOnScreenState.TOTAL_IS_IN_VISIBLE_AREA;
	}
	private ScrollingDirection getScrollingDirection() {
		if( scrollPosition>position.y )
			return ScrollingDirection.SCROLLING_UP;
		else if (scrollPosition<position.y)
			return ScrollingDirection.SCROLLING_DOWN;
		else
			return ScrollingDirection.NOT_SCROLLING;
	}
	@Override
	public void stopScroll() {
	}


}
