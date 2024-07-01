package com.cbtest.state;

public interface ConsoleState {
    void run() throws Exception;

    ConsoleState nextState();
}
