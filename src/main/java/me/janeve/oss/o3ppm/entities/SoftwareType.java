package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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