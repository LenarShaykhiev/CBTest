package com.cbtest.exceptions.account;

import com.cbtest.exceptions.BaseException;

public class AccountNotExistsException extends BaseException {
    public AccountNotExistsException(String message) {
        super(message);
    }
}
