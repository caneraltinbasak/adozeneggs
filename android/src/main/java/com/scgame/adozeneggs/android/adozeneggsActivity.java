package com.scgame.adozeneggs.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.scgame.adozeneggs.core.adozeneggs;

public class adozeneggsActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("com/scgame/adozeneggs/resources");
    PlayN.run(new adozeneggs());
  }
}
