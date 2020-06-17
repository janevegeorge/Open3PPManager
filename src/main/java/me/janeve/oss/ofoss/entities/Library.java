package me.janeve.oss.ofoss.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@ToString
@Document(collection = "3pp_libraries")
public class Library {
    @Id @ToString.Include private String id;
    private String name;
    private SoftwareData softwareData;
}