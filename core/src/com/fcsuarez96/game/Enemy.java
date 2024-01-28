package com.fcsuarez96.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashMap;


public class Enemy extends Entity {
  String[] ghostColors = {"RED", "ORANGE", "BLUE", "PINK"};

  private HashMap<Double, Direction> directionChoice = new HashMap<>();

  public Enemy(Game game,int x,int y,int colorIndex) {
    super(game, "barnacle.png");
    this.x=x;
    this.y=y;
    speed=0.4f;
    switch (colorIndex) {
      case 1:
        sprite.setColor(Color.ORANGE);
        break;
      case 2:
        sprite.setColor(Color.BLUE);
        break;
      case 3:
        sprite.setColor(Color.PINK);
        break;
      default:
        sprite.setColor(Color.RED);
        break;
    }
  }

  public void update(float delta) {
    tickSpeed += delta;
    if (tickSpeed >= speed) {
      tickSpeed = 0;
      updateMovement();
    }
  }

  private void updateMovement() {
    if (isIntersection()) {
      directionChoice.clear();
      if (!game.map.isWall(this, Direction.UP) && direction != Direction.DOWN) {
        directionChoice.put(Math.random(), Direction.UP);
      }
      if (!game.map.isWall(this, Direction.DOWN) && direction != Direction.UP) {
        directionChoice.put(Math.random(), Direction.DOWN);
      }
      if (!game.map.isWall(this, Direction.LEFT) && direction != Direction.RIGHT) {
        directionChoice.put(Math.random(), Direction.LEFT);
      }
      if (!game.map.isWall(this, Direction.RIGHT) && direction != Direction.LEFT) {
        directionChoice.put(Math.random(), Direction.RIGHT);
      }
      if (!directionChoice.isEmpty()) {
        Double key = directionChoice.keySet().stream().sorted().findFirst().get();
        direction = directionChoice.get(key);
      } else {
        direction=Direction.RIGHT;
      }

    } else if (noWay()) {
      switch (direction) {
        case UP:
          direction = Direction.DOWN;
          break;
        case DOWN:
          direction = Direction.UP;
          break;

        case LEFT:
          direction = Direction.RIGHT;
          break;

        case RIGHT:
          direction = Direction.LEFT;
          break;
      }
    }
    switch (direction) {
      case UP:
        y++;
        break;
      case DOWN:
        y--;
        break;

      case LEFT:
        x--;
        break;

      case RIGHT:
        x++;
        break;
    }

    teleport();


  }

  private boolean isIntersection() {
    switch (direction) {
      case UP:
      case DOWN:
        if (!game.map.isWall(this, Direction.LEFT) || !game.map.isWall(this, Direction.RIGHT)) {
          return true;
        }

      case LEFT:
      case RIGHT:
        if (!game.map.isWall(this, Direction.UP) || !game.map.isWall(this, Direction.DOWN)) {
          return true;
        }
    }
    return false;
  }

  private boolean noWay() {
    switch (direction) {
      case UP:
        return game.map.isWall(this, Direction.LEFT)
            && game.map.isWall(this, Direction.RIGHT)
            && game.map.isWall(this, Direction.UP);
      case DOWN:
        return game.map.isWall(this, Direction.LEFT)
            && game.map.isWall(this, Direction.RIGHT)
            && game.map.isWall(this, Direction.DOWN);
      case LEFT:
        return game.map.isWall(this, Direction.LEFT)
            && game.map.isWall(this, Direction.UP)
            && game.map.isWall(this, Direction.DOWN);
      case RIGHT:
        return game.map.isWall(this, Direction.RIGHT)
            && game.map.isWall(this, Direction.UP)
            && game.map.isWall(this, Direction.DOWN);
    }
    return false;
  }
}




