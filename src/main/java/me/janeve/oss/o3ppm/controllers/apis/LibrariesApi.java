package me.janeve.oss.o3ppm.controllers.apis;

import me.janeve.oss.o3ppm.controllers.BaseController;
import me.janeve.oss.o3ppm.entities.Library;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibrariesApi extends BaseController {

    @GetMapping("")
    public List<Library> findLibraryByNamePattern(@RequestParam String namePattern){
        return libraryRepository.findByNameLikeIgnoreCase(namePattern);
    }

}