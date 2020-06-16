package me.janeve.oss.ofoss.dao;

import me.janeve.oss.ofoss.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}