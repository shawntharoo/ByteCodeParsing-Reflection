/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.assignment2;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 *
 * @author Sandakelum
 */
public class MethodInfo {

    private int MethodNameInd;
    private int MethodDescriptInd;
    private int NoOfMethodAttr;
    private int MethodAccessFlag;
    private String MethodName;
    private String MethodDescriptor;
    private String RetrievedMethodName;
    private String ReturnPara;
    private String paraTypesString;
    private String ConcatParaTypes;
    private String str;
    private String MethodAccessFlagNum;
    private boolean abstractType = false;
    private String[] strSplit;
    AttributeInfo AttrInf;
    private String[] ConcatMethodDescriptor;
    private boolean Methodmatch = false;
    private String PassMethod;

    public MethodInfo() {

    }

    //Retrieving method info
    public MethodInfo(DataInputStream dis, ConstantPool constantPoolData, Method method, Constructor con, Class c) throws IOException, InvalidConstantPoolIndex {
        MethodAccessFlag = dis.readUnsignedShort();
        MethodAccessFlagNum = Integer.toHexString(MethodAccessFlag);
        MethodNameInd = dis.readUnsignedShort();
        MethodName = ((ConstantUtf8) constantPoolData.getEntry(MethodNameInd)).getBytes();
        MethodDescriptInd = dis.readUnsignedShort();
        MethodDescriptor = ((ConstantUtf8) constantPoolData.getEntry(MethodDescriptInd)).getBytes();
        NoOfMethodAttr = dis.readUnsignedShort();
        //Check the constructors
        if (MethodName.equals("<init>")) {
            MethodName = c.getSimpleName();
        }
        //Retrieve the method name and the types from the method attribute
        if (method != null) {
            RetrievedMethodName = method.getName();
            paraTypesString = SMAssignment2.MethodParameterTypes(method);
        } else {
            RetrievedMethodName = con.getName();
            paraTypesString = SMAssignment2.ConstructorParameterTypes(con);
        }
        if (RetrievedMethodName.equals(MethodName)) {
            ReturnPara = ParameterTypes(MethodDescriptor);
            if (ReturnPara.length() != 0) {
                if (ReturnPara.charAt(0) == ',') {
                    ReturnPara = ReturnPara.substring(2, ReturnPara.length());
                }
            }
            if (paraTypesString.equals(ReturnPara)) {
                if (MethodAccessFlagNum.equalsIgnoreCase("400")) {
                    abstractType = true;
                }
                PassMethod = MethodName + "(" + ReturnPara + ")";
                Methodmatch = true;
            }
        }
        for (int i = 0; i < NoOfMethodAttr; i++) {
            //call for attributeInfo
            new AttributeInfo(dis, Methodmatch, constantPoolData, c, abstractType, PassMethod);
        }
    }

    //format the parameter types into a specific style
    public String ParameterTypes(String MethodDescriptor) {
        ConcatMethodDescriptor = MethodDescriptor.split("\\)");
        ConcatParaTypes = ConcatMethodDescriptor[0].substring(1, ConcatMethodDescriptor[0].length());
        if (ConcatParaTypes.contains("[Ljava/lang/") || ConcatParaTypes.contains("Ljava/lang/")) {
            for (int i = 0; i < ConcatParaTypes.length(); i++) {
                if (ConcatParaTypes.charAt(i) == '[') {
                    if (ConcatParaTypes.charAt(i + 1) == 'B') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", byte[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'F') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", float[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'C') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", char[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'D') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", double[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'I') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", int[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'J') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", long[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'Z') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", boolean[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'S') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", short[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'L') {
                        str = ConcatParaTypes.substring(i, ConcatParaTypes.lastIndexOf(';'));
                        strSplit = str.split("/");
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 13 + strSplit[2].length()), ", " + strSplit[2] + "[]");
                    }
                } else if (ConcatParaTypes.charAt(i) == 'L') {
                    str = ConcatParaTypes.substring(i, ConcatParaTypes.lastIndexOf(';'));
                    strSplit = str.split("/");
                    ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 12 + strSplit[2].length()), ", " + strSplit[2]);
                } else {
                    if (ConcatParaTypes.charAt(i) == 'B') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", byte");
                    }
                    if (ConcatParaTypes.charAt(i) == 'F') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", float");
                    }
                    if (ConcatParaTypes.charAt(i) == 'C') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", char");
                    }
                    if (ConcatParaTypes.charAt(i) == 'D') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", double");
                    }
                    if (ConcatParaTypes.charAt(i) == 'I') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", int");
                    }
                    if (ConcatParaTypes.charAt(i) == 'J') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", long");
                    }
                    if (ConcatParaTypes.charAt(i) == 'Z') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", boolean");
                    }
                    if (ConcatParaTypes.charAt(i) == 'S') {
                        if (ConcatParaTypes.charAt(i + 5) == 'g') {

                        } else {
                            ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", short");
                        }
                    }
                }
            }
        }else if(ConcatParaTypes.contains("[L") || ConcatParaTypes.contains("L")){
            for (int i = 0; i < ConcatParaTypes.length(); i++) {
                if (ConcatParaTypes.charAt(i) == '[') {
                    if (ConcatParaTypes.charAt(i + 1) == 'B') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", byte[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'F') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", float[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'C') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", char[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'D') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", double[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'I') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", int[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'J') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", long[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'Z') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", boolean[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'S') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2), ", short[]");
                    } else if (ConcatParaTypes.charAt(i + 1) == 'L') {
                        str = ConcatParaTypes.substring(i+2, ConcatParaTypes.lastIndexOf(';'));
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 2 + str.length()), ", " + str + "[]");
                    }
                } else if (ConcatParaTypes.charAt(i) == 'L') {
                    str = ConcatParaTypes.substring(i+1, ConcatParaTypes.lastIndexOf(';'));
                    ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1 + str.length()), ", " + str);
                } else {
                    if (ConcatParaTypes.charAt(i) == 'B') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", byte");
                    }
                    if (ConcatParaTypes.charAt(i) == 'F') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", float");
                    }
                    if (ConcatParaTypes.charAt(i) == 'C') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", char");
                    }
                    if (ConcatParaTypes.charAt(i) == 'D') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", double");
                    }
                    if (ConcatParaTypes.charAt(i) == 'I') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", int");
                    }
                    if (ConcatParaTypes.charAt(i) == 'J') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", long");
                    }
                    if (ConcatParaTypes.charAt(i) == 'Z') {
                        ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", boolean");
                    }
                    if (ConcatParaTypes.charAt(i) == 'S') {
                        if (ConcatParaTypes.charAt(i + 5) == 'g') {

                        } else {
                            ConcatParaTypes = ConcatParaTypes.replace(ConcatParaTypes.substring(i, i + 1), ", short");
                        }
                    }
                }
            }
        }else {
            if (ConcatParaTypes.contains("[B")) {
                ConcatParaTypes = ConcatParaTypes.replace("[B", ", byte[]");
            }
            if (ConcatParaTypes.contains("B")) {
                ConcatParaTypes = ConcatParaTypes.replace("B", ", byte");
            }
            if (ConcatParaTypes.contains("[F")) {
                ConcatParaTypes = ConcatParaTypes.replace("[F", ", float[]");
            }
            if (ConcatParaTypes.contains("F")) {
                ConcatParaTypes = ConcatParaTypes.replace("F", ", float");
            }
            if (ConcatParaTypes.contains("C")) {
                ConcatParaTypes = ConcatParaTypes.replace("C", ", char");
            }
            if (ConcatParaTypes.contains("[C")) {
                ConcatParaTypes = ConcatParaTypes.replace("[C", ", char[]");
            }
            if (ConcatParaTypes.contains("D")) {
                ConcatParaTypes = ConcatParaTypes.replace("D", ", double");
            }
            if (ConcatParaTypes.contains("[D")) {
                ConcatParaTypes = ConcatParaTypes.replace("[D", ", double[]");
            }
            if (ConcatParaTypes.contains("I")) {
                ConcatParaTypes = ConcatParaTypes.replace("I", ", int");
            }
            if (ConcatParaTypes.contains("[I")) {
                ConcatParaTypes = ConcatParaTypes.replace("[I", ", int[]");
            }
            if (ConcatParaTypes.contains("J")) {
                ConcatParaTypes = ConcatParaTypes.replace("J", ", long");
            }
            if (ConcatParaTypes.contains("[J")) {
                ConcatParaTypes = ConcatParaTypes.replace("[J", ", long[]");
            }
            if (ConcatParaTypes.contains("Z")) {
                ConcatParaTypes = ConcatParaTypes.replace("Z", ", boolean");
            }
            if (ConcatParaTypes.contains("[Z")) {
                ConcatParaTypes = ConcatParaTypes.replace("[Z", ", boolean");
            }
            if (ConcatParaTypes.contains("S")) {
                ConcatParaTypes = ConcatParaTypes.replace("S", ", short");
            }
            if (ConcatParaTypes.contains("[S")) {
                ConcatParaTypes = ConcatParaTypes.replace("[S", ", short");
            }
        }
        return ConcatParaTypes;
    }
}
