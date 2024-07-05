package com.cbtest.state.impl.transaction;

import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.models.Account;
import com.cbtest.models.Transaction;
import com.cbtest.service.AccountService;
import com.cbtest.service.TransactionService;
import com.cbtest.service.factories.AccountServiceFactory;
import com.cbtest.service.factories.TransactionServiceFactory;
import com.cbtest.state.ConsoleState;

import java.math.BigDecimal;
import java.util.List;

import static com.cbtest.mapper.FromDtoMapper.accFromDto;

public class DepositState implements ConsoleState {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ConsoleManager consoleManager;
    private ConsoleState nextState;
    private Account receiver;

    public DepositState() {
        this.accountService = AccountServiceFactory.getAccountService();
        this.transactionService = TransactionServiceFactory.getTransactionService();
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
    }

    @Override
    public void run() throws Exception {

        List<Account> accounts = accFromDto(accountService.getAllValidAccounts());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < accounts.size(); i++) {
            sb.append(i + 1).append(" - ").append(accounts.get(i).
                    getAccountNumber()).append(" - ").append(accounts.get(i).getCurrency()).append("\n");
        }
        System.out.println("Выберите счет получателя (введите номер счета, на который отправятся деньги):");
        System.out.println(sb);

        int number;
        do {
            System.out.println("Введите порядковый номер счета:");
            number = Integer.parseInt(consoleManager.readLine());
        } while (number < 1 || number > accounts.size());

        receiver = accounts.get(number - 1);

        int sum; // TODO: оптимизировать под BigDecimal
        do {
            System.out.printf("Введите сумму пополнения в %s: \n", receiver.getCurrency());
            sum = Integer.parseInt(consoleManager.readLine());
        } while (sum <= 0);

        consoleManager.clear();

        Transaction transaction = Transaction.builder()
                .to(receiver)
                .sum(BigDecimal.valueOf(sum))
                .build();

        transactionService.debit(transaction);
    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
