package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@Document(collection = "3pp_libraries")
public class Library {
    @Id @ToString.Include private String id;
    @NotBlank @ToString.Include private String name;
    @Valid private SoftwareData softwareData;
    @Valid private TradeCompliance tradeCompliance;
}