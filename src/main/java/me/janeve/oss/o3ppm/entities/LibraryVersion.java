package me.janeve.oss.o3ppm.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
@Document(collection = "library_versions")
public class LibraryVersion {
    @Id private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL) @Valid @DBRef Library library;
    @NotEmpty String version;
    @NotEmpty @Pattern(regexp = "^((https?|ftp)://[^\\s/$.?#].[^\\s]*)?$") private String downloadUrl;
}