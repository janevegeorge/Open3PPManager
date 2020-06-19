package me.janeve.oss.o3ppm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
@Document(collection = "users")
public class User {
    @Id private String id;
    @NotBlank private String username;
    private String fname;
    private String lname;
    @NotBlank @ToString.Exclude private String password;
    private String[] authorities;
}