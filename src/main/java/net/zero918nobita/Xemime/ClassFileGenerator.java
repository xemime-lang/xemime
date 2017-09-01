package net.zero918nobita.Xemime;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ClassGen;

import java.io.IOException;

public class ClassFileGenerator {
    public static void main(String[] args) {
        final String className = "Adder";
        JavaClass base;
        try {
            ClassParser classParser = new ClassParser("Empty.class");
            base = classParser.parse();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        ClassGen cg = new ClassGen(base);
        cg.setClassName(className);
        JavaClass c = cg.getJavaClass();
        c.setSourceFileName(className + ".java");
        try {
            c.dump(className + ".class");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
