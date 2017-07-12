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
public class AttributeInfo {

    private int AttrNameIndex;
    private long AttrLength;
    private String AttrType;
    AttributeInfo attrinfo;
    Instruction instruction;
    CodeAttribute code;

    public AttributeInfo(DataInputStream dis, boolean methodmatch, ConstantPool constantPool, Class c, boolean abstractType, String methName) throws IOException, InvalidConstantPoolIndex {
        AttrNameIndex = dis.readUnsignedShort();
        AttrType = ((ConstantUtf8) constantPool.getEntry(AttrNameIndex)).getBytes();
        AttrLength = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        //Goto the code level if it is selected method
        if (methodmatch == true) {
            //Get the code from the code attribute
            if (AttrType.equalsIgnoreCase("code")) {
                try {
                    //retireving the detils inside the codeattribute
                    code = new CodeAttribute(dis, constantPool, c, abstractType, methName);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
                    Logger.getLogger(AttributeInfo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Skip the bytes if it is not CODE
            else {
                for (int i = 0; i < AttrLength; i++) {
                    dis.skipBytes(1);
                }
            }
        } //Skip the bytes if it is not the selected method
        else {
            for (int i = 0; i < AttrLength; i++) {
                dis.skipBytes(1);
            }
        }
    }
}
