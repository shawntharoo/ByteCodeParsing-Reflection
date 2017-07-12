/* CallingMethodsInSameClass.java
 *
 * illustrates how to call static methods a class
 * from a method in the same class
 */

public class MethodInForLoop
{
	public static void main(String[] args) {
		for(int i=0; i<3; i++){
			FirstMethod();
		}
	}

	public static void FirstMethod() {
	}
}