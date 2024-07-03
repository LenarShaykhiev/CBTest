package com.cbtest.repository.memory.account;

import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.models.Account;
import com.cbtest.repository.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
    private final Map<String, Account> memory = new HashMap<>();

    @Override
    public Account saveAccount(Account account) {
        return memory.put(account.getAccountNumber(), account);
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return Optional.ofNullable(memory.getOrDefault(accountNumber, null));
    }

    @Override
    public Optional<List<Account>> getAccountsByClientNumber(String clientNumber) {
        return Optional.of(memory.values().stream().filter(acc -> acc.getClient()
                        .getNumber().equals(clientNumber)).filter(Account::getIsValid)
                .collect(Collectors.toList()));
    }


    @Override
    public Account deleteAccountByAccountNumber(String accountNumber) throws AccountNotExistsException {
        Account account = memory.getOrDefault(accountNumber, null);
        if (account != null && account.getIsValid()) {
            account.setIsValid(false);
        } else {
            throw new AccountNotExistsException("Счет не существует или уже был удален");
        }
        return account;
    }

    @Override
    public Optional<List<Account>> getAccountsByIsValid() {
        return Optional.of(memory.values().stream().filter(Account::getIsValid).toList());
    }
}
