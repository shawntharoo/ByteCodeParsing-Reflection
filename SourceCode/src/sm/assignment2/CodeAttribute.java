/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.assignment2;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sandakelum
 */
public class CodeAttribute {

    private int CodeAttrCount;
    private int CodeLength;
    private byte OpcodeData[];
    private int ExceptionTLength;
    Instruction instruction;
    private String OpcodeMnemonic;
    private String operandString;
    private String indexbyte[];
    private Class ClassName;
    private int opcodeInt;
    private String OpcodeValues;
    private String OpcodeClassName;
    private String ModifiedParaTypes;
    private String MIModifiedPara;
    private String MethodDescription;
    private String index;
    Class noparams[] = {};
    Class[] argTypes = new Class[]{int.class};
    MethodInfo mi = new MethodInfo();
    SMAssignment2 sm2 = new SMAssignment2();
    ClassFile cf;

    public CodeAttribute(DataInputStream dis, ConstantPool constantPool, Class c, boolean abstractType, String methName) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        dis.skipBytes(2);
        dis.skipBytes(2);
        CodeLength = (int) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        OpcodeData = new byte[CodeLength];
        for (int j = 0; j < CodeLength; j++) {
            OpcodeData[j] = dis.readByte();
        }
        ClassName = c.getClass();
        for (int k = 0; k < CodeLength;) {
            try {
                instruction = new Instruction(OpcodeData, k);
            } catch (CodeParsingException ex) {
                Logger.getLogger(CodeAttribute.class.getName()).log(Level.SEVERE, null, ex);
            }
            OpcodeMnemonic = instruction.getOpcode().getMnemonic();
            if (OpcodeMnemonic.equals("invokestatic") || OpcodeMnemonic.equals("invokevirtual") || OpcodeMnemonic.equals("invokespecial")) {
                try {
                    operandString = instruction.getOperandString();
                    indexbyte = operandString.split("indexbyte2=");
                    index = indexbyte[1].trim().substring(0, 2);
                    opcodeInt = Integer.parseInt(index, 16);
                    OpcodeValues = ((ConstantMethodRef) constantPool.getEntry(opcodeInt)).getName();
                    OpcodeClassName = ((ConstantMethodRef) constantPool.getEntry(opcodeInt)).getClassName();
                    if (OpcodeClassName.equals("java/lang/Object")) {
                        OpcodeClassName = "Object";
                    }
                    if (OpcodeValues.equals("<init>")) {
                        OpcodeValues = OpcodeClassName;
                    }
                    MethodDescription = ((ConstantMethodRef) constantPool.getEntry(opcodeInt)).getType();
                } catch (InvalidConstantPoolIndex ex) {
                    Logger.getLogger(CodeAttribute.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (OpcodeValues.equals(OpcodeClassName)) {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareConstructers(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                } else {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareMethods(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                }
            }else if(OpcodeMnemonic.equals("invokeinterface")){
                try {
                    operandString = instruction.getOperandString();
                    indexbyte = operandString.split("indexbyte2=");
                    index = indexbyte[1].trim().substring(0, 2);
                    opcodeInt = Integer.parseInt(index, 16);
                    OpcodeValues = ((ConstantInterfaceMethodRef) constantPool.getEntry(opcodeInt)).getName();
                    OpcodeClassName = ((ConstantInterfaceMethodRef) constantPool.getEntry(opcodeInt)).getClassName();
                    if (OpcodeClassName.equals("java/lang/Object")) {
                        OpcodeClassName = "Object";
                    }
                    if (OpcodeValues.equals("<init>")) {
                        OpcodeValues = OpcodeClassName;
                    }
                    MethodDescription = ((ConstantInterfaceMethodRef) constantPool.getEntry(opcodeInt)).getType();
                } catch (InvalidConstantPoolIndex ex) {
                    Logger.getLogger(CodeAttribute.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (OpcodeValues.equals(OpcodeClassName)) {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareConstructers(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                } else {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareMethods(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                }
            }else if(OpcodeMnemonic.equals("invokedynamic")){
                try {
                    operandString = instruction.getOperandString();
                    indexbyte = operandString.split("indexbyte2=");
                    index = indexbyte[1].trim().substring(0, 2);
                    opcodeInt = Integer.parseInt(index, 16);
                    OpcodeValues = ((ConstantInvokeDynamic) constantPool.getEntry(opcodeInt)).getName();
                    OpcodeClassName = ((ConstantInvokeDynamic) constantPool.getEntry(opcodeInt)).getName();
                    if (OpcodeClassName.equals("java/lang/Object")) {
                        OpcodeClassName = "Object";
                    }
                    if (OpcodeValues.equals("<init>")) {
                        OpcodeValues = OpcodeClassName;
                    }
                    MethodDescription = ((ConstantInvokeDynamic) constantPool.getEntry(opcodeInt)).getType();
                } catch (InvalidConstantPoolIndex ex) {
                    Logger.getLogger(CodeAttribute.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (OpcodeValues.equals(OpcodeClassName)) {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareConstructers(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                } else {
                    FindNextClass FNC = new FindNextClass();
                    FNC.CompareMethods(OpcodeClassName, OpcodeValues, MethodDescription, abstractType, methName);
                }
            }
            k += instruction.getSize();
        }
        ExceptionTLength = dis.readUnsignedShort();
        for (int k = 0; k < ExceptionTLength; k++) {
            dis.skipBytes(2);
            dis.skipBytes(2);
            dis.skipBytes(2);
            dis.skipBytes(2);
        }
        CodeAttrCount = dis.readUnsignedShort();
        for (int l = 0; l < CodeAttrCount; l++) {
            try {
                new AttributeInfo(dis, true, constantPool, c, false, null);
            } catch (InvalidConstantPoolIndex ex) {
                Logger.getLogger(CodeAttribute.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
