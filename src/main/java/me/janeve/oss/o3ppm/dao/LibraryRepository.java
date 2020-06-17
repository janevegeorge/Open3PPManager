package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryRepository extends MongoRepository<Library, String> {
}