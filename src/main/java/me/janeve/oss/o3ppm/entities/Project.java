package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@Document(collection = "projects")
public class Project {
    @Id @ToString.Include private String id;
    @NotBlank @ToString.Include private String name;
    @DBRef @ToString.Include private User owner;
    private List<ProjectRelease> releases;
    private String description;
}