package com.cbtest.state.impl.Account;

import com.cbtest.state.ConsoleState;

public class CloseAccountState implements ConsoleState
{

    @Override
    public void run() throws Exception {

    }

    @Override
    public ConsoleState nextState() {
        return null;
    }
}
