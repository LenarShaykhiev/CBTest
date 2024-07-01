package com.cbtest.service.factories;

import com.cbtest.repository.memory.account.AccountRepositoryFactory;
import com.cbtest.service.AccountService;
import com.cbtest.service.impl.AccountServiceImpl;

import java.security.Provider;

public class AccountServiceFactory {
    private static AccountService accountService;

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountServiceImpl();
        }
        return accountService;
    }
}
