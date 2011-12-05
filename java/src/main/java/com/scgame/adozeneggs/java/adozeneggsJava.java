package com.scgame.adozeneggs.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.scgame.adozeneggs.core.adozeneggs;

public class adozeneggsJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("com/scgame/adozeneggs/resources");
    PlayN.run(new adozeneggs());
  }
}
