package me.janeve.oss.ofoss.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter @Setter
public class ProjectReleases {
    private String version;
    @DBRef private List<Library> libraries;
}