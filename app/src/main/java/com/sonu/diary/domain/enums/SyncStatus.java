package com.sonu.diary.domain.enums;

public enum SyncStatus {
    P("Pending"),
    S("Send to Server"),
    C("Completed");

    private String description;

    SyncStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
