package me.janeve.oss.o3ppm.controllers;

import me.janeve.oss.o3ppm.entities.Library;
import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.ProjectLibraryDependency;
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
    public String create3PPLibrary(@RequestParam String projectId, @RequestParam String version, ProjectLibraryDependency dependency) {

        logger.info("Dependency: " + dependency);

        if(dependency.getLibrary().getName().trim().isEmpty() || dependency.getLibraryVersion().trim().isEmpty()) {
            throw new RuntimeException("Invalid library name or version.");
        }

        Project project = findProject(projectId);
        ProjectRelease projectRelease = getProjectRelease(project, version);

        logger.info("New Library Dependency " + dependency);
        List<ProjectLibraryDependency> projectReleaseDependencies = projectRelease.getDependencies();
        if(projectReleaseDependencies == null) {
            projectReleaseDependencies = new ArrayList<>();
        }

        Library library = dependency.getLibrary();
        library = libraryRepository.save(library);
        dependency.setLibrary(library);

        dependency = projectDependencyRepository.insert(dependency);

        projectReleaseDependencies.add(dependency);
        projectRelease.setDependencies(projectReleaseDependencies);

        projectRepository.save(project);

        return "redirect:/projects/" + projectId + "/releases/" + version;
    }

}