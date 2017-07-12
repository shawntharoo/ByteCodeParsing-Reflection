import java.io.PrintStream;

public class Recursion {
  
  public static void main(String[] values) {
    try { 
      int i = Integer.parseInt(values[0]);
      long fact = Findfactorial(i);
      System.out.println("factorial(" + i + ") == " + fact);
    }
    catch (RuntimeException localRuntimeException)
    {
      System.out.println("Error in Factorial");
    }
  }
  
  public static long Findfactorial(int val)
  {
    long x = val;
    if (val > 1)
    {
      x =x * (Findfactorial(val - 1));
    }
    for (int i = 0; i < 1000; i++) {}
    return x;
  }
}