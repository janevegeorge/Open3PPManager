package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.Library;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LibraryRepository extends MongoRepository<Library, String> {
    List<Library> findByNameLikeIgnoreCase(String namePattern);
}