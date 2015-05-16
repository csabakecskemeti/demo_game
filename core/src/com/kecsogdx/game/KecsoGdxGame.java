package com.kecsogdx.game;

import com.badlogic.gdx.Game;

/**
 * Created by kecso on 5/16/15.
 */
public class KecsoGdxGame extends Game {


    KecsoGdxMain mainMenuScreen;
    KecsoGdxGameScreen play;

    @Override
    public void create() {
        mainMenuScreen = new KecsoGdxMain(this);
        play = new KecsoGdxGameScreen(this);
        setScreen(mainMenuScreen);
    }
}
