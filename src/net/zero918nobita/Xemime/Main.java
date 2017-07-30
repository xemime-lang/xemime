package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Lexer lex = new Lexer(in);
            Parser parser = new Parser();
            Interpreter interpreter = new Interpreter();
            HashMap<String, Reference> local = new HashMap<>();
            Environment env = new Environment(1, interpreter, local);
            while (true) {
                System.out.print("Prelude> ");
                X_Object obj = parser.parse(lex);
                if (obj == null) break;
                System.out.println(obj.run(env).toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
