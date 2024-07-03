package com.cbtest.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleManager {
    private BufferedReader reader;

    public String readLine() throws IOException {
        if (System.console() != null) {
            return System.console().readLine();
        }
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        return reader.readLine();
    }
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
