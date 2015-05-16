package com.kecsogdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class KecsoGdxGamePlay implements ApplicationListener {
//public class KecsoGdxGamePlay implements Screen {
	private Texture dropImage;
	private Texture bucketImage;
    private Texture creativeImage;
	private Sound dropSound;
	private Music rainMusic;
    private Sound creativeSound;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle bucket;
    private Vector3 touchPos;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private Array<Rectangle> creatives;
    private int points = 0;
    private BitmapFont font;
    private int creativeSpeed = 200;
    private int dropSpeed = 200;

	@Override
	public void create() {
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        creativeImage = new Texture(Gdx.files.internal("amazon_button.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        creativeSound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = (480 / 2) - (bucketImage.getWidth() / 2);
        bucket.y = 20;
        bucket.width = bucketImage.getWidth();
        bucket.height = bucketImage.getHeight();
        touchPos = new Vector3();

        raindrops = new Array<Rectangle>();
        spawnRainDrop();
        creatives = new Array<Rectangle>();
        spawnCreativeDrop();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        for(Rectangle creative: creatives) {
            batch.draw(creativeImage, creative.x, creative.y);
        }
        font.draw(batch, "SCORE: " + points, 20, 790);
        font.draw(batch, "<Sponsored Native Game Ads DEMO>", 150, 790);
        batch.end();

        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x < bucketImage.getWidth() / 2) {
                bucket.x = touchPos.x;
            } else if ((touchPos.x + bucketImage.getWidth() / 2) > 480) {
                bucket.x = touchPos.x  - bucketImage.getWidth();
            } else {
                bucket.x = touchPos.x - bucketImage.getWidth() / 2;
            }
        }

        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            if (MathUtils.random(0, 10) > 2) {
                spawnRainDrop();
            } else {
                spawnCreativeDrop();
            }
        }
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
            if(raindrop.y + dropImage.getHeight() < 0) iter.remove();
            if(raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
                points ++;
                dropSpeed += 1;
            }
        }

        Iterator<Rectangle> iter2 = creatives.iterator();
        while (iter2.hasNext()) {
            Rectangle creative = iter2.next();
            creative.y -= creativeSpeed * Gdx.graphics.getDeltaTime();
            if (creative.y + creativeImage.getHeight() < 0) iter2.remove();
            if (creative.overlaps(bucket)) {
                creativeSound.play();
                iter2.remove();
                points += 10;
                creativeSpeed += 10;
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        creativeImage.dispose();
        dropSound.dispose();
        creativeSound.dispose();
        rainMusic.dispose();
        batch.dispose();
        font.dispose();
        points = 0;
    }

    private void spawnRainDrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 480 - dropImage.getWidth());
        raindrop.y = 700;
        raindrop.width = dropImage.getWidth();
        raindrop.height = dropImage.getHeight();
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnCreativeDrop() {
        Rectangle creative = new Rectangle();
        creative.x = MathUtils.random(0, 480 - creativeImage.getWidth());
        creative.y = 600;
        creative.width = creativeImage.getWidth();
        creative.height = creativeImage.getHeight();
        creatives.add(creative);
        lastDropTime = TimeUtils.nanoTime();
    }
}
