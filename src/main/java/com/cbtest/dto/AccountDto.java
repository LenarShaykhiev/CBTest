package com.cbtest.dto;

import com.cbtest.models.Account;
import com.cbtest.models.Client;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AccountDto {
    private String accountNumber;
    private BigDecimal balance;
    private String bik;
    private String currency;
    private Client client;
    private Boolean isValid;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
               .accountNumber(account.getAccountNumber())
               .balance(account.getBalance())
               .bik(account.getBik())
               .currency(String.valueOf(account.getCurrency()).toUpperCase())
               .client(account.getClient())
                .isValid(account.getIsValid())
               .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream()
               .map(AccountDto::from)
               .collect(Collectors.toList());
    }
}
