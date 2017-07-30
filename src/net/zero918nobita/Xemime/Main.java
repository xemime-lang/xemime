package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Lexer lex = new Lexer(in);
        Parser parser = new Parser();
    }
}
