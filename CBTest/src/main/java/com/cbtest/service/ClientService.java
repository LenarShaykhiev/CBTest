package com.cbtest.service;

import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.models.Client;

import java.util.List;

public interface ClientService {
    Client signUp(SignUpForm form) throws ClientExistException;

    List<Client> getAllClients();
}
