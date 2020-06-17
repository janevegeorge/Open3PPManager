package me.janeve.oss.ofoss.controllers;

import me.janeve.oss.ofoss.dao.ProjectRepository;
import me.janeve.oss.ofoss.dao.UserRepository;
import me.janeve.oss.ofoss.entities.Project;
import me.janeve.oss.ofoss.entities.ProjectRelease;
import me.janeve.oss.ofoss.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/new")
    public String createNewProject(Model model, Project project) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            User authenticatedUser = userRepository.findByUsername(auth.getName());
            if(authenticatedUser == null) {
                throw new RuntimeException("Unauthorized access.");
            }
            project.setOwner(authenticatedUser);
            logger.info("Adding : " + project.toString());
            repository.insert(project);
        } else {
            logger.severe("User not authenticated.");
        }
        return homePage(model);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("projects", repository.findAll());
        return "projects/list";
    }

    @GetMapping("/{id}")
    public String getProjectDetails(Model model, @PathVariable String id) {
        Optional<Project> entry = repository.findById(id);
        entry.ifPresent(project -> model.addAttribute("project", project));
        return "projects/details";
    }

    @PostMapping("/{projectId}/releases/new")
    public String createNewProjectRelease(Model model, @PathVariable String projectId, ProjectRelease release) {
        Project project = findProject(projectId);
        List<ProjectRelease> releases = project.getReleases();
        if(releases == null) {
            releases = new ArrayList<>();
        }
        releases.add(release);
        project.setReleases(releases);

        logger.info("Adding release " + release + " to propject " + projectId);
        repository.save(project);

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{projectId}/releases/{version}")
    public String createNewProjectRelease(Model model, @PathVariable String projectId, @PathVariable String version) {
        Project project = findProject(projectId);
        List<ProjectRelease> releases = project.getReleases();
        if(releases == null) {
            releases = new ArrayList<>();
        }

        ProjectRelease projectRelease = null;
        for(ProjectRelease release: releases){
            if(release.getVersion().equalsIgnoreCase(version)) {
                projectRelease = release;
                break;
            }
        }

        if(projectRelease == null) {
            throw new RuntimeException("Project Version - 404 Not Found!");
        }

        model.addAttribute("project", project);
        model.addAttribute("release", projectRelease);

        return "/project_releases/details";
    }

    private Project findProject(@PathVariable String projectId) {
        Optional<Project> queryResult = repository.findById(projectId);
        Project project;
        if(queryResult.isPresent()){
            project = queryResult.get();
        } else {
            throw new RuntimeException("Project - 404 Not Found");
        }
        return project;
    }

    @GetMapping("/new")
    public String newProjectForm() {
        return "projects/new";
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());

}