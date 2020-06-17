package me.janeve.oss.o3ppm.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter @Setter
@ToString
@RequiredArgsConstructor
public class ProjectRelease {
    @NonNull private String version;
    private String baseVersion;
    @DBRef private List<LibraryVersion> dependencies;
}