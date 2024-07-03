package com.cbtest.app;

import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.MainState;

public class AppLogic {
    private ConsoleState state;
    public AppLogic() {
        this.state = new MainState();
    }

    public void run() {
        while (true) {
            try {
                state.run();
                if (state != null) {
                    state = state.nextState();
                }
            } catch (Exception e) {
                System.err.println("Проблемы с вводом:" + e.getMessage());
                state = new MainState();
            }
        }
    }
}
