package com.fcsuarez96.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class Game extends ScreenAdapter {

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 1200;
    SpriteBatch spriteBatch;
    public OrthographicCamera camera;
    BitmapFont font;
    Map map;
    Player player;
    List<Entity> entities = new ArrayList<>();
    int keys;
    String fileText;
    public Stage stage;
    MyGame myGame;
    Sound keyCollectedSound;
    Sound victorySound;
    private boolean hasPlayedVictorySound = false;
    Sound gameOverSound;


    public Game(String fileText, MyGame myGame) {
        this.fileText = fileText;
        this.myGame = myGame;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();
        font.setColor(Color.RED);
        map = new Map(fileText);
        addPlayer();
        addDynamicEnemy();
        addTrap();
        keys = getKeys();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set the stage as the input processor
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("lost.mp3"));
        keyCollectedSound = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));
        victorySound = Gdx.audio.newSound(Gdx.files.internal("victory.mp3"));

        // Create a label for going back to the main menu
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label backToMenuLabel = new Label("Back to Main Menu", labelStyle);
        backToMenuLabel.setPosition(50, 50);
        backToMenuLabel.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                dispose();
                myGame.setScreen(new MainMenuScreen(myGame, camera));
            }
        });

        stage.addActor(backToMenuLabel);
    }

    public void addPlayer() {
        int checker = 0;
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[i].length; j++) {
                int tile = map.getTile(i, j);
                if (tile == 1 && checker == 0) {
                    player = new Player(this, myGame, i, j);
                    checker = 1;
                    break;
                }
            }
        }
    }

    public void addDynamicEnemy() {
        int colorPasser = 0;
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[i].length; j++) {
                int tile = map.getTile(i, j);
                if (tile == 4) {
                    entities.add(new Enemy(this, i, j, (colorPasser) % 4));
                    colorPasser++;
                }
            }
        }
    }

    public void addTrap() {
        int colorPasser = 0;
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[i].length; j++) {
                int tile = map.getTile(i, j);
                if (tile == 3) {
                    entities.add(new Trap(this, i, j, (colorPasser) % 4));
                    colorPasser++;
                }
            }
        }
    }

    public int getKeys() {
        for (int i = 0; i < map.map.length; i++) {
            for (int j = 0; j < map.map[i].length; j++) {
                int tile = map.getTile(i, j);
                if (tile == 5) {
                    keys++;
                }
            }
        }
        return keys;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        delta = Gdx.graphics.getDeltaTime();

        spriteBatch.begin();

        if (keys == 0 && player.winSituation()) {
            player.win();
            font.getData().setScale(2.0f);
            font.draw(spriteBatch, "CONGRATULATIONS YOU WON!!", 180, 450);
            if(!hasPlayedVictorySound)
            victorySound.play();
            font.getData().setScale(1.0f);
            spriteBatch.end();
            hasPlayedVictorySound = true;
        } else {
            font.draw(spriteBatch, "Keys remaining: " + keys, 350, 750);
            map.draw(spriteBatch);
            player.draw(spriteBatch);
            for (Entity entity : entities) {
                entity.draw(spriteBatch);
            }
            hasPlayedVictorySound=false;
            spriteBatch.end();
            player.update(delta);
            for (Entity entity : entities) {
                entity.update(delta);
            }
        }

        // Update and render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Update the menu layout if the screen size changes
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        stage.dispose();
    }
}
