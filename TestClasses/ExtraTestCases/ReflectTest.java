import java.io.PrintStream;


public class ReflectTest
{
  public ReflectTest() {}
  
  public static void arthurDent(int paramInt)
  {
    if (paramInt == 42)
    {
      for (;;)
      {
        System.out.print("The mice did it. ");
      }
    }
    

    System.out.println("I seem to be having tremendous difficulty with my lifestyle.");
  }
  








  public static String bottleOfBeer(int paramInt)
  {
    if (paramInt < 0)
    {
      throw new IndexOutOfBoundsException("Negative beer not allowed.");
    }
    String str;
    switch (paramInt)
    {
    case 0: 
      str = "no bottles";
      break;
    
    case 1: 
      str = "1 bottle";
      break;
    
    default: 
      str = paramInt + " bottles";
    }
    add(1);
    return str + " of beer on the wall";
  }
  
  private static int sum = 0;
  
  public static int add(int paramInt) {
    sum += paramInt;
    return sum;
  }
  
  public static void colour(int paramInt)
  {
    paramInt++;
    int i = paramInt / 8;
    int j = paramInt % 8 + 30;
    System.out.print("\033[" + i + ";" + j + "m");
  }
}