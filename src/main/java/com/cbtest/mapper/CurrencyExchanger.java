package com.cbtest.mapper;

import com.cbtest.models.Account;

import java.math.BigDecimal;

import static com.cbtest.costants.CurrencyConstants.*;

public class CurrencyExchanger {
    public static BigDecimal exchange(BigDecimal sum, Account.Currency sender, Account.Currency receiver) {
        BigDecimal exchangeRate = switch (sender) {
            case RUB -> switch (receiver) {
                case USD -> RUB_TO_USD;
                case EUR -> RUB_TO_EUR;
                default -> throw new IllegalArgumentException("Неверная валюта!");
            };
            case USD -> switch (receiver) {
                case RUB -> USD_TO_RUB;
                case EUR -> USD_TO_EUR;
                default -> throw new IllegalArgumentException("Неверная валюта!");
            };
            case EUR -> switch (receiver) {
                case RUB -> EUR_TO_RUB;
                case USD -> EUR_TO_USD;
                default -> throw new IllegalArgumentException("Неверная валюта!");
            };
        };
        return sum.multiply(exchangeRate);
    }
}
