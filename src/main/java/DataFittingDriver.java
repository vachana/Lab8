import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class DataFittingDriver {

  public static void main(String[] args) throws FileNotFoundException {
    DataPoint dp = new DataPoint();
    Scanner s = new Scanner(System.in);
    File file = new File(s.next());
    Scanner sc = new Scanner(file);
    while ((sc.hasNext())) {
      dp.addData(Double.valueOf(sc.next()), Double.valueOf(sc.next()));
    }
    List<Point2D> value = dp.dataList();
    dp.kmeans(5);
//    for(int i=0;i<value.size();i++){
//      System.out.println(value.get(i).x+ " : t : "+value.get(i).y);
//    }
  }
}