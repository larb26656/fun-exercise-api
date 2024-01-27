package com.javabootcamp.fintechbank.accounts.models;

public enum AccountType {
    SAVING("SAVING"),
    CHECKING("CHECKING"),
    CURRENT("CURRENT");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
