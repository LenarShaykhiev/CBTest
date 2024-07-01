package com.cbtest.models;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {
    private BigDecimal sum;
    private Account from;
    private Account to;
}
