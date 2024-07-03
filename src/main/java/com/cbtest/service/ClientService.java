package com.cbtest.service;

import com.cbtest.dto.ClientDto;
import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.exceptions.clent.ClientNotExistException;
import com.cbtest.models.Client;

import java.util.List;

public interface ClientService {
    Client signUp(SignUpForm form) throws ClientExistException;
    List<ClientDto> getAllClients() throws ClientNotExistException;
    List<ClientDto> getAllClientsByAccountIsExists() throws ClientExistException;
    ClientDto updateClient(ClientDto clientDto, String number) throws ClientNotExistException;
}
