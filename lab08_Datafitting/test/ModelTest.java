import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


public class ModelTest {

  private DataFittingAlgo model;
  private double EPS = 0.001;

  /**
   * Test that Line generated by two points gives same Y position
   */
  @Test
  public void testFitLine() {
    double a = Math.random(), b = Math.random(), c = Math.random();
    System.out.println(a);
    System.out.println(b);
    System.out.println(c);

    double x1 = Math.random();
    double y1 = (-a * x1 - c) / b;
    double x2 = Math.random();
    double y2 = (-a * x2 - c) / b;

    model = new DataFittingAlgo();
    model.addPoint(new Point2D(x1, y1));
    model.addPoint(new Point2D(x2, y2));

    Line2D line = model.fitLine();

    double ny1 = line.calculateY(x1);
    double ny2 = line.calculateY(x2);

    Assert.assertEquals(y1, ny1, EPS);
    Assert.assertEquals(y2, ny2, EPS);
  }

  /**
   * Test that point assigned to a cluster is closest to it's centroid
   * than another cluster's centroid
   */
  @Test
  public void testKmeans() {
    int n = 10;
    int k = 3;

    model = new DataFittingAlgo();
    for (int i = 0; i < n; i++) {
      model.addPoint(new Point2D(Math.random(), Math.random()));
    }

    Cluster[] clusters = model.kmeans(k);

    for (int i = 0; i < k; i++) {
      Cluster act = clusters[i];
      ArrayList<Point2D> points = act.getPoints();
      for (int j = 0; j < points.size(); j++) {
        Point2D actualPoint = points.get(j);
        double distanceToCluster = act.calculateDistance(actualPoint);

        for (int l = 0; l < k; l++) {
          if (l != i) {
            double dist = clusters[l].calculateDistance(actualPoint);
            Assert.assertTrue(dist >= distanceToCluster);
          }
        }
      }
    }
  }

}
