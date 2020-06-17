package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "3pp_libraries")
public class Library {
    @Id @ToString.Include private String id;
    @NonNull @ToString.Include private String name;
    private SoftwareData softwareData;
    private TradeCompliance tradeCompliance;
}