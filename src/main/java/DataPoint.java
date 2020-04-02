import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataPoint {
  private double a = 0;
  private double b = 0;
  private double c = 0;
  private double m = 0;
  private double intercept = 0;
  private int iteration = 10;
  private static Random random = new Random();

  static List<Point2D> coordinates;

  public DataPoint() {
    coordinates = new ArrayList<Point2D>();
  }

  public void addData(double x, double y) {
    coordinates.add(new Point2D(x, y));
  }

  public List<Point2D> dataList() {
    return coordinates;
  }

  public Line2D fitLine() {

//Compute a,b,c
    return new Line2D(-(a / b), -(c / b));
  }

  //returns a list with random coordinates of size k
  private List<Point2D> getRandomCenters(List<Point2D> points, int k) {
    List<Point2D> coordinatesFix = new ArrayList<Point2D>();
    for (int i = 0; i < k; i++) {
      int randomIndex = random.nextInt(points.size());
      coordinatesFix.add(new Point2D(points.get(randomIndex).x, points.get(randomIndex).y));
    }
    return coordinatesFix;
  }

  public List<Integer> kmeans(int k) {
    int l = 0;
    double[] distanceFromCenter = new double[k];
    double initialErr = Double.POSITIVE_INFINITY;
    if (k < 0) {
      throw new IllegalArgumentException("k value cannot be negative");
    }
    List<Point2D> randomCenters = new ArrayList<Point2D>();
    while ((iteration--) > 0) {
      randomCenters = getRandomCenters(this.dataList(), k);
      for (int i = 0; i < this.dataList().size(); i++) {
        for (int j = 0; j < k; j++) {
          distanceFromCenter[l++] = Math.sqrt(Math.pow((this.dataList().get(i).x - randomCenters.get(j).x), 2)
              + Math.pow((this.dataList().get(i).x - randomCenters.get(j).x), 2));
        }
        //store (this.dataList().get(i).x,this.dataList().get(i).y) in jth index[add to this cluster]
        //that corresponds to the minimum value in distanceFromCenter[] for every i.
        //(what datastructure to use that stores all datapoints in k partitions here!!
      }
    }
    return null;
  }


}
