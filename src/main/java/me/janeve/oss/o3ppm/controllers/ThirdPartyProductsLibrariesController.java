package me.janeve.oss.o3ppm.controllers;

import me.janeve.oss.o3ppm.entities.Library;
import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.LibraryVersion;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/3pp_libraries")
public class ThirdPartyProductsLibrariesController extends BaseController {

    @GetMapping("/new")
    public String newLibraryForm() {
        return "3pp_libraries/new";
    }

    @GetMapping("/")
    public String detailsView(Model model) {
        model.addAttribute("libraries", libraryRepository.findAll());
        return "3pp_libraries/details";
    }

    @PostMapping("/new/standalone")
    public String create3PPLibrary(@Validated LibraryVersion standaloneDependency) {
        logger.info("Standalone Dependency: " + standaloneDependency);
        Library library = standaloneDependency.getLibrary();
        library = libraryRepository.save(library);
        standaloneDependency.setLibrary(library);
        libraryVersionRepository.save(standaloneDependency);
        return "redirect:/3pp_libraries/";
    }

    @PostMapping("/new")
    public String create3PPLibrary(@RequestParam String projectId, @RequestParam String version, @Validated() LibraryVersion dependency) {

        logger.info("Dependency: " + dependency);

        if(dependency.getLibrary().getName().trim().isEmpty() || dependency.getLibraryVersion().trim().isEmpty()) {
            throw new RuntimeException("Invalid library name or version.");
        }

        Project project = findProject(projectId);
        ProjectRelease projectRelease = getProjectRelease(project, version);

        logger.info("New Library Dependency " + dependency);
        List<LibraryVersion> projectReleaseDependencies = projectRelease.getDependencies();
        if(projectReleaseDependencies == null) {
            projectReleaseDependencies = new ArrayList<>();
        }

        Library library = dependency.getLibrary();
        library = libraryRepository.save(library);
        dependency.setLibrary(library);

        dependency = libraryVersionRepository.insert(dependency);

        projectReleaseDependencies.add(dependency);
        projectRelease.setDependencies(projectReleaseDependencies);

        projectRepository.save(project);

        return "redirect:/projects/" + projectId + "/releases/" + version;
    }

}