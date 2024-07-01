package com.cbtest.dto;

import com.cbtest.models.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SignUpForm {
    private String fullName;
    private String number;
    private String inn;
    private String address;
}
