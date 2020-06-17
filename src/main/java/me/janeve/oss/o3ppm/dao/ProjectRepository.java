package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}