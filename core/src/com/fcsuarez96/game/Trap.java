package com.fcsuarez96.game;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;


public class Trap extends Entity {
  String[] ghostColors = {"RED", "ORANGE", "BLUE", "PINK"};

  private HashMap<Double, Direction> directionChoice = new HashMap<>();

  public Trap(Game game, int x, int y, int colorIndex) {
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

}




