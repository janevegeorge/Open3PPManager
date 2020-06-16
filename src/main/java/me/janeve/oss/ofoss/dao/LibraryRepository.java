package me.janeve.oss.ofoss.dao;

import me.janeve.oss.ofoss.entities.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryRepository extends MongoRepository<Library, String> {
}