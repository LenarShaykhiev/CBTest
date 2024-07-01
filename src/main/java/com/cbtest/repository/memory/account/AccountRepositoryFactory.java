package com.cbtest.repository.memory.account;

import com.cbtest.repository.AccountRepository;

public class AccountRepositoryFactory {
    private static AccountRepository accountRepository;

    public static AccountRepository getAccountRepository() {
        if (accountRepository == null) {
            accountRepository = new AccountRepositoryImpl();
        }
        return accountRepository;
    }
}
