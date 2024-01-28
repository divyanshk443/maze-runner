package com.fcsuarez96.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;


    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the screen with the camera
        this.setScreen(new MainMenuScreen(this, camera));
    }

    public void render() {
        super.render();
    }

    public void resize(int width, int height) {
        super.resize(width, height);

        // Update the camera's viewport to match the new screen size
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        // Notify the current screen about the resize event
        getScreen().resize(width, height);
    }
    public void dispose() {
        batch.dispose();
        font.dispose();

    }
}
