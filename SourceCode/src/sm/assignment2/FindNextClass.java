/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.assignment2;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sm.assignment2.SMAssignment2.AllMethods;
import static sm.assignment2.SMAssignment2.UnClasses;
import static sm.assignment2.SMAssignment2.UnConstructors;
import static sm.assignment2.SMAssignment2.UnMethods;

/**
 *
 * @author Sandakelum
 */
public class FindNextClass {

    private String ModifiedParaTypes;
    private String MIModifiedPara;
    private String ConcatClass;
    private String StoreMeth;
    private String StoreMethcheck;
    MethodInfo mi = new MethodInfo();
    SMAssignment2 sm2 = new SMAssignment2();
    ClassFile cf;

    public FindNextClass() {

    }

    public void CompareMethods(String OpcodeClassName, String OpcodeValues, String MethodDescription, boolean abstractType, String MethName) {
        Class cls;
        try {
            cls = Class.forName(OpcodeClassName);
            MIModifiedPara = mi.ParameterTypes(MethodDescription);
            if (MIModifiedPara.length() != 0) {
                if (MIModifiedPara.charAt(0) == ',') {
                    MIModifiedPara = MIModifiedPara.substring(2, MIModifiedPara.length());
                }
            }
            if (AllMethods.isEmpty()) {
                StoreMethcheck = OpcodeValues + "(" + MIModifiedPara + ")";
                if (OpcodeClassName.equals(OpcodeValues)) {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                    }
                } else if (MethName.equals(StoreMethcheck)) {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called");
                    }
                } else {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                    }
                }
                Method[] CodeMethods = cls.getDeclaredMethods();
                for (Method CodeMethod : CodeMethods) {
                    if (CodeMethod.getName().equals(OpcodeValues)) {
                        ModifiedParaTypes = sm2.MethodParameterTypes(CodeMethod);
                        if (ModifiedParaTypes.equals(MIModifiedPara)) {
                            try {
                                ConcatClass = OpcodeClassName + ".class";
                                StoreMeth = OpcodeValues + "(" + MIModifiedPara + ")";
                                AllMethods.add(MethName + "," + StoreMeth);

                                if (UnMethods.contains(StoreMeth)) {

                                } else {
                                    UnMethods.add(StoreMeth);
                                }

                                if (UnClasses.contains(OpcodeClassName)) {

                                } else {
                                    UnClasses.add(OpcodeClassName);
                                }
                                cf = new ClassFile(ConcatClass, CodeMethod, null, cls);
                            } catch (ClassFileParserException | IOException ex) {
                                Logger.getLogger(FindNextClass.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            } else {
                StoreMethcheck = OpcodeValues + "(" + MIModifiedPara + ")";
                if (AllMethods.contains(MethName + "," + StoreMethcheck)) {
                    if (MethName.equals(StoreMethcheck)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println("\t" + OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract][Recursion]\n");
                        } else {
                            System.out.println("\t" + OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Recursion]\n");
                        }
                    }
                } else {
                    if (OpcodeClassName.equals(OpcodeValues)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                        }
                    } else if (MethName.equals(StoreMethcheck)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called");
                        }
                    } else {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                        }
                    }
                    Method[] CodeMethods = cls.getDeclaredMethods();
                    for (Method CodeMethod : CodeMethods) {
                        if (CodeMethod.getName().equals(OpcodeValues)) {
                            ModifiedParaTypes = sm2.MethodParameterTypes(CodeMethod);
                            if (ModifiedParaTypes.equals(MIModifiedPara)) {
                                try {
                                    ConcatClass = OpcodeClassName + ".class";
                                    StoreMeth = OpcodeValues + "(" + MIModifiedPara + ")";
                                    AllMethods.add(MethName + "," + StoreMeth);

                                    if (UnMethods.contains(StoreMeth)) {

                                    } else {
                                        UnMethods.add(StoreMeth);
                                    }

                                    if (UnClasses.contains(OpcodeClassName)) {

                                    } else {
                                        UnClasses.add(OpcodeClassName);
                                    }
                                    cf = new ClassFile(ConcatClass, CodeMethod, null, cls);
                                } catch (ClassFileParserException | IOException ex) {
                                    Logger.getLogger(FindNextClass.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            MIModifiedPara = mi.ParameterTypes(MethodDescription);
            if (MIModifiedPara.length() != 0) {
                if (MIModifiedPara.charAt(0) == ',') {
                    MIModifiedPara = MIModifiedPara.substring(2, MIModifiedPara.length());
                }
            }
            if (MIModifiedPara == null) {
                MIModifiedPara = "";
            }
            if (UnClasses.contains(OpcodeClassName)) {

            } else {
                UnClasses.add(OpcodeClassName);
            }
            System.out.print("Inside " + MethName + " - > ");
            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Missing] has been called\n");
        }
    }

    public void CompareConstructers(String OpcodeClassName, String OpcodeValues, String MethodDescription, boolean abstractType, String MethName) {
        Class cls;
        try {
            cls = Class.forName(OpcodeClassName);
            MIModifiedPara = mi.ParameterTypes(MethodDescription);
            if (MIModifiedPara.length() != 0) {
                if (MIModifiedPara.charAt(0) == ',') {
                    MIModifiedPara = MIModifiedPara.substring(2, MIModifiedPara.length());
                }
            }
            if (AllMethods.isEmpty()) {
                StoreMethcheck = OpcodeValues + "(" + MIModifiedPara + ")";
                if (OpcodeClassName.equals(OpcodeValues)) {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                    }
                } else if (MethName.equals(StoreMethcheck)) {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                    }
                } else {
                    System.out.print("Inside " + MethName + " - > ");
                    if (abstractType == true) {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                    } else {
                        System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                    }
                }
                Constructor[] constructor = cls.getConstructors();
                for (Constructor cons : constructor) {
                    if (cons.getName().equals(OpcodeValues)) {
                        ModifiedParaTypes = sm2.ConstructorParameterTypes(cons);
                        if (ModifiedParaTypes.equals(MIModifiedPara)) {
                            try {
                                ConcatClass = OpcodeClassName + ".class";
                                StoreMeth = OpcodeValues + "(" + MIModifiedPara + ")";
                                AllMethods.add(MethName + "," + StoreMeth);

                                if (UnConstructors.contains(StoreMeth)) {

                                } else {
                                    UnConstructors.add(StoreMeth);
                                }

                                if (UnClasses.contains(OpcodeClassName)) {

                                } else {
                                    UnClasses.add(OpcodeClassName);
                                }
                                cf = new ClassFile(ConcatClass, null, cons, cls);
                            } catch (ClassFileParserException | IOException ex) {
                                Logger.getLogger(FindNextClass.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            } else {
                StoreMethcheck = OpcodeValues + "(" + MIModifiedPara + ")";
                if (AllMethods.contains(MethName + "," + StoreMethcheck)) {
                    if (MethName.equals(StoreMethcheck)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println("\t" + OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract][Recursion]\n");
                        } else {
                            System.out.println("\t" + OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Recursion]\n");
                        }
                    }
                } else {
                    if (OpcodeClassName.equals(OpcodeValues)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                        }
                    } else if (MethName.equals(StoreMethcheck)) {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                        }
                    } else {
                        System.out.print("Inside " + MethName + " - > ");
                        if (abstractType == true) {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Abstract] has been called\n");
                        } else {
                            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ") has been called\n");
                        }
                    }
                    Constructor[] constructor = cls.getConstructors();
                    for (Constructor cons : constructor) {
                        if (cons.getName().equals(OpcodeValues)) {
                            ModifiedParaTypes = sm2.ConstructorParameterTypes(cons);
                            if (ModifiedParaTypes.equals(MIModifiedPara)) {
                                try {
                                    ConcatClass = OpcodeClassName + ".class";
                                    StoreMeth = OpcodeValues + "(" + MIModifiedPara + ")";
                                    AllMethods.add(MethName + "," + StoreMeth);

                                    if (UnConstructors.contains(StoreMeth)) {

                                    } else {
                                        UnConstructors.add(StoreMeth);
                                    }

                                    if (UnClasses.contains(OpcodeClassName)) {

                                    } else {
                                        UnClasses.add(OpcodeClassName);
                                    }
                                    cf = new ClassFile(ConcatClass, null, cons, cls);
                                } catch (ClassFileParserException | IOException ex) {
                                    Logger.getLogger(FindNextClass.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            MIModifiedPara = mi.ParameterTypes(MethodDescription);
            if (MIModifiedPara.length() != 0) {
                if (MIModifiedPara.charAt(0) == ',') {
                    MIModifiedPara = MIModifiedPara.substring(2, MIModifiedPara.length());
                }
            }
            if (MIModifiedPara == null) {
                MIModifiedPara = "";
            }

            if (UnClasses.contains(OpcodeClassName)) {

            } else {
                UnClasses.add(OpcodeClassName);
            }
            System.out.print("Inside " + MethName + " - > ");
            System.out.println(OpcodeClassName + "." + OpcodeValues + "(" + MIModifiedPara + ")" + "[Missing] has been called\n");
        }
    }
}
