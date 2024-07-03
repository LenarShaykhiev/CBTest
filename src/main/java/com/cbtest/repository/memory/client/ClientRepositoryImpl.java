package com.cbtest.repository.memory.client;

import com.cbtest.models.Client;
import com.cbtest.repository.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<String, Client> memory = new HashMap<>();

    @Override
    public Client createClient(Client client) {
        return memory.put(client.getNumber(), client);
    }

    @Override
    public Optional<Client> getClientByNumber(String number) {
        return Optional.ofNullable(memory.get(number));
    }

    @Override
    public Optional<List<Client>> findAll() {
        if (memory.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ArrayList<>(memory.values()));
    }

    @Override
    public Optional<List<Client>> getAllByAccountsNotEmpty() {
        return Optional.of(memory.values()
                .stream()
                .filter(client -> !client.getAccounts().isEmpty())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<Client> updateClient(Client client, String number) {
        memory.put(number, client);
        return Optional.ofNullable(memory.get(number));
    }

    @Override
    public Optional<List<Client>> getAllClients() {
        return Optional.of(new ArrayList<>(memory.values()));
    }
}
