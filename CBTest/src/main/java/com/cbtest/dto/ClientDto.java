package com.cbtest.dto;

import com.cbtest.models.Account;
import com.cbtest.models.Client;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ClientDto {
    private String fullName;
    private String number;
    private String inn;
    private String address;
    @ToString.Exclude
    private List<Account> accounts;

    public static ClientDto from(Client client) {
        return ClientDto.builder()
               .fullName(client.getFullName())
               .number(client.getNumber())
               .inn(client.getInn())
               .address(client.getAddress())
               .accounts(client.getAccounts())
               .build();
    }

    public static List<ClientDto> from(List<Client> clients) {
        return clients.stream()
               .map(ClientDto::from)
               .collect(Collectors.toList());
    }
}
