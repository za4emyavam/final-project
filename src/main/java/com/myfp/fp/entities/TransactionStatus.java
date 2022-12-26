package com.myfp.fp.entities;

public enum TransactionStatus {
    SUCCESSFUL("successful"),
    DENIED("denied");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionStatus fromString(String value) {
        for (TransactionStatus status :
                TransactionStatus.values()) {
            if (status.value.equals(value))
                return status;
        }
        return null;
    }
}
