package com.myfp.fp.entities;

public enum TransactionType {
    DEBIT("debit"),
    REFILL("refill");

    String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionType fromString(String value) {
        for (TransactionType status :
                TransactionType.values()) {
            if (status.value.equals(value))
                return status;
        }
        return null;
    }
}
