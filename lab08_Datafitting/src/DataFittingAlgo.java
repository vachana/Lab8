import java.util.*;

/**
 * Class to fit points to a Line and Clustering by Kmeans method
 */
public class DataFittingAlgo {

  private ArrayList<Point2D> points;

  Random random = new Random();

  public DataFittingAlgo() {
    points = new ArrayList<>();
  }


  /**
   * Get the points inserted
   */
  public ArrayList<Point2D> getPoints() {
    return points;
  }

  /**
   * Add a new point
   *
   * @param p
   */
  public void addPoint(Point2D p) {
    points.add(p);
  }

  /**
   * Fit points to a Line and return Line equation
   */
  public Line2D fitLine() {
    double meanX = 0;
    double meanY = 0;

    int n = points.size();

    if (n < 2) {
      return null;
    }

    for (int i = 0; i < n; i++) {
      meanX += points.get(i).getX();
      meanY += points.get(i).getY();
    }

    meanX /= n;
    meanY /= n;

    double syy = 0, sxx = 0, sxy = 0;

    for (int i = 0; i < n; i++) {
      syy += Math.pow(points.get(i).getY() - meanY, 2);
      sxx += Math.pow(points.get(i).getX() - meanX, 2);
      sxy += (points.get(i).getY() - meanY) * (points.get(i).getX() - meanX);
    }

    double d = 2 * sxy / (sxx - syy);

    double o = Math.atan(d);
    double t = o;

    if ((syy - sxx) * Math.cos(t) - 2 * sxy * Math.sin(t) <= 0) {
      t = o + Math.PI;
    }

    double a = Math.cos(t / 2), b = Math.sin(t / 2), c = -a * meanX - b * meanY;

    return new Line2D(a, b, c);
  }

  /*
   *
   *
   */
  private ArrayList<Point2D> getRandomCenters(ArrayList<Point2D> points, int k) {
    ArrayList<Point2D> coordinatesFix = new ArrayList<Point2D>();
    for (int i = 0; i < k; i++) {
      int randomIndex = random.nextInt(points.size());
      coordinatesFix.add(new Point2D(points.get(randomIndex).getX(), points.get(randomIndex).getY()));
    }
    return coordinatesFix;
  }

  /**
   * Calculate Clusters from points using Kmeans algorithm
   * with a given number of clusters K
   *
   * @param k
   */
  public Cluster[] kmeans(int k) {
    double minError = Double.POSITIVE_INFINITY;
    int n = points.size();
    Cluster ans[] = new Cluster[n];


    for (int ransacIt = 0; ransacIt < 10; ransacIt++) {
      Cluster clusters[] = new Cluster[k];

      for (int i = 0; i < k; i++) {
        // Point2D randomPoint = points.get((int) (Math.random() * n));
        //clusters[i] = new Cluster(randomPoint.getX(), randomPoint.getY());
        ArrayList<Point2D> random = getRandomCenters(points, k);
        double x = random.get(i).getX();
        double y = random.get(i).getY();
        clusters[i] = new Cluster(x, y);
      }

      double e = Double.POSITIVE_INFINITY;

      for (int iterations = 0; iterations < 100; iterations++) {
        for (int j = 0; j < k; j++) {
          clusters[j].cleanPoints();
        }
        for (int i = 0; i < n; i++) {
          double minDist = Double.POSITIVE_INFINITY;
          int cluster = -1;
          for (int j = 0; j < k; j++) {
            double dist = clusters[j].calculateDistance(points.get(i));
            if (dist < minDist) {
              minDist = dist;
              cluster = j;
            }
          }
          clusters[cluster].addPoint(points.get(i));
        }

        double ne = 0;
        for (int j = 0; j < k; j++) {
          clusters[j].recalculateCentroid();
          ne += clusters[j].getError();
        }
        if (Math.abs(ne - e) / e < 0.00001) {
          break;
        }
        e = ne;
      }

      if (e < minError) {
        minError = e;
        ans = clusters;
      }
    }

    return ans;
  }

}