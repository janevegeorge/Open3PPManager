package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.LibraryVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryVersionRepository extends MongoRepository<LibraryVersion, String> {
}