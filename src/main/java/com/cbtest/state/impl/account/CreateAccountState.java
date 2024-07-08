package com.cbtest.state.impl.account;

import com.cbtest.dto.AccountDto;
import com.cbtest.exceptions.account.AccountExistsException;
import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.models.Account;
import com.cbtest.models.Client;
import com.cbtest.service.AccountService;
import com.cbtest.service.ClientService;
import com.cbtest.service.factories.AccountServiceFactory;
import com.cbtest.service.factories.ClientServiceFactory;
import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.MainState;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static com.cbtest.mapper.FromDtoMapper.clientFromDto;

public class CreateAccountState implements ConsoleState {

    private final AccountService accountService;
    private final ClientService clientService;
    private final ConsoleManager consoleManager;
    private ConsoleState nextState;

    private Client client;

    public CreateAccountState() {
        this.accountService = AccountServiceFactory.getAccountService();
        this.clientService = ClientServiceFactory.getClientService();
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
    }

    @Override
    public void run() throws Exception {
        try {
            List<Client> clients = clientFromDto(clientService.getAllClients());
            System.out.println("Выберите клиента (введите номер клиента, которому нужно создать счет):");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < clients.size(); i++) {
                sb.append(i + 1).append(" - ").append(clients.get(i).getFullName()).append("\n");
            }
            System.out.print(sb);

            int number;
            do {
                System.out.println("Введите номер клиента:");
                number = Integer.parseInt(consoleManager.readLine());
            } while (number < 1 || number > clients.size());

            client = clients.get(number - 1);
            System.out.println("Клиент - " + client.getFullName());
        } catch (NoSuchElementException e) {
            System.out.println("Клиенты не найдены. Нажмите 'Enter' для возврата в главное меню");
        } finally {
            consoleManager.readLine();
            consoleManager.clear();
            nextState = new MainState();
        }

        String accountNumber;
        do {
            System.out.println("Введите номер счета:");
            accountNumber = consoleManager.readLine();
        } while (accountNumber.isBlank());

        String bik;
        do {
            System.out.println("Введите бик:");
            bik = consoleManager.readLine();
        } while (bik.isBlank());

        String currency;
        do {
            System.out.println("Введите валюту 'RUB', 'USD' или 'EUR':");
            currency = consoleManager.readLine();
        } while (currency.isBlank());

        try {
            Account accountRes = Account.builder()
                    .accountNumber(accountNumber)
                    .bik(bik)
                    .currency(Account.Currency.valueOf(currency.toUpperCase()))
                    .balance(BigDecimal.valueOf(0))
                    .client(client)
                    .isValid(true)
                    .build();
            accountService.addAccount(AccountDto.from(accountRes));
            System.out.println("Счет с номером " + accountNumber + " успеешно создан! Нажмите 'Enter' для возврата в главное меню");
        } catch (AccountExistsException e) {
            System.out.println(e.getMessage() + ". Нажмите 'Enter' для возврата в главное меню");
        } catch (IllegalArgumentException e) {
            System.out.println("Введен неверный валютный код. Нажмите 'Enter' для возврата в главное меню");
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
