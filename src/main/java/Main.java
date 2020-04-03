import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import datafitting.ImagePlotter;

public class Main {
  public static void main(String[] args) throws Exception {
    Color colors[] = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLACK, Color.CYAN, Color.ORANGE};

    /* For lines change the data file input for each one */
    System.out.println("Enter linear data");
    Scanner scan = new Scanner(System.in);
    File file = new File(scan.next());
    Scanner sc = new Scanner(new FileInputStream(file));
    LinearRegression model = new LinearRegression();
    double minX = Double.POSITIVE_INFINITY;
    double maxX = -minX;

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      String st[] = s.split(" ");
      double x = Double.parseDouble(st[0]);
      double y = Double.parseDouble(st[1]);
      model.addPoint(new Point2D(x, y));
    }

    Line2D l = model.fitLine();
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(400);
    plotter.setHeight(400);
    plotter.setDimensions(-350, 350, -350, 350);
    ArrayList<Point2D> points = model.getPoints();

    for (int i = 0; i < points.size(); i++) {
      minX = Math.min(minX, points.get(i).getX());
      maxX = Math.max(minX, points.get(i).getX());
      plotter.addPoint((int) points.get(i).getX(), (int) points.get(i).getY());
    }

    plotter.addLine((int) minX, (int) l.calculateY(minX), (int) maxX, (int) l.calculateY(maxX), Color.RED);
    plotter.write("src/main/res/line-1.png");

    /* For clusters change the data file input for each one */
    System.out.println("Enter file for kmeans");
    Scanner scan1 = new Scanner(System.in);
    File filenew = new File(scan1.next());
    sc = new Scanner(new FileInputStream(filenew));
    model = new LinearRegression();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      String st[] = s.split(" ");
      double x = Double.parseDouble(st[0]);
      double y = Double.parseDouble(st[1]);

      model.addPoint(new Point2D(x, y));
    }

    Cluster clusters[] = model.kmeans(2);

    plotter = new ImagePlotter();
    plotter.setWidth(1000);
    plotter.setHeight(1000);

    plotter.setDimensions(-500, 500, -500, 500);

    for (int i = 0; i < clusters.length; i++) {
      points = clusters[i].getPoints();

      for (int j = 0; j < points.size(); j++) {
        plotter.addPoint((int) points.get(j).getX(), (int) points.get(j).getY(), colors[i]);
      }
    }
    plotter.write("src/main/res/cluster-3.png");
  }
}