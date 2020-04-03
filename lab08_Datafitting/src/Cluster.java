import java.util.*;

/**
 * This class represents a Cluster, it contains the information of the
 * centroid and the points it has.
 */
public class Cluster{
  
  private ArrayList<Point2D> points;
  private Point2D centroid;

  public Cluster(double x, double y){
    centroid = new Point2D(x, y);
    points = new ArrayList<>();
  }

  /**
   * Get the quantity of points in the cluster
   */
  public int getClusterSize(){
    return points.size();
  }

  /**
   * Get the centroid of the cluster
   */
  public Point2D getCentroid(){
    return centroid;
  }

  /**
   * Calculate the total error of the cluster that is
   * given by the sum of distances of each point to 
   * the centroid
   */
  public double getError(){
    double err=0;
    for(int i =0; i<points.size(); i++){
      err += calculateDistance(points.get(i));
    }
    return err;
  }

  /**
   * Calculate the distance of a point to the centroid
   * of the cluster
   *
   * @param p
   */
  public double calculateDistance(Point2D p){
    return Math.hypot(p.getX() - centroid.getX(), p.getY() - centroid.getY());
  }

  /**
   * Add a point to the cluster
   * 
   * @param p
   */
  public void addPoint(Point2D p){
    points.add(p);
  }

  /**
   * Recalculate the centroid position according to the
   * points in the cluster
   */
  public void recalculateCentroid(){
    double x=0;
    double y=0;

    for(int i = 0; i< points.size(); i++){
      x+=points.get(i).getX();
      y+=points.get(i).getY();
    }

    x/=points.size();
    y/=points.size();
    centroid = new Point2D(x,y);
  }

  /**
   * Delete points in the cluster
   */
  public void cleanPoints(){
    points = new ArrayList<>();
  }

  /**
   * Get the points in the cluster
   */
  public ArrayList<Point2D> getPoints(){
    return points;
  }
}