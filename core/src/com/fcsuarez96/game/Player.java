package com.fcsuarez96.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {

    MyGame myGame;
    private OrthographicCamera camera;

    public Player(Game game, MyGame myGame, int x, int y) {
        super(game, "slimeBlock.png");
        this.x = x;
        this.y = y;
        this.myGame = myGame;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    public void update(float delta) {
        playerInput();
        die();
        tickSpeed += delta;
        if (tickSpeed >= speed) {
            tickSpeed = 0;
            playerMovement();
        }
    }

    private void playerInput() {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            direction = Direction.UP;
        }

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            direction = Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            direction = Direction.DOWN;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            direction = Direction.RIGHT;
        }
    }


    private void playerMovement() {
        if (!isWall()) {
            switch (direction) {
                case UP:
                    y++;
                    break;

                case DOWN:
                    y--;
                    break;

                case RIGHT:
                    x++;
                    break;

                case LEFT:
                    x--;
                    break;
            }
        }
        pickTreasure();

        teleport();

    }

    private boolean isWall() {
        int tile = 0;
        switch (direction) {
            case UP:
                tile = game.map.getTile(x, y + 1);
                break;

            case DOWN:
                tile = game.map.getTile(x, y - 1);
                break;

            case RIGHT:
                tile = game.map.getTile(x + 1, y);
                break;

            case LEFT:
                tile = game.map.getTile(x - 1, y);
                break;
        }
        return (tile == 0) ;
    }

    private void pickTreasure() {
        if (game.map.getTile(x, y) == 5) {
            game.map.setMap(game.map.getHeight() - y - 1, x, 6);
            game.keyCollectedSound.play();
            game.keys--;
        }
    }

    private void die() {
        for (int i = 0; i < game.entities.size(); i++) {
            if (this.x == game.entities.get(i).x && this.y == game.entities.get(i).y) {
                game.font.getData().setScale(3.0f);
                game.spriteBatch.begin();
                game.font.draw(game.spriteBatch, "GAME LOST!", 280, 650);
                game.gameOverSound.play();
                game.spriteBatch.end();
                game.font.getData().setScale(1.0f);
                myGame.setScreen(new MainMenuScreen(myGame,camera));
            }
        }
    }

    public Boolean winSituation() {
        if (game.map.getTile(x, y) == 2) {
            return true;
        }
        return false;
    }


    public void win() {
        game.entities.clear();
    }

}

