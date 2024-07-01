package com.cbtest.repository;

import com.cbtest.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client createClient(Client client);
    Optional<Client> getClientByNumber(String number);
    Optional<List<Client>> findAll();
    Optional<List<Client>> getAllByAccountsNotEmpty();
    Optional<List<Client>> getAllClients();

}
