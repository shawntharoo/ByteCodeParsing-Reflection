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
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import static sm.assignment2.SMAssignment2.AllMethods;
import static sm.assignment2.SMAssignment2.UnClasses;
import static sm.assignment2.SMAssignment2.UnConstructors;
import static sm.assignment2.SMAssignment2.UnMethods;

public class ClassFile {

    private String filename;
    private long magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int interfaceCount;
    private int NoOffields;
    private int NoOffieldAttr;
    private long fieldAttrLength;
    private int NoOfMethods;
    private int NoOfAttr;
    MethodInfo MethodNames;
    AttributeInfo ClassAttrInf;
    // ...

    /**
     * Parses a class file an constructs a ClassFile object. At present, this
     * only parses the header and constant pool.
     *
     * @throws java.io.IOException
     */
    public ClassFile(String filename, Method method, Constructor con, Class c) throws ClassFileParserException,
            IOException {
        DataInputStream dis
                = new DataInputStream(new FileInputStream(filename));
        this.filename = filename;
        magic = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        minorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        constantPool = new ConstantPool(dis);
        dis.skipBytes(2);
        dis.skipBytes(2);
        dis.skipBytes(2);
        interfaceCount = dis.readUnsignedShort();
        dis.skipBytes(interfaceCount * 2);
        NoOffields = dis.readUnsignedShort();
        for (int i = 0; i < NoOffields; i++) {
            dis.skipBytes(2);
            dis.skipBytes(2);
            dis.skipBytes(2);
            NoOffieldAttr = dis.readUnsignedShort();
            for (int j = 0; j < NoOffieldAttr; j++) {
                dis.skipBytes(2);
                fieldAttrLength = (long) dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
                for (int k = 0; k < fieldAttrLength; k++) {
                    dis.skipBytes(2);
                }
            }
        }
        //Method details
        NoOfMethods = dis.readUnsignedShort();
        for (int i = 0; i < NoOfMethods; i++) {
            //call methodInfo for retrieving method names
            MethodNames = new MethodInfo(dis, constantPool, method, con, c);
        }
        //Attribute details of the class
        NoOfAttr = dis.readUnsignedShort();
        for (int i = 0; i < NoOfAttr; i++) {
            //call AttributeInfo for retrieving attribute data
            new AttributeInfo(dis, false, constantPool, c, false, null);
        }
    }

    /**
     * Returns the contents of the class file as a formatted String.
     */
    public String toString() {
        return String.format(
                "Filename: %s\n"
                + "Magic: 0x%08x\n"
                + "Class file format version: %d.%d\n\n"
                + "Constant pool:\n\n%s",
                filename, magic, majorVersion, minorVersion, constantPool);
    }
}
