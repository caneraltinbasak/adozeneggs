package com.scgame.adozeneggs.core;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import java.util.HashMap;
import java.util.Map;
import playn.core.Image;
import playn.core.Json;
import playn.core.ResourceCallback;
import playn.core.Sound;

public class CachedResource {
	private Map<String, Object> cachedResources = new HashMap<String, Object>();;
	private Boolean hasLoaded = false;
	static private final String JSON_PATH = "ResourceList.json";
	private int loadedElements = 0;
	private int totalElements = 0;
	private LoaderInterface eventlistener = null;
	private static CachedResource instance = null;

	public static CachedResource getInstance() {
		if (instance == null) {
			instance = new CachedResource();
		}
		return instance;
	}

	protected CachedResource() {
		// Exists only to defeat instantiation.
	}

	public void loadResources(LoaderInterface listener) {
		eventlistener = listener; // set up listener
		if(!hasLoaded){
			assetManager().getText(JSON_PATH, new ResourceCallback<String>() {

				@Override
				public void done(String json) {
					Json.Object document = json().parse(json);
					Json.Array resourceArray = document.getArray("Resource");
					totalElements = resourceArray.length();
					for (int i = 0; i < totalElements; i++) {
						Json.Object resource = resourceArray.getObject(i);
						if (resource.getString("type") == "image") {
							if (resource.getString("quality") == GameConstants.ScreenProperties.gQuality) {
								Image aImage = assetManager().getImage(
										resource.getString("URL"));
								aImage.addCallback(new ResourceCallback<Image>() {
									@Override
									public void done(Image resource) {
										loadedElements++;
										eventlistener.onPercentUpdate(totalElements
												/ loadedElements);
									}

									@Override
									public void error(Throwable err) {

									}
								});
								cachedResources.put(resource.getString("URL"),
										aImage);
							}
						} else if (resource.getString("type") == "sound") {
							Sound aSound = assetManager().getSound(
									resource.getString("URL"));
							cachedResources.put(resource.getString("URL"), aSound);
							loadedElements++;
							eventlistener.onPercentUpdate(totalElements
									/ loadedElements);

						} else if (resource.getString("type") == "text") {
							log().error("Text is not supported yet");
						}
						if(i == loadedElements)
							eventlistener.onLoadComplete();

					}

				}

				@Override
				public void error(Throwable err) {
					log().error("Failed to load"+ JSON_PATH,err);
				}

			});
			hasLoaded = true;
		}else{
			eventlistener.onLoadComplete();
		}
		eventlistener=null; // clear listener
	}

	public void purgeResources() {
		cachedResources.clear();
		hasLoaded = false;
	}

	public Object getResource(String id) {
		if (hasLoaded){
			Object resource = cachedResources.get(id);
			if(resource!=null){
				return resource;
			}else{
				log().error("Cached resource load error:"+id);
			}
		}
		else{
			log().error("CashedResource::getResource is called before loading all resources");
		}
		return null;
	}
}
