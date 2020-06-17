package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@ToString
@Document(collection = "library_version")
public class LibraryVersion {
    @Id private String id;
    @DBRef Library library;
    @NonNull String libraryVersion;
}