package com.cbtest.service.impl;

import com.cbtest.models.Account;
import com.cbtest.models.Transaction;
import com.cbtest.service.TransactionService;

import java.math.BigDecimal;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public void credit(Transaction transaction) {

        Account from = transaction.getFrom();
        Account to = transaction.getTo();

        BigDecimal fromBalance = from.getBalance();
        BigDecimal toBalance = to.getBalance();

        if (fromBalance.compareTo(toBalance) > 0) {
            from.setBalance(from.getBalance().subtract(transaction.getSum()));
            to.setBalance(to.getBalance().add(transaction.getSum()));
        } else {
            throw new IllegalArgumentException("На балансе отправителя недостаточно денег!");
        } // TODO: rollback
    }

    @Override
    public void debit(Transaction transaction) {

        Account to = transaction.getTo();

        to.setBalance(to.getBalance().add(transaction.getSum()));
    }
}
