package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "users")
public class User {
    @Id private String id;
    private String username;
    private String fname;
    private String lname;
    @ToString.Exclude private String password;
    private String[] authorities;
}