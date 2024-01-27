package com.javabootcamp.fintechbank.accounts.models.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferMoneyRequest(
        @NotNull
        @Positive
        Double amount,

        String remark
) {
}
