package com.cbtest.in;

public class ConsoleManagerFactory {
    private static ConsoleManager consoleManager;

    public static ConsoleManager getConsoleManager() {
        if (consoleManager == null) {
            consoleManager = new ConsoleManager();
        }
        return consoleManager;
    }
}
