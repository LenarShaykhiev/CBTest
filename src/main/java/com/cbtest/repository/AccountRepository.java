package com.cbtest.repository;

import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> getAccountByAccountNumber(String accountNumber);
    Optional<List<Account>> getAccountsByClientNumber(String clientNumber);
    Account deleteAccountByAccountNumber(String accountNumber) throws AccountNotExistsException;
    Optional<List<Account>> getAccountsByIsValid();
}
