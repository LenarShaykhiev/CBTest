package com.cbtest.service.impl;

import com.cbtest.dto.AccountDto;
import com.cbtest.dto.ClientDto;
import com.cbtest.exceptions.account.AccountExistsException;
import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.exceptions.clent.ClientNotExistException;
import com.cbtest.models.Account;
import com.cbtest.models.Client;
import com.cbtest.repository.AccountRepository;
import com.cbtest.repository.memory.account.AccountRepositoryFactory;
import com.cbtest.service.AccountService;
import com.cbtest.service.ClientService;
import com.cbtest.service.factories.ClientServiceFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.cbtest.dto.AccountDto.from;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientService clientService;

    public AccountServiceImpl() {
        this.accountRepository = AccountRepositoryFactory.getAccountRepository();
        this.clientService = ClientServiceFactory.getClientService();
    }


    @Override
    public Account addAccount(AccountDto accountDto) throws AccountExistsException, ClientNotExistException {
        Account account = Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .bik(accountDto.getBik())
                .client(accountDto.getClient())
                .balance(BigDecimal.valueOf(0))
                .currency(Account.Currency.valueOf(accountDto.getCurrency().toUpperCase()))
                .isValid(true)
                .build();
        addAccountValidation(account);
        Client client = account.getClient();
        client.getAccounts().add(account);
        clientService.updateClient(ClientDto.from(client), client.getNumber());
        return accountRepository.saveAccount(account);
    }

    @Override
    public AccountDto closeAccount(AccountDto account) throws AccountNotExistsException {
        Optional<Account> accountOpt = accountRepository.getAccountByAccountNumber(account.getAccountNumber());
        if (accountOpt.isPresent()) {
            Account accountToUpdate = accountOpt.get();
            accountToUpdate.setIsValid(false);
            return from(accountRepository.saveAccount(accountToUpdate));
        } else {
            throw new AccountNotExistsException("Аккаунт с таким номером не найден!");
        }
    }

    @Override
    public List<AccountDto> getAllValidAccounts() throws AccountNotExistsException {
        Optional<List<Account>> accountsOpt = accountRepository.getAccountsByIsValid();
        if (accountsOpt.isPresent() && !accountsOpt.get().isEmpty()) {
            return from(accountsOpt.get());
        } else {
            throw new AccountNotExistsException("Открытых счетов не найдено!");
        }
    }

    @Override
    public List<AccountDto> getAccountsByClient(Client client) throws AccountNotExistsException {
        Optional<List<Account>> accountsOpt = accountRepository.getAccountsByClientNumber(client.getNumber());
        if (accountsOpt.isPresent() && !accountsOpt.get().isEmpty()) {
            return from(accountsOpt.get());
        } else {
            throw new AccountNotExistsException("У данного клиента нет открытого счета!");
        }
    }

    private void addAccountValidation(Account account) throws AccountExistsException {
        if (accountRepository.getAccountByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new AccountExistsException("Счет с номером " + account.getAccountNumber() + " уже существует!");
        }
    }
}
