package games;

import java.awt.Color;

public class Test_Figures {
  private static int[][][] figures;
  private static int[][] currentFigure;
  private static Tile shapePosition;
  private static Color figureColor;

  public static class Tile {
    int x;
    int y;

    Tile(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public static void Shapes() {
    figures = new int[][][] { { { 0, 0, 1, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, 1, 0 },
        { 0, 0, 1, 0 } }, // straight shape

        { { 0, 1, 1, 0 },
            { 0, 1, 1, 0 } }, // Square

        { { 0, 0, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 1, 1 } }, // L shape

        { { 0, 0, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 1, 1, 0 } }, // reverse L shape

        { { 0, 0, 1, 0 },
            { 0, 1, 1, 1 },
            { 0, 0, 0, 0 } }, // T shape

        { { 0, 1, 1, 0 },
            { 0, 0, 1, 1 },
            { 0, 0, 0, 0 } }, // Z shape

        { { 0, 0, 1, 1 },
            { 0, 1, 1, 0 },
            { 0, 0, 0, 0 } } // reverse Z shape
    };
  }

  public static void randomFigure() {
    Shapes();
    int randomIndex = (int) (Math.random() * figures.length);
    currentFigure = figures[randomIndex];
    shapePosition = new Tile(3, 0);
    System.out.println("The selected figure is: " + randomIndex);

    switch (randomIndex) {
      case 0:
        figureColor = new Color(0, 255, 255);
        break;
      case 1:
        figureColor = new Color(255, 255, 0);
        break;
      case 2:
        figureColor = new Color(255, 165, 0);
        break;
      case 3:
        figureColor = new Color(0, 0, 255);
        break;
      case 4:
        figureColor = new Color(128, 0, 128);
        break;
      case 5:
        figureColor = new Color(0, 255, 0);
        break;
      case 6:
        figureColor = new Color(255, 0, 0);
        break;
    }
  }

  public static int[][] getCurrentFigure() {
    return currentFigure;
  }

  public static Tile getShapePosition() {
    return shapePosition;
  }

  public static Color getFigureColor() {
    return figureColor;
  }

  public static int[][][] getFigures() {
    return figures;
  }

}
