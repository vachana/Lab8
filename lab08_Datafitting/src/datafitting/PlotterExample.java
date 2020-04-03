package datafitting;

import java.io.IOException;

/**
 * An example of how to use the ImagePlotter for this data fitting assignment.
 * 
 * @author Amit Shesh
 * @version 2017-02-16
 */
public class PlotterExample {
  public static void main(String[] args) {
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);

    plotter.setDimensions(-300, 300, -350, 350);
    for (int x = -200; x < 200; x += 20) {
      for (int y = 0; y <= x; y += 20) {
        plotter.addCircle(x, y, 10);
        plotter.addPoint(x, y);
        plotter.addLine(x, y, x + 20, y);
        plotter.addLine(x, y, x, y + 20);
      }
    }

    try {
      plotter.write("example.png");
    } catch (IOException e) {

    }
  }
}
