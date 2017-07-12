import java.io.PrintStream;


public class NoCallTree
{
  
  private static int tot = 0;
  
  public static int addition(int val) {
    tot = tot + val;
    return tot;
  }
}