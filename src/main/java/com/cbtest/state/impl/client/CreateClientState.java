package com.cbtest.state.impl.client;

import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.service.ClientService;
import com.cbtest.service.factories.ClientServiceFactory;
import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.MainState;

public class CreateClientState implements ConsoleState {

    private final ConsoleManager consoleManager;
    private final ClientService clientService;
    private ConsoleState nextState;

    public CreateClientState() {
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
        this.clientService = ClientServiceFactory.getClientService();
    }


    @Override
    public void run() throws Exception {
        String firstName;
        do {
            System.out.println("Введите имя:");
            firstName = consoleManager.readLine();
        } while (firstName.isBlank()); //TODO: сделать норм валидацию всех полей
        String lastName;
        do {
            System.out.println("Введите фамилию:");
            lastName = consoleManager.readLine();
        } while (lastName.isBlank());
        String middleName;
        do {
            System.out.println("Введите отчество:");
            middleName = consoleManager.readLine();
        } while (middleName.isBlank());
        String phoneNumber;
        do {
            System.out.println("Введите номер телефона:");
            phoneNumber = consoleManager.readLine();
        } while (phoneNumber.isBlank());
        String inn;
        do {
            System.out.println("Введите инн:");
            inn = consoleManager.readLine();
        } while (inn.isBlank());
        String address;
        do {
            System.out.println("Введите адрес:");
            address = consoleManager.readLine();
        } while (address.isBlank());

        try {
            SignUpForm signUpForm = SignUpForm.builder()
                   .fullName(firstName + " " + lastName + " " + middleName)
                   .number(phoneNumber)
                   .inn(inn)
                   .address(address)
                   .build();
            clientService.signUp(signUpForm);
            System.out.println("Клиент " + signUpForm.getFullName() + " успешно создан! Нажмите 'Enter' для возврата в главное меню");
        } catch (ClientExistException e) {
            System.out.println(e.getMessage() + "Нажмите 'Enter' для возврата в главное меню");
        } finally {
            consoleManager.readLine();
            consoleManager.clear();
            nextState = new MainState();

        }

    }



    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
