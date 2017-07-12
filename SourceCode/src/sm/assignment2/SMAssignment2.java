/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.assignment2;

/**
 *
 * @author Sandakelum
 */
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMAssignment2 {

    public static List<String> AllMethods = new ArrayList<>();
    public static List<String> UnMethods = new ArrayList<>();
    public static List<String> UnConstructors = new ArrayList<>();
    public static List<String> UnClasses = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String className;
        String CName;
        String[] SplitedN;
        Method Meth;
        Constructor con;
        String FUllmethod;
        //User input for class name
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the class name: ");
        CName = reader.nextLine();
        SplitedN = CName.split("\\.");
        className = SplitedN[0];
        try {
            //Convert the string into a class
            Class c = Class.forName(className);
            //Get the class Name
            Class name = c.getClass();
            //Call for the method to get the methods
            //Get the methods of the class
            Method[] methods = c.getDeclaredMethods();
            Constructor[] constructor = c.getConstructors();
            //Call for the method to get the parameters
            PrintAllMethods(methods, constructor);
            //User Input for method name
            System.out.println("Enter the Method or Constructor name with parameters(eg:MethodName(para1, para2)):");
            FUllmethod = reader.nextLine();

            Meth = MethodCheck(c, FUllmethod);
            con = ConstructerCheck(c, FUllmethod);
            try {
                UnClasses.add(c.getName());
                //Call for the classFile class to extract the byte code
                ClassFile classF = new ClassFile(CName, Meth, con, c);
            } catch (ClassFileParserException | IOException ex) {
                Logger.getLogger(SMAssignment2.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found -> " + e);
        }
System.out.println("*******************************\n");
        System.out.println("Number of unique METHODS are: " + UnMethods.size() + "\n" + UnMethods + "\n");
        System.out.println("Number of unique CONSTRUCTORS are: " + UnConstructors.size() + "\n" + UnConstructors + "\n");
        System.out.println("Number of unique CLASSES are: " + UnClasses.size() + "\n" + UnClasses + "\n");
    }

    /*Match the user entered method with the methods retrieved from the reflection 
    and return a method with parameters*/
    public static Method MethodCheck(Class c, String FUllmethod) {
        String Parameters;
        String[] ConMethodName;
        String substirngPara;
        Method parsingMeth = null;
        ConMethodName = FUllmethod.split("\\(");
        //Get the methods of the class
        Method[] methods = c.getDeclaredMethods();
        for (Method m1 : methods) {
            if (m1.getName().equals(ConMethodName[0])) {
                //Extract the parameters
                substirngPara = ConMethodName[1].substring(0, ConMethodName[1].length() - 1);
                //Call for the method to get the parameters
                Parameters = MethodParameterTypes(m1);
                if (Parameters.equals(substirngPara)) {
                    parsingMeth = m1;
                    System.out.println("The method you have selected is -> " + m1.getName() + "(" + Parameters + ")");
                    System.out.println("*******************************\n");
                    if (Parameters == null) {
                        Parameters = "";
                    }
                    UnMethods.add(m1.getName() + "(" + Parameters + ")");
                    System.out.print("The first method is -> ");
                    System.out.println(c.getName() + "." + m1.getName() + "(" + Parameters + ")\n");
                }
            }
        }
        return parsingMeth;
    }

    /*Match the user entered constructor with the constructors retrieved from the reflection 
    and return a constructor with parameters*/
    public static Constructor ConstructerCheck(Class c, String FUllmethod) {
        String Parameters;
        String[] ConMethodName;
        String substirngPara;
        Constructor parsingMeth = null;
        ConMethodName = FUllmethod.split("\\(");
        //Get the methods of the class
        Constructor[] constructor = c.getConstructors();
        for (Constructor cons : constructor) {
            if (cons.getName().equals(ConMethodName[0])) {
                //Extract the parameters
                substirngPara = ConMethodName[1].substring(0, ConMethodName[1].length() - 1);
                //Call for the method to get the parameters
                Parameters = ConstructorParameterTypes(cons);
                if (Parameters.equals(substirngPara)) {
                    parsingMeth = cons;
                    System.out.println("The constructer you have selected is: " + cons.getName() + "(" + Parameters + ")\n");
                    System.out.println("*******************************\n");
                    if (Parameters == null) {
                        Parameters = "";
                    }
                    UnMethods.add(cons.getName() + "(" + Parameters + ")");
                    System.out.println(c.getName() + "." + cons.getName() + "(" + Parameters + ")");
                }
            }
        }
        return parsingMeth;
    }

    //Method use to display all the methods, constructors and their parametetrs
    public static void PrintAllMethods(Method[] methods, Constructor[] constructor) {
        String convertedParaTypes;
        String modifiedParaTypes;
        String str;
        String[] strSplit;

        System.out.println("*******************************\n");
        System.out.println("<--The methods and the parameters of each method of the selected class-->");
        //Iterate through constructors and display
        for (Constructor cons : constructor) {
            //Get the parameter types of each constructor
            Class[] paraTypes = cons.getParameterTypes();
            convertedParaTypes = Arrays.toString(paraTypes);
            modifiedParaTypes = convertedParaTypes.substring(1, convertedParaTypes.length() - 1);
            if (modifiedParaTypes.contains("java.lang")) {
                for (int i = 0; i < modifiedParaTypes.length(); i++) {
                    if (modifiedParaTypes.charAt(i) == '[') {
                        if (modifiedParaTypes.charAt(i + 2) == 'j') {
                            str = modifiedParaTypes.substring(i, modifiedParaTypes.lastIndexOf(';'));
                            str = str.substring(12, str.length());
                            modifiedParaTypes = modifiedParaTypes.replace(modifiedParaTypes.substring(i - 6, i + 13 + str.length()), str + "[]");
                        }
                    } else {
                        if (modifiedParaTypes.charAt(i) == 'j') {
                            str = modifiedParaTypes.substring(i, modifiedParaTypes.lastIndexOf(' '));
                            if (str.charAt(str.length() - 1) == ',') {
                                str = str.substring(10, str.length() - 1);
                            } else {
                                str = str.substring(10, str.length());
                            }
                            modifiedParaTypes = modifiedParaTypes.replace(modifiedParaTypes.substring(i - 6, i + 10 + str.length()), str);

                        }
                    }
                }
            }
            System.out.println(cons.getName() + "(" + modifiedParaTypes + ")");
        }
        //Iterate through constructors and display
        for (Method m : methods) {
            //Get the parameter types of each method
            Class[] paraTypes = m.getParameterTypes();
            convertedParaTypes = Arrays.toString(paraTypes);
            modifiedParaTypes = convertedParaTypes.substring(1, convertedParaTypes.length() - 1);
            if (modifiedParaTypes.contains("java.lang")) {
                for (int i = 0; i < modifiedParaTypes.length(); i++) {
                    if (modifiedParaTypes.charAt(i) == '[') {
                        if (modifiedParaTypes.charAt(i + 2) == 'j') {
                            str = modifiedParaTypes.substring(i, modifiedParaTypes.lastIndexOf(';'));
                            str = str.substring(12, str.length());
                            modifiedParaTypes = modifiedParaTypes.replace(modifiedParaTypes.substring(i - 6, i + 13 + str.length()), str + "[]");
                        }
                    } else {
                        if (modifiedParaTypes.charAt(i) == 'j') {
                            System.out.println(modifiedParaTypes);
                            modifiedParaTypes = modifiedParaTypes.trim();
                            str = modifiedParaTypes.substring(i, modifiedParaTypes.lastIndexOf(' '));
                            if (str.charAt(str.length() - 1) == ',') {
                                str = str.substring(10, str.length() - 1);
                            } else {
                                str = str.substring(10, str.length());
                            }
                            modifiedParaTypes = modifiedParaTypes.replace(modifiedParaTypes.substring(i - 6, i + 10 + str.length()), str);
                        }
                    }
                }
            }
            System.out.println(m.getName() + "(" + modifiedParaTypes + ")");
        }
        System.out.println("*******************************\n");
    }

    //Method use to return all the parametetrs of a specific method
    public static String MethodParameterTypes(Method m) {
        String OMconvertedParaTypes;
        String OMmodifiedParaTypes;
        String str;
        String[] strSplit;
        //Get the parameter types of each method
        Class[] OMparaTypes = m.getParameterTypes();
        OMconvertedParaTypes = Arrays.toString(OMparaTypes);
        OMmodifiedParaTypes = OMconvertedParaTypes.substring(1, OMconvertedParaTypes.length() - 1);
        if (OMmodifiedParaTypes.contains("java.lang")) {
            for (int i = 0; i < OMmodifiedParaTypes.length(); i++) {
                if (OMmodifiedParaTypes.charAt(i) == '[') {
                    if (OMmodifiedParaTypes.charAt(i + 2) == 'j') {
                        str = OMmodifiedParaTypes.substring(i, OMmodifiedParaTypes.lastIndexOf(';'));
                        str = str.substring(12, str.length());
                        OMmodifiedParaTypes = OMmodifiedParaTypes.replace(OMmodifiedParaTypes.substring(i - 6, i + 13 + str.length()), str + "[]");
                    }
                } else {
                    if (OMmodifiedParaTypes.charAt(i) == 'j') {
                        str = OMmodifiedParaTypes.substring(i, OMmodifiedParaTypes.lastIndexOf(' '));
                        if (str.charAt(str.length() - 1) == ',') {
                            str = str.substring(10, str.length() - 1);
                        } else {
                            str = str.substring(10, str.length());
                        }
                        OMmodifiedParaTypes = OMmodifiedParaTypes.replace(OMmodifiedParaTypes.substring(i - 6, i + 10 + str.length()), str);
                    }
                }
            }
        }
        return OMmodifiedParaTypes;
    }

    //Method use to return all the parametetrs of a specific constructor
    public static String ConstructorParameterTypes(Constructor cons) {
        String OMconvertedParaTypes;
        String OMmodifiedParaTypes;
        String str;
        String[] strSplit;
        //Get the parameter types of each constructor
        Class[] OMparaTypes = cons.getParameterTypes();
        OMconvertedParaTypes = Arrays.toString(OMparaTypes);
        OMmodifiedParaTypes = OMconvertedParaTypes.substring(1, OMconvertedParaTypes.length() - 1);
        if (OMmodifiedParaTypes.contains("java.lang")) {
            for (int i = 0; i < OMmodifiedParaTypes.length(); i++) {
                if (OMmodifiedParaTypes.charAt(i) == '[') {
                    if (OMmodifiedParaTypes.charAt(i + 2) == 'j') {
                        str = OMmodifiedParaTypes.substring(i, OMmodifiedParaTypes.lastIndexOf(';'));
                        str = str.substring(12, str.length());
                        OMmodifiedParaTypes = OMmodifiedParaTypes.replace(OMmodifiedParaTypes.substring(i - 6, i + 13 + str.length()), str + "[]");
                    }
                } else {
                    if (OMmodifiedParaTypes.charAt(i) == 'j') {
                        str = OMmodifiedParaTypes.substring(i, OMmodifiedParaTypes.lastIndexOf(' '));
                        if (str.charAt(str.length() - 1) == ',') {
                            str = str.substring(10, str.length() - 1);
                        } else {
                            str = str.substring(10, str.length());
                        }
                        OMmodifiedParaTypes = OMmodifiedParaTypes.replace(OMmodifiedParaTypes.substring(i - 6, i + 10 + str.length()), str);
                    }
                }
            }
        }
        return OMmodifiedParaTypes;
    }
}
