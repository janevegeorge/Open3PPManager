package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
public class SoftwareData {
    private String vendor;
    @Pattern(regexp = "^((https?|ftp)://[^\\s/$.?#].[^\\s]*)?$") private String downloadUrl;
    @NotNull private SoftwareType softwareType;
    private String platform;
}