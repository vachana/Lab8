import java.util.*;
import java.io.*;
import java.awt.*;

import datafitting.ImagePlotter;

public class Main{
  public static void main(String[] args) throws Exception{
    Color colors[]= new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.PINK, Color.CYAN, Color.ORANGE};

    //String inputFile = "data/linedata-3.txt"; // Change according to input Ex. ../test/clusterdata-3.txt
    String outputFile = "res/cluster-6.png"; // Change according how to name the output image Ex. ../res/cluster-3.png
    int numberClusters = 6; // Change according to input file Ex. 4 when using ../text/clusterdata-4.txt
    //Task task = Task.LINEAL_REGRESSION; // Change if clustering to CLUSTERING
    Task task = Task.CLUSTERING; 
    
    System.out.println("Enter File path:");
    Scanner scan = new Scanner(System.in);
    File inputFile = new File(scan.next());

    Scanner sc = new Scanner(new FileInputStream(inputFile));
    DataFittingAlgo model = new DataFittingAlgo();

    while(sc.hasNextLine()){
      String s = sc.nextLine();
      String st[] = s.split(" ");
      double x = Double.parseDouble(st[0]);
      double y = Double.parseDouble(st[1]);

      model.addPoint(new Point2D(x, y));
    }

    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(1000);
    plotter.setHeight(1000);

    plotter.setDimensions(-500, 500, -500, 500);

    if(task == Task.LINEAL_REGRESSION){
      Line2D l = model.fitLine();

      ArrayList<Point2D> points = model.getPoints();

      double minX = Double.POSITIVE_INFINITY;
      double maxX = -minX;

      for(int i =0; i < points.size(); i++){
        minX = Math.min(minX,points.get(i).getX());
        maxX = Math.max(minX,points.get(i).getX());
        plotter.addPoint((int)points.get(i).getX(), (int) points.get(i).getY());
      }

      plotter.addLine((int)minX, (int)l.calculateY(minX), (int)maxX, (int) l.calculateY(maxX), Color.RED);
    } else {
      Cluster clusters[] = model.kmeans(numberClusters);

      for(int i = 0; i< clusters.length; i++){
        ArrayList<Point2D> points = clusters[i].getPoints();
        
        for(int j = 0; j< points.size(); j++){
          plotter.addPoint((int)points.get(j).getX(), (int) points.get(j).getY(), colors[i]);
        }
      }
    }

    plotter.write(outputFile);
  }  
}