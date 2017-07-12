import java.io.PrintStream;

public class Numbers {
  public Numbers() {}
  
  public static void main(String[] paramArrayOfString) {
    try { int i = Integer.parseInt(paramArrayOfString[0]);
      long l1 = factorial(i);
      long l2 = fibonacci(i);
      
      System.out.println("factorial(" + i + ") == " + l1);
      System.out.println("fibonacci(" + i + ") == " + l2);
    }
    catch (RuntimeException localRuntimeException)
    {
      System.out.println("Usage: java Numbers <number>");
    }
  }
  
  public static long factorial(int paramInt)
  {
    long l = paramInt;
    if (paramInt > 1)
    {
      l *= factorial(paramInt - 1);
    }
    for (int i = 0; i < 1000; i++) {}
    return l;
  }
  
  public static long fibonacci(int paramInt)
  {
    long l = paramInt;
    if (paramInt > 1)
    {
      l = fibonacci(paramInt - 1) + fibonacci(paramInt - 2);
    }
    return l;
  }
}