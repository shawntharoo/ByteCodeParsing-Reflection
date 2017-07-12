 abstract class abstractTest{  
   abstractTest()
   {
       System.out.println(" ");
   }  
   abstract void method1();  
   void mathod2()
   {
       System.out.println("inside method2");
   }  
 }  
  
 class MainAbstract extends abstractTest{  
 void method1()
 {
     System.out.println("inside method1");
 }  
 }  
 class abstractTest2{  
 public static void main(String args[]){  
  abstractTest obj = new MainAbstract();  
  obj.method1();  
  obj.mathod2();  
 }  
}  