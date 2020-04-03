import java.util.ArrayList;

public class LinearRegression{
  private ArrayList<Point2D> points;

  public LinearRegression(){
    points = new ArrayList();
  }

  public ArrayList<Point2D> getPoints(){
    return points;
  }

  public void addPoint(Point2D p){
    points.add(p);
  }

  public Line2D fitLine(){
    double meanX = 0;
    double meanY = 0;

    int n = points.size();
    if(n < 2){
      return null;
    }
    for(int i = 0; i < n; i++){
      meanX += points.get(i).getX();
      meanY += points.get(i).getY();
    }

    meanX /= n;
    meanY /= n;
    double syy = 0, sxx = 0, sxy = 0;
    for(int i = 0; i < n; i++){
      syy += Math.pow(points.get(i).getY() - meanY, 2);
      sxx += Math.pow(points.get(i).getX() - meanX, 2);
      sxy += (points.get(i).getY() - meanY) * (points.get(i).getX() - meanX);
    }
    double d = 2 * sxy / (sxx - syy);
    double o = Math.atan(d);
    double t = o;
    if((syy - sxx) * Math.cos(t) - 2*sxy * Math.sin(t) <= 0){
      t=o+Math.PI;
    }
    double a = Math.cos(t/2), b=Math.sin(t/2), c=-a*meanX-b*meanY;
    return new Line2D(a, b, c);
  }

  private List<Point2D> getRandomCenters(List<Point2D> points, int k) {
    List<Point2D> coordinatesFix = new ArrayList<Point2D>();
    for (int i = 0; i < k; i++) {
      int randomIndex = random.nextInt(points.size());
      coordinatesFix.add(new Point2D(points.get(randomIndex).x, points.get(randomIndex).y));
    }
    return coordinatesFix;
  }

  public Cluster[] kmeans(int k){
    double minError = Double.POSITIVE_INFINITY;
    int n = points.size();
    Cluster ans[] = new Cluster[n];
    double minX = Double.POSITIVE_INFINITY;
    double maxX = -minX;
    double minY = Double.POSITIVE_INFINITY;
    double maxY = -minY;

    for(int i = 0; i<n; i++){
      minX = Math.min(minX,points.get(i).getX());
      maxX = Math.max(minX,points.get(i).getX());
      minY = Math.min(minX,points.get(i).getY());
      maxY = Math.max(minX,points.get(i).getY());
    }

    for(int ransacIt = 0; ransacIt < 10; ransacIt++){
      Cluster clusters[] = new Cluster[k];
      for(int i = 0; i < k; i++){
        List<Point2D> random =  getRandomCenters(points, k);
        double x = random.get(i).x;
        double y = random.get(i).y;
        clusters[i] = new Cluster(x, y);
      }
      double e = Double.POSITIVE_INFINITY;
      for(int iterations = 0; iterations < 100; iterations++){
        for(int j = 0; j < k; j++){
          clusters[j].cleanPoints();
        }
        for(int i = 0; i < n; i++){
          double minDist = Double.POSITIVE_INFINITY;
          int cluster = -1;
          for(int j = 0; j < k; j++){
            double dist = clusters[j].calculateDistance(points.get(i));
            if(dist < minDist){
              minDist=dist;
              cluster=j;
            }
          }
          clusters[cluster].addPoint(points.get(i));
        }

        double ne = 0;
        for(int j = 0; j < k; j++){
          clusters[j].recalculateCentroid();
          ne += clusters[j].getError();
        }
        if(Math.abs(ne-e)/e<0.00001){
          break;
        }
        e=ne;
      }
      if(e < minError){
        minError=e;
        ans = clusters;
      }
    }

    return ans;
  }
}