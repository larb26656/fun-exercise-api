package com.javabootcamp.fintechbank.accounts.models.create_account;

import com.javabootcamp.fintechbank.accounts.models.AccountType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateAccountRequest(
        @NotNull
        AccountType type,

        @NotNull
        @NotEmpty
        String name,

        @NotNull
        @Positive
        Double balance
) {

}
