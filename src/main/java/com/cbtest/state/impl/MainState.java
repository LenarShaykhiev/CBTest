package com.cbtest.state.impl;


import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.account.CloseAccountState;
import com.cbtest.state.impl.account.CreateAccountState;
import com.cbtest.state.impl.client.CreateClientState;

public class MainState implements ConsoleState {

    private final ConsoleManager consoleManager;
    private ConsoleState nextState;

    public MainState() {
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
    }

    @Override
    public void run() throws Exception {
        System.out.printf("Выберите действие:%n '1' - Создать клиента%n " +
                "'2' - Создать счет клиенту%n" +
                "'3' - Закрыть счет клиенту%n " +
                "'4' - Перевести денежные средства% " +
                "'5' - зачислить денежные средства");
        String input = consoleManager.readLine();
        if (input == null) {
            System.out.println("Введите нужный пункт:");
        } else switch (input) {
            case "1" -> {
                nextState = new CreateClientState();
                consoleManager.clear();
            }
            case "2" -> {
                nextState = new CreateAccountState();
                consoleManager.clear();
            }
            case "3" -> {
                nextState = new CloseAccountState();
                consoleManager.clear();
            }
            case "4" -> {
                nextState = new TransferMoneyState();
                consoleManager.clear();
            }
            case "5" -> {
                nextState = new DepositMoneyState();
                consoleManager.clear();
            }
            default -> {
//                System.out.println("Введите нужный пункт:");
                consoleManager.readLine();
                consoleManager.clear();
                nextState = new MainState();
            }
        }
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
