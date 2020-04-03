public class Line2D {
  private double a, b, c;

  public Line2D(double a, double b, double c){
    this.a = a;
    this.b = b;
    this.c = c;
  }
  public double getA(){
    return a;
  }
  public double getB(){
    return b;
  }
  public double getC(){
    return c;
  }

  public double calculateY(double x){
    if(b == 0){
      return Math.random();
    }
    return (a*x+c)/-b;
  }
}