package com.kecsogdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kecsogdx.game.KecsoGdxGamePlay;
import com.kecsogdx.game.KecsoGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new KecsoGdxGamePlay(), config);
//        initialize(new KecsoGdxGame(), config);
	}
}
