package com.myfp.fp.entities;

public enum UserStatus {
    SUBSCRIBED("subscribed"),
    BLOCKED("blocked");

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }

    public static UserStatus fromString(String value) {
        for (UserStatus status :
                UserStatus.values()) {
            if (status.name.equals(value))
                return status;
        }
        return null;
    }
}
