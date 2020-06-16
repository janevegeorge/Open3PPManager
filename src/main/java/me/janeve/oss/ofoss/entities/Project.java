package me.janeve.oss.ofoss.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@Document(collection = "projects")
public class Project {
    @Id @ToString.Include private String id;
    @ToString.Include private String name;
    @DBRef @ToString.Include private User owner;
    private List<ProjectReleases> releases;
    private String description;
}