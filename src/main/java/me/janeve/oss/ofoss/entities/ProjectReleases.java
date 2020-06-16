package me.janeve.oss.ofoss.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project_releases")
public class ProjectReleases {

    @Id
    private String id;

    @DBRef
    Project project;



}
