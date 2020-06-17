package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
public class SoftwareData {
    private String vendor;
    private String downloadUrl;
    @NotNull private SoftwareType softwareType;
    private String platform;
}