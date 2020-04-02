import java.util.ArrayList;
import java.util.List;

public class DataPoint {
  private double a = 0;
  private double b = 0;
  private double c = 0;
  private double m = 0;
  private double intercept = 0;

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

  private Line2D fitLine() {


    return new Line2D(-(a / b), -(c / b));
  }

}
