package com.myfp.fp.entities;

public enum Role {
    USER("user"),
    ADMIN("admin"),
    MAIN_ADMIN("main_admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }

    public static Role fromString(String value) {
        for (Role role :
                Role.values()) {
            if (role.name.equals(value))
                return role;
        }
        return null;
    }
}
