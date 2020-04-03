import java.util.*;

public class Cluster{

  private ArrayList<Point2D> points;
  private Point2D centroid;
  private double error;

  public Cluster(double x, double y){
    centroid = new Point2D(x, y);
    points = new ArrayList();
    error = 0;
  }

  public int getClusterSize(){
    return points.size();
  }
  public Point2D getCentroid(){
    return centroid;
  }

  public double getError(){
    double err=0;
    for(int i =0; i<points.size(); i++){
      err += calculateDistance(points.get(i));
    }
    return err;
  }

  public double calculateDistance(Point2D p){
    return Math.hypot(p.getX() - centroid.getX(), p.getY() - centroid.getY());
  }

  public void addPoint(Point2D p){
    points.add(p);
  }

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

  public void cleanPoints(){
    points = new ArrayList();
  }

  public ArrayList<Point2D> getPoints(){
    return points;
  }

}