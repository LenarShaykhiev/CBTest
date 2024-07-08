package com.cbtest.state.impl.account;

import com.cbtest.exceptions.account.AccountNotExistsException;
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

import java.util.List;
import java.util.NoSuchElementException;

import static com.cbtest.dto.AccountDto.from;
import static com.cbtest.mapper.FromDtoMapper.accFromDto;
import static com.cbtest.mapper.FromDtoMapper.clientFromDto;

public class CloseAccountState implements ConsoleState {

        private final AccountService accountService;
        private final ClientService clientService;
        private final ConsoleManager consoleManager;
        private ConsoleState nextState;
        private Client client;
        private Account account;

        public CloseAccountState() {
            this.accountService = AccountServiceFactory.getAccountService();
            this.clientService = ClientServiceFactory.getClientService();
            this.consoleManager = ConsoleManagerFactory.getConsoleManager();
        }
    @Override
    public void run() throws Exception {
            List<Client> clients = clientFromDto(clientService.getAllClientsByAccountIsExists());
            System.out.println("Выберите клиента (введите номер клиента, чей счет нужно закрыть):");
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

            List<Account> accounts = accFromDto(accountService.getAccountsByClient(client));
            System.out.println("Выберите счет для закрытия (введите номер счета):");
            sb = new StringBuilder();
            for (int i = 0; i < accounts.size(); i++) {
                sb.append(i + 1).append(" - ").append(accounts.get(i).getAccountNumber()).append("\n");
            }
            System.out.println(sb);

            number = 0;
            do {
                System.out.println("Введите порядковый номер счета:");
                number = Integer.parseInt(consoleManager.readLine());
            } while (number < 1 || number > accounts.size());

            account = accounts.get(number - 1);
            accountService.closeAccount(from(account));
            System.out.println("Аккаунт " + account.getAccountNumber() + "закрыт. Нажмите 'Enter' для возврата в главное меню.");

            consoleManager.readLine();
            consoleManager.clear();
            nextState = new MainState();
        }


    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
