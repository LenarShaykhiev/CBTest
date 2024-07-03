package com.cbtest.exceptions.transaction;

import com.cbtest.exceptions.BaseException;

public class OutOfMoney extends BaseException {
    public OutOfMoney(String message) {
        super(message);
    }
}
