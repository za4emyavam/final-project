package com.myfp.fp.entities;

public enum TariffStatus {
    DISABLED("disabled"),
    ACTIVE("active");

    private String name;

    TariffStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TariffStatus fromString(String value) {
        for (TariffStatus status :
                TariffStatus.values()) {
            if (status.name.equals(value))
                return status;
        }
        return null;
    }
}
