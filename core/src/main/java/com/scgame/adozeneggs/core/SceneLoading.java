package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import com.scgame.adozeneggs.core.SceneNavigator;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.ResourceCallback;

public class SceneLoading extends Scene implements LoaderInterface{
	private GroupLayer gLayer = null;
	private Image bgImage;
	private LoadingBar loadingBar;
	private String jsonPath = "layouts/SceneLoading.json";
	private boolean loadingComplete = false;
	private float percentComplete= 0;
	
	public SceneLoading(){
		initImageLayouts();
		gLayer.setVisible(false);
	}
	private void initImageLayouts() {
		gLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(gLayer);
		assetManager().getText(jsonPath , new ResourceCallback<String>() {

			@Override
			public void done(String resource) {
				Json.Object document = json().parse(resource);
				Json.Object imgLocation = document.getObject("image_location");
				// parse resolutions
				Json.Array arrRes = imgLocation.getArray("resolution");
				for (int i = 0; i < arrRes.length(); i++) {
					Json.Object resolution = arrRes.getObject(i);
					int height = resolution.getInt("height");
					int width = resolution.getInt("width");
					if ((width == GameConstants.ScreenProperties.width) && (height == GameConstants.ScreenProperties.height)) {
						Json.Object objBgImage = resolution.getObject("bg_image");
						String bgImagePath = objBgImage.getString("path");
						bgImage = assetManager().getImage(bgImagePath);
						bgImage.addCallback(new ResourceCallback<Image>() {

							@Override
							public void error(Throwable err) {
								log().error("LoadingView::initImageLayouts() : Error loading" + bgImage, err);
							}

							@Override
							public void done(Image resource) {
								ImageLayer bgLayer = graphics().createImageLayer(bgImage);    
								gLayer.add(bgLayer);
							}
						});
						Json.Object objLoadingImage = resolution.getObject("loading_bar");
						String loadingImagePath = objLoadingImage.getString("path");
	    	    		int x = objLoadingImage.getInt("x");
	    	    		int y = objLoadingImage.getInt("y");
						loadingBar = new LoadingBar(x, y, loadingImagePath);
						gLayer.add(loadingBar.getLayer());
					}
				}
			}

			@Override
			public void error(Throwable err) {
				log().error("LoadingView::initImageLayouts() : Error loading" + jsonPath, err);
			}
		});
	}

	@Override
	public void init(Object totalElements) {
		CachedResource.getInstance().loadResources(this);
	    gLayer.setVisible(true);
	}
	@Override
	public void shutdown() {
		if (gLayer != null) {
			gLayer.setVisible(false);
		}
	}
	@Override
	public void onPercentUpdate(float percent) {
		percentComplete = percent;
	}
	@Override
	public void onLoadComplete() {
		loadingComplete =true;
		SceneNavigator.getInstance().createScenes();
	}
	@Override
	public void update(float delta) {
		if(loadingComplete == true)
			SceneNavigator.getInstance().runScene(eScenes.MENU, null);
	}
	@Override
	public void paint(float alpha) {
		loadingBar.onPercentUpdate(percentComplete);
	}
}
