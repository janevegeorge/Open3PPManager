package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.Library;
import me.janeve.oss.o3ppm.entities.LibraryVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LibraryVersionRepository extends MongoRepository<LibraryVersion, String> {
    @Query(fields="{ library : 0 }")
    List<LibraryVersion> findByLibraryId(String libraryId);
}