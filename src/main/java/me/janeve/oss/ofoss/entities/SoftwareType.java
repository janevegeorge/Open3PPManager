package me.janeve.oss.ofoss.entities;

import lombok.Getter;

@Getter
public enum SoftwareType {
    FOSS("FOSS"),
    COMMERCIAL("Commercial"),
    INTERNAL("Internal"),
    UNKNOWN("Others");

    private final String displayName;

    SoftwareType(String displayName) {
        this.displayName = displayName;
    }
}