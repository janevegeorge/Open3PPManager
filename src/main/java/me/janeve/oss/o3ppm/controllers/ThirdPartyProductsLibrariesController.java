package me.janeve.oss.o3ppm.controllers;

import me.janeve.oss.o3ppm.entities.Library;
import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/3pp_libraries")
public class ThirdPartyProductsLibrariesController extends BaseController {

    @GetMapping("/new")
    public String newLibraryForm() {
        return "3pp_libraries/new";
    }

    @PostMapping("/new")
    public String create3PPLibrary(@RequestParam String projectId, @RequestParam String version, Library library) {
        Project project = findProject(projectId);
        ProjectRelease projectRelease = getProjectRelease(project, version);

        logger.info("New Library " + library);
        List<Library> projectReleaseLibraries = projectRelease.getLibraries();
        if(projectReleaseLibraries == null) {
            projectReleaseLibraries = new ArrayList<>();
        }

        library = libraryRepository.save(library);
        projectReleaseLibraries.add(library);
        projectRelease.setLibraries(projectReleaseLibraries);

        projectRepository.save(project);

        return "redirect:/projects/" + projectId + "/releases/" + version;
    }

}