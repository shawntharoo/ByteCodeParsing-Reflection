import java.io.*;


public class MissingMethods
{

  public static String missingCheck(int val)
  {
    if (val < 0)
    {
      System.out.println("Less than 0 is not a valid number");
    }
    String appnd = null;
    switch (val)
    {
    case 0: 
      appnd = val + "numbers";
      break;
    
    case 1: 
      appnd = "any number greater than 0";
      break;
    
    default: 
      System.out.println("Invalid number");
    }
    
    return appnd;
  }
  
}
