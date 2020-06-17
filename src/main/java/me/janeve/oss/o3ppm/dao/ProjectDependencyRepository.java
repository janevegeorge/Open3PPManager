package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.Library;
import me.janeve.oss.o3ppm.entities.ProjectLibraryDependency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectDependencyRepository extends MongoRepository<ProjectLibraryDependency, String> {
}