package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@ToString
@Document(collection = "library_versions")
public class LibraryVersion {
    @Id private String id;
    @Valid @DBRef Library library;
    @NotEmpty String libraryVersion;
}