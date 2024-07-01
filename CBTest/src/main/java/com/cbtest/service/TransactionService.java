package com.cbtest.service;

import com.cbtest.models.Transaction;

public interface TransactionService {
    void credit(Transaction transaction);
    void debit(Transaction transaction);
}
