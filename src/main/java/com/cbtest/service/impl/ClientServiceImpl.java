package com.cbtest.service.impl;

import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.exceptions.clent.ClientNotExistException;
import com.cbtest.models.Client;
import com.cbtest.repository.ClientRepository;
import com.cbtest.repository.memory.client.ClientRepositoryFactory;
import com.cbtest.service.ClientService;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl() {
        this.clientRepository = ClientRepositoryFactory.getClientRepository();
    }


    @Override
    public Client signUp(SignUpForm form) throws ClientExistException {
        Client client = Client.builder()
                .fullName(form.getFullName())
                .inn(form.getInn())
                .number(form.getNumber())
                .address(form.getAddress())
                .build();

        signUpValidation(client);
        return clientRepository.createClient(client);
    }

    @Override
    public List<Client> getAllClients() throws NoSuchElementException, ClientNotExistException {
        if (clientRepository.getAllClients().isPresent() && !clientRepository.getAllClients().get().isEmpty()) {
            return clientRepository.getAllClients().get();
        } else {
            throw new ClientNotExistException("Нет созданных клиентов!");
        }

    }

    @Override
    public List<Client> getAllClientsByAccountIsExists() throws NoSuchElementException, ClientExistException {
        return clientRepository.getAllByAccountsNotEmpty().orElseThrow(() -> new ClientExistException("Нет созданных клиентов"));
    }


    private void signUpValidation(Client client) throws ClientExistException {
        if (clientRepository.getClientByNumber(client.getInn()).isPresent()) {
            throw new ClientExistException("Клиент с ИНН " + client.getInn() + " уже существует!");
        }
    }
}
