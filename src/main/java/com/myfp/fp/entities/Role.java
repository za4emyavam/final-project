package com.myfp.fp.entities;

public enum Role {
    USER("role.user"),
    MANAGER("role.manager"),
    ADMINISTRATOR("role.admin");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }
}
