package com.cbtest.service.impl;

import com.cbtest.dto.AccountDto;
import com.cbtest.exceptions.account.AccountExistsException;
import com.cbtest.models.Account;
import com.cbtest.repository.AccountRepository;
import com.cbtest.repository.memory.account.AccountRepositoryFactory;
import com.cbtest.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = AccountRepositoryFactory.getAccountRepository();
    }


    @Override
    public Account addAccount(AccountDto accountDto) throws AccountExistsException {
        Account account = Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .bik(accountDto.getBik())
                .client(accountDto.getClient())
                .balance(BigDecimal.valueOf(0))
                .currency(Account.Currency.valueOf(accountDto.getCurrency().toUpperCase()))
                .isValid(true)
                .build();
        addAccountValidation(account);
        return accountRepository.saveAccount(account);
    }

    @Override
    public Account closeAccount(AccountDto account) {
        Optional<Account> accountOpt = accountRepository.getAccountByAccountNumber(account.getAccountNumber());
        if (accountOpt.isPresent()) {
            Account accountToUpdate = accountOpt.get();
            accountToUpdate.setIsValid(false);
            return accountRepository.saveAccount(accountToUpdate);
        } else {
            throw new IllegalArgumentException("Аккаунт с таким номером не найден!");
        }
    }

    private void addAccountValidation(Account account) throws AccountExistsException {
        if (accountRepository.getAccountByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new AccountExistsException("Account with number " + account.getAccountNumber() + " already exists");
        }
    }
}
