package com.fcsuarez96.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen implements Screen {

    final MyGame game;
    private Stage stage;
    private OrthographicCamera camera;

    String filePath= "/Users/divyansh/Downloads/game build/maze-runner/core/src/com/fcsuarez96/game/map.txt";
    public MainMenuScreen(MyGame game, OrthographicCamera camera) {
        this.camera = camera != null ? camera : new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.game = game;
        stage = new Stage(); // Create a stage to hold UI elements
        Gdx.input.setInputProcessor(stage); // Set the stage as the input processor

        // Create a label
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        labelStyle.font.getData().setScale(2.0f);
        Label titleLabel = new Label("Welcome to Maze Runner Game!!!", labelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() * 0.7f, Align.center);
        stage.addActor(titleLabel);

        // Create a button
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = game.font;
        TextButton startButton = new TextButton("Tap to begin!", buttonStyle);
        startButton.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() * 0.5f, Align.center);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new Game(filePath,game));
            }
        });

        TextButton loadMapButton = new TextButton("Load new Map File", buttonStyle);
        loadMapButton.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() * 0.4f, Align.center);
        loadMapButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                filePath = "/Users/divyansh/Downloads/game build/maze-runner/core/src/com/fcsuarez96/game/map.txt";
                game.setScreen(new Game(filePath,game));
            }
        });

        stage.addActor(loadMapButton);
        stage.addActor(startButton);
    }


    @Override
    public void show() {
        // Set up the menu when it's shown (initialize elements, set input processors, etc.)
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        // Ensure the camera follows the player or desired position
        updateCameraPosition();

        // Set the batch to use the camera's combined matrix
        game.batch.setProjectionMatrix(camera.combined);

    }

    private void updateCameraPosition() {
        // Adjust the camera position to keep the player in the middle 80% of the screen
        // Replace these values with your player's position or desired logic
        float playerX = 100;
        float playerY = 100;

        float cameraX = Math.max(Gdx.graphics.getWidth() * 0.1f, Math.min(playerX - Gdx.graphics.getWidth() * 0.4f, Gdx.graphics.getWidth() * 0.5f));
        float cameraY = Math.max(Gdx.graphics.getHeight() * 0.1f, Math.min(playerY - Gdx.graphics.getHeight() * 0.4f, Gdx.graphics.getHeight() * 0.5f));

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        // Update the menu layout if the screen size changes
        stage.getViewport().update(width, height, true);
    }

    // Other overridden methods...
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
