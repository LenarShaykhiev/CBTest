package com.cbtest.models;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {

    public enum Currency {
        RUB, USD, EUR
    }

    private String accountNumber;
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0);
    private String bik;
    private Currency currency;
    private Client client;
    @Builder.Default
    private Boolean isValid = true;
}
