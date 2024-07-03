package com.cbtest.state.impl.transaction;

import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.state.ConsoleState;

public class DepositState implements ConsoleState {
    @Override
    public void run() throws Exception {
    // Депозит средств
        throw new ClientExistException("Hf,jnftn");
    }

    @Override
    public ConsoleState nextState() {
        return null;
    }
}
