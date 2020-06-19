package me.janeve.oss.o3ppm.controllers.apis;

import me.janeve.oss.o3ppm.controllers.BaseController;
import me.janeve.oss.o3ppm.entities.LibraryVersion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryVersionsApi extends BaseController {

    @GetMapping("/{libraryId}/versions")
    public List<LibraryVersion> findLibraryVersions(@PathVariable String libraryId){
        return libraryVersionRepository.findByLibraryId(libraryId);
    }

}