package com.cbtest.state.impl.transaction;

import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.exceptions.transaction.OutOfMoney;
import com.cbtest.in.ConsoleManager;
import com.cbtest.in.ConsoleManagerFactory;
import com.cbtest.mapper.CurrencyExchanger;
import com.cbtest.models.Account;
import com.cbtest.models.Transaction;
import com.cbtest.service.AccountService;
import com.cbtest.service.TransactionService;
import com.cbtest.service.factories.AccountServiceFactory;
import com.cbtest.service.factories.TransactionServiceFactory;
import com.cbtest.state.ConsoleState;
import com.cbtest.state.impl.MainState;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static com.cbtest.mapper.FromDtoMapper.accFromDto;

public class SendState implements ConsoleState {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ConsoleManager consoleManager;
    private ConsoleState nextState;

    public SendState() {
        this.accountService = AccountServiceFactory.getAccountService();
        this.transactionService = TransactionServiceFactory.getTransactionService();
        this.consoleManager = ConsoleManagerFactory.getConsoleManager();
    }


    @Override
    public void run() throws Exception {
//        try {
            List<Account> accounts = accFromDto(accountService.getAllValidAccounts());
            System.out.println("Выберите счет отправителя (введите номер счета, с которого отправятся деньги):");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < accounts.size(); i++) {
                sb.append(i + 1).append(" - ").append(accounts.get(i).getAccountNumber())
                        .append(" - ").append(accounts.get(i).getCurrency()).append("\n");
            }
            System.out.print(sb);

            int number;
            do {
                System.out.println("Введите порядковый номер счета:");
                number = Integer.parseInt(consoleManager.readLine());
            } while (number < 1 || number > accounts.size());

        Account sender = accounts.get(number - 1);
            System.out.println("Счет-отправитель - " + sender.getAccountNumber());

            BigDecimal sum; // TODO: оптимизировать под BigDecimal
            do {
                System.out.printf("Введите сумму перевода в %s \n", sender.getCurrency());
                sum = BigDecimal.valueOf(Integer.parseInt(consoleManager.readLine()));
            } while (sum.doubleValue() < 0 || sum.doubleValue() > sender.getBalance().doubleValue());

            consoleManager.clear();

            System.out.println("Выберите счет получателя (введите номер счета, на который отправятся деньги):");
            System.out.println(sb);

            int numberRes;
            Account receiver;
            do {
                System.out.println("Введите порядковый номер счета:");
                numberRes = Integer.parseInt(consoleManager.readLine());
                if (numberRes == number) {
                    throw new IllegalArgumentException("Нельзя перевести деньги со счета на тот же счет!");
                }
                receiver = accounts.get(numberRes - 1);
            } while (numberRes < 1 || numberRes > accounts.size());

            if (!sender.getCurrency().equals(receiver.getCurrency())) {
                Account.Currency senderCurrency = sender.getCurrency();
                Account.Currency receiverCurrency = receiver.getCurrency();

                sum = CurrencyExchanger.exchange(sum, senderCurrency, receiverCurrency);
            }

            Transaction transaction = Transaction.builder()
                    .from(sender)
                    .to(receiver)
                    .sum(sum)
                    .build();

            transactionService.send(transaction);
            System.out.println("Перевод успешно выполнен. Нажмите 'Enter' для возврата в главное меню");
//        } catch (AccountNotExistsException e) {
//            throw new AccountNotExistsException("Нет таких");
//        } catch (IllegalArgumentException | OutOfMoney e) {
//            System.out.println(e.getMessage());
//        } finally {
            consoleManager.readLine();
            consoleManager.clear();
            nextState = new MainState();
//        }


    }

    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
