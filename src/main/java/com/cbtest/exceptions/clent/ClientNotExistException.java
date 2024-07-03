package com.cbtest.exceptions.clent;

import com.cbtest.exceptions.BaseException;

public class ClientNotExistException extends BaseException {
    public ClientNotExistException(String message) {
        super(message);
    }
}
