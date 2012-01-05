package com.scgame.adozeneggs.html;

import com.scgame.adozeneggs.core.*;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;


public class adozeneggsHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assetManager().setPathPrefix("adozeneggs/");
    PlayN.run(new adozeneggs());
  }
}
