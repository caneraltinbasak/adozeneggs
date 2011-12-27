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
	static private final String IMAGE_TYPE = "image";
	static private final String SOUND_TYPE = "sound";
	static private final String TEXT_TYPE = "text";
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

				private int elementsToLoad;
				private int loadedElements;

				@Override
				public void done(String json) {
					Json.Object document = json().parse(json);
					Json.Array resourceArray = document.getArray("Resource");
					elementsToLoad = 0;
					// Count elements to be loaded
					for (int i = 0; i < resourceArray.length(); i++) {
						Json.Object resource = resourceArray.getObject(i);
						String resourceType= resource.getString("type");
						if (resourceType.equals(IMAGE_TYPE)) {
							if (resource.getString("quality").equals(GameConstants.ScreenProperties.gQuality)) {
								elementsToLoad++;
							}
						}else if (resourceType.equals(SOUND_TYPE)) {
							elementsToLoad++;
						}

						
					}
					// Load elements
					for (int i = 0; i < resourceArray.length(); i++) {
						Json.Object resource = resourceArray.getObject(i);
						String resourceType= resource.getString("type");
						String resourceUrl = resource.getString("url");
						if (resourceType.equals(IMAGE_TYPE)) {
							if (resource.getString("quality").equals(GameConstants.ScreenProperties.gQuality)) {
								Image aImage = assetManager().getImage(resourceUrl);
								aImage.addCallback(new ResourceCallback<Image>() {
									@Override
									public void done(Image resource) {
										loadedElements++;
										eventlistener.onPercentUpdate((float)loadedElements / (float)elementsToLoad);
										if(loadedElements == elementsToLoad)
										{
											hasLoaded = true;
											eventlistener.onLoadComplete();
											eventlistener=null; // clear listener
										}
									}

									@Override
									public void error(Throwable err) {

									}
								});
								cachedResources.put(resourceUrl,aImage);
							}
						} else if (resourceType.equals(SOUND_TYPE)) {
							Sound aSound = assetManager().getSound(resourceUrl);
							cachedResources.put(resourceUrl, aSound);
							loadedElements++;
							eventlistener.onPercentUpdate((float)loadedElements / (float)elementsToLoad);
							if(loadedElements == elementsToLoad)
							{
								hasLoaded = true;
								eventlistener.onLoadComplete();
								eventlistener=null; // clear listener
							}
						} else if (resourceType.equals(TEXT_TYPE)) {
							log().error("Text is not supported yet");
						}else{
							log().error("Unknown resource type: " + resourceType);
						}
					}
				}

				@Override
				public void error(Throwable err) {
					log().error("Failed to load"+ JSON_PATH,err);
				}
			});
			
		}
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
