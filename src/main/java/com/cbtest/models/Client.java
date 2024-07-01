package com.cbtest.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Client {
    private String fullName;
    private String number;
    private String inn;
    private String address;
    @ToString.Exclude
    private List<Account> accounts;
}
