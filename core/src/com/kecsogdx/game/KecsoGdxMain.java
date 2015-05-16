package com.kecsogdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by kecso on 5/16/15.
 */
public class KecsoGdxMain implements Screen {


    KecsoGdxGame game; // Note it's "MyGame" not "Game"


    // constructor to keep a reference to the main Game class
    public KecsoGdxMain(KecsoGdxGame game){
        this.game = game;
    }


    @Override
    public void render(float delta) {
        // update and draw stuff
        if (Gdx.input.justTouched()) // use your own criterion here
            game.setScreen(game.play);
    }


    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
    }
}
