package com.cbtest.service.factories;

import com.cbtest.service.TransactionService;
import com.cbtest.service.impl.TransactionServiceImpl;

public class TransactionServiceFactory {

    private static TransactionService transactionService;

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionServiceImpl();
        }
        return transactionService;
    }
}
