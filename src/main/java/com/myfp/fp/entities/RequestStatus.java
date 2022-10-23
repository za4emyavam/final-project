package com.myfp.fp.entities;

public enum RequestStatus {
    IN_PROCESSING("in processing"),
    REJECTED("rejected"),
    APPROVED("approved");

    private String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RequestStatus fromString(String value) {
        for (RequestStatus status :
                RequestStatus.values()) {
            if (status.value.equals(value))
                return status;
        }
        return null;
    }
}
