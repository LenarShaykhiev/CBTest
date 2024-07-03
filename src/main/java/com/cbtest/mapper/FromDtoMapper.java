package com.cbtest.mapper;

import com.cbtest.dto.AccountDto;
import com.cbtest.dto.ClientDto;
import com.cbtest.models.Account;
import com.cbtest.models.Client;

import java.util.List;

public class FromDtoMapper {
    public static Account accFromDto (AccountDto accountDto) {
        return Account.builder()
                .client(accountDto.getClient())
                .accountNumber(accountDto.getAccountNumber())
                .balance(accountDto.getBalance())
                .bik(accountDto.getBik())
                .isValid(accountDto.getIsValid())
                .currency(Account.Currency.valueOf(accountDto.getCurrency().toUpperCase()))
                .build();
    }

    public static List<Account> accFromDto (List<AccountDto> accountDtoList) {
        return accountDtoList.stream().map(FromDtoMapper::accFromDto).toList();
    }

    public static Client clientFromDto (ClientDto clientDto) {
        return Client.builder()
                .inn(clientDto.getInn())
                .address(clientDto.getAddress())
                .number(clientDto.getNumber())
                .fullName(clientDto.getFullName())
                .accounts(clientDto.getAccounts())
                .build();
    }

    public static List<Client> clientFromDto (List<ClientDto> clientDtoList) {
        return clientDtoList.stream().map(FromDtoMapper::clientFromDto).toList();
    }
}
