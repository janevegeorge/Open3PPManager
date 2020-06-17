package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SoftwareData {
    private String vendor;
    private String downloadUrl;
    private SoftwareType softwareType;
    private String platform;
}