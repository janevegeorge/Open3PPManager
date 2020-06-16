package me.janeve.oss.ofoss.dao;

import me.janeve.oss.ofoss.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}