package com.cbtest.service;

import com.cbtest.exceptions.transaction.OutOfMoney;
import com.cbtest.models.Transaction;

public interface TransactionService {
    void send(Transaction transaction) throws OutOfMoney;
    void debit(Transaction transaction);
}
