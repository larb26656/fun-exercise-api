package com.javabootcamp.fintechbank.accounts.models.deposit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositRequest(
        @NotNull
        @Positive
        Double amount
) {
}
