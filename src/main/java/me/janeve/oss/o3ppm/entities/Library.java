package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
@ToString
@Document(collection = "3pp_libraries")
public class Library {
    @Id private String id;
    @NotBlank private String name;
    @DBRef List<LibraryVersion> versions;
    @Valid private SoftwareData softwareData;
    @Valid private TradeCompliance tradeCompliance;
}