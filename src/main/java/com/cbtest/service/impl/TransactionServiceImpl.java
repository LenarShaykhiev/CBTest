package com.cbtest.service.impl;

import com.cbtest.exceptions.transaction.OutOfMoney;
import com.cbtest.models.Account;
import com.cbtest.models.Transaction;
import com.cbtest.service.TransactionService;

import java.math.BigDecimal;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public void send(Transaction transaction) throws OutOfMoney {

        Account from = transaction.getFrom();
        Account to = transaction.getTo();

        BigDecimal fromBalance = from.getBalance();
        BigDecimal toBalance = to.getBalance();

        if (fromBalance.compareTo(toBalance) > 0) {
            from.setBalance(from.getBalance().subtract(transaction.getSum()));
            to.setBalance(to.getBalance().add(transaction.getSum()));
        } else {
            throw new OutOfMoney("На балансе отправителя недостаточно денег!");
        } // TODO: rollback
    }

    @Override
    public void debit(Transaction transaction) {

        Account to = transaction.getTo();

        to.setBalance(to.getBalance().add(transaction.getSum()));
    }
}
