package com.cbtest.repository.memory.client;

import com.cbtest.repository.ClientRepository;

public class ClientRepositoryFactory {
    private static ClientRepository clientRepository;

    public static ClientRepository getClientRepository() {
        if (clientRepository == null) {
            clientRepository = new ClientRepositoryImpl();
        }
        return clientRepository;
    }
}
