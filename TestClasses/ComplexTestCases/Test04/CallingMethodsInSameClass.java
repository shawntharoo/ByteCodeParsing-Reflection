/* CallingMethodsInSameClass.java
 *
 * illustrates how to call static methods a class
 * from a method in the same class
 */

public class CallingMethodsInSameClass
{
	public static void main(String[] args) {
		FirstMethod();
		FirstMethod();
		SecondMethod();
	}

	public static void FirstMethod() {
	}

	public static void SecondMethod() {
		FirstMethod();
		FirstMethod();
	}
}