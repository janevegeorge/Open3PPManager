package me.janeve.oss.o3ppm.dao;

import me.janeve.oss.o3ppm.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}