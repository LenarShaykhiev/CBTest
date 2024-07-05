package com.cbtest.service;

import com.cbtest.dto.AccountDto;
import com.cbtest.exceptions.account.AccountExistsException;
import com.cbtest.exceptions.account.AccountNotExistsException;
import com.cbtest.exceptions.clent.ClientNotExistException;
import com.cbtest.models.Account;
import com.cbtest.models.Client;

import java.util.List;

public interface AccountService {
    Account addAccount(AccountDto account) throws AccountExistsException, ClientNotExistException;
    AccountDto closeAccount(AccountDto account) throws AccountNotExistsException;
    List<AccountDto> getAllValidAccounts() throws AccountNotExistsException;

    List<AccountDto> getAccountsByClient(Client client) throws AccountNotExistsException;
}
