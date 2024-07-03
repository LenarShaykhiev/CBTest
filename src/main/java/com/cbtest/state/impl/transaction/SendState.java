package com.cbtest.state.impl.transaction;

import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.exceptions.transaction.OutOfMoney;
import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.models.Account;
import com.cbtest.models.Transaction;
import com.cbtest.service.AccountService;
import com.cbtest.service.TransactionService;
import com.cbtest.service.factories.AccountServiceFactory;
import com.cbtest.service.factories.TransactionServiceFactory;
import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.MainState;

import java.math.BigDecimal;
import java.util.List;

public class SendState implements ConsoleState {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ConsoleManager consoleManager;
    private ConsoleState nextState;
    private Account sender;
    private Account receiver;
    public SendState() {
        this.accountService = AccountServiceFactory.getAccountService();
        this.transactionService = TransactionServiceFactory.getTransactionService();
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
    }


    @Override
    public void run() throws Exception {
        try {
            List<Account> accounts = accountService.getAllValidAccounts();
            System.out.println("Выберите счет отправителя (введите номер счета, с которого отправятся деньги):");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < accounts.size(); i++) {
                sb.append(i + 1).append(" - ").append(accounts.get(i).getAccountNumber()).append("\n");
            }
            System.out.print(sb);

            int number;
            do {
                System.out.println("Введите порядковый номер счета:");
                number = Integer.parseInt(consoleManager.readLine());
            } while (number < 1 || number > accounts.size());

            sender = accounts.get(number - 1);
            System.out.println("Счет-отправитель - " + sender.getAccountNumber());

            int sum; // TODO: оптимизировать под BigDecimal
            do {
                System.out.println("Введите сумму перевода:");
                sum = Integer.parseInt(consoleManager.readLine());
            } while (sum <= 0);

            consoleManager.clear();

            System.out.println("Выберите счет получателя (введите номер счета, на который отправятся деньги):");
            System.out.println(sb);

            int numberRes;
            do {
                System.out.println("Введите порядковый номер счета:");
                numberRes = Integer.parseInt(consoleManager.readLine());
                if (numberRes == number) {
                    throw new IllegalArgumentException("Нельзя перевести деньги на сам себя!");
                }
            } while (numberRes < 1 || numberRes > accounts.size());

            Transaction transaction = Transaction.builder()
                    .from(sender)
                    .to(receiver)
                    .sum(BigDecimal.valueOf(sum))
                    .build();

            transactionService.send(transaction);
            System.out.println("Перевод успешно выполнен. Нажмите 'Enter' для возврата в главное меню");
//        } catch (AccountNotExistsException e) {
//            throw new AccountNotExistsException("Нет таких");
        } catch (IllegalArgumentException | OutOfMoney e) {
            System.out.println(e.getMessage());
        } finally {
            consoleManager.clear();
            nextState = new MainState();
        }


    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
