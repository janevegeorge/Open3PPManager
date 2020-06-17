package me.janeve.oss.ofoss.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SoftwareData {
    private String vendor;
    private String downloadUrl;
    private SoftwareType softwareType;
    private String softwareVersion;
    private String platform;
}