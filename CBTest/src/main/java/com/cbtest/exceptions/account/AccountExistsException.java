package com.cbtest.exceptions.account;

import com.cbtest.exceptions.BaseException;

public class AccountExistsException extends BaseException {
    public AccountExistsException(String message) {
        super(message);
    }
}
