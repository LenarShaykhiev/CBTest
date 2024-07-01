package com.cbtest.service.factories;

import com.cbtest.service.ClientService;
import com.cbtest.service.impl.ClientServiceImpl;

public class ClientServiceFactory {
    private static ClientService clientService;

    public static ClientService getClientService() {
        if (clientService == null) {
            clientService = new ClientServiceImpl();
        }
        return clientService;
    }
}
