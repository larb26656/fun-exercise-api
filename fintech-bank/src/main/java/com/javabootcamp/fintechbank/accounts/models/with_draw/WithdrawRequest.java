package com.javabootcamp.fintechbank.accounts.models.with_draw;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WithdrawRequest (
        @NotNull
        @Positive
        Double amount
) {
}
