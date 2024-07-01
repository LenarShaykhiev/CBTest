package com.cbtest.service;

import com.cbtest.dto.AccountDto;
import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.account.AccountExistsException;
import com.cbtest.models.Account;
import com.cbtest.models.Client;

import java.util.List;

public interface AccountService {
    Account addAccount(AccountDto account) throws AccountExistsException;
    Account closeAccount(AccountDto account);
}
