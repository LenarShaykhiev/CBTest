package com.cbtest.service.impl;

import com.cbtest.dto.ClientDto;
import com.cbtest.dto.SignUpForm;
import com.cbtest.exceptions.clent.ClientExistException;
import com.cbtest.exceptions.clent.ClientNotExistException;
import com.cbtest.models.Account;
import com.cbtest.models.Client;
import com.cbtest.repository.ClientRepository;
import com.cbtest.repository.memory.client.ClientRepositoryFactory;
import com.cbtest.service.ClientService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.cbtest.dto.ClientDto.from;
import static com.cbtest.mapper.FromDtoMapper.clientFromDto;

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
    public List<ClientDto> getAllClients() throws ClientNotExistException {
        if (clientRepository.getAllClients().isPresent() && !clientRepository.getAllClients().get().isEmpty()) {
            return from(clientRepository.getAllClients().get());
        } else {
            throw new ClientNotExistException("Нет созданных клиентов!");
        }

    }

    @Override
    public List<ClientDto> getAllClientsByAccountIsExists() throws NoSuchElementException, ClientExistException {
        if (clientRepository.getAllByAccountsNotEmpty().isPresent() && !clientRepository.getAllByAccountsNotEmpty().get().isEmpty()) {
            return from(clientRepository.getAllByAccountsNotEmpty().get());
        } else {
            throw new ClientExistException("Нет созданных клиентов");
        }
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto, String number) throws ClientNotExistException {
        Client client = clientFromDto(clientDto);
        clientRepository.updateClient(client, number);
        return from(client);
    }


    private void signUpValidation(Client client) throws ClientExistException {
        if (clientRepository.getClientByNumber(client.getInn()).isPresent()) {
            throw new ClientExistException("Клиент с ИНН " + client.getInn() + " уже существует!");
        }
    }
}
