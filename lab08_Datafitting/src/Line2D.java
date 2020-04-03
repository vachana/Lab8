
/**
 * Class to represent the equation of a Line
 */
public class Line2D{
  private double a, b, c;

  public Line2D(double a, double b, double c){
    this.a = a;
    this.b = b;
    this.c = c;
  }

  /**
   * Get the value of a
   */
  public double getA(){
    return a;
  }

  /**
   * Get the value of b
   */
  public double getB(){
    return b;
  }

  /**
   * Get the value of c
   */
  public double getC(){
    return c;
  }

  /**
   * Estimate the value of y for a given x
   * according to he equation of the line
   *
   * @param x
   */
  public double calculateY(double x){
    if(b == 0){
      return Math.random();
    }
    return (a*x+c)/-b;
  }
}