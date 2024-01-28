package com.fcsuarez96.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Map {

  public static final int TILE_SIZE = 30;
  public static final int OFFSET_X = 85;
  public static final int OFFSET_Y = 85;

  Texture mapWall;
  Texture key;
  Texture exit;
  Sprite mapSprite;
  Sprite keySprite;
  Sprite exitSprite;
  public int[][] map;

  public Map(String filePath) {
    loadMapFromFile(filePath);
    mapWall = new Texture("stone.png");
    key = new Texture("key.png");
    exit = new Texture("gate.png");
    mapSprite = new Sprite(mapWall);
    keySprite = new Sprite(key);
    exitSprite = new Sprite(exit);
  }

  private void loadMapFromFile(String filePath) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(filePath));
      String line;
      int rows = 0;
      int columns = 0;
      while ((line = br.readLine()) != null) {
        columns = line.trim().split("\\s+").length;
        rows++;
      }

      map = new int[rows][columns];

      br.close();

      br = new BufferedReader(new FileReader(filePath));
      int row = 0;
      while ((line = br.readLine()) != null) {
        String[] tokens = line.trim().split("\\s+");
        for (int col = 0; col < tokens.length && col < map[row].length; col++) {
          map[row][col] = Integer.parseInt(tokens[col]);
        }
        row++;
      }
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }


  public void draw(SpriteBatch spriteBatch) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        int tile = getTile(i, j);
        if (tile == 0) {
          mapSprite
              .setBounds(i * TILE_SIZE + OFFSET_X, j * TILE_SIZE + OFFSET_Y, TILE_SIZE,
                  TILE_SIZE);
          mapSprite.draw(spriteBatch);

        }

        if (tile == 5) {
          keySprite
              .setBounds(i * TILE_SIZE + OFFSET_X, j * TILE_SIZE + OFFSET_Y, TILE_SIZE,
                  TILE_SIZE);
          keySprite.draw(spriteBatch);
        }
        if (tile == 2) {
          exitSprite
                  .setBounds(i * TILE_SIZE + OFFSET_X, j * TILE_SIZE + OFFSET_Y, TILE_SIZE,
                          TILE_SIZE);
          exitSprite.draw(spriteBatch);
        }
      }
    }
  }

  public int getTile(int x, int y) {
    if (x < 0 || x > getWidth() - 1 || y < 0 || y > getHeight() - 1) {
      return 6;
    }
    return map[map.length - y - 1][x];
  }

  public boolean isWall(Entity entity, Entity.Direction direction) {
    switch (direction) {
      case UP:
        return getTile(entity.x, entity.y + 1) == 0;
      case DOWN:
        return getTile(entity.x, entity.y - 1) == 0;
      case LEFT:
        return getTile(entity.x - 1, entity.y) == 0;
      case RIGHT:
        return getTile(entity.x + 1, entity.y) == 0;
    }
    return false;
  }


  public int getWidth() {
    return map[0].length;
  }

  public int getHeight() {
    return map.length;
  }

  public void setMap(int x, int y , int n) {
    this.map[x][y] = n;
  }


}
