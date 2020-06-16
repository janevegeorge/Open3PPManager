package me.janeve.oss.ofoss.controllers;

import me.janeve.oss.ofoss.dao.ProjectRepository;
import me.janeve.oss.ofoss.dao.UserRepository;
import me.janeve.oss.ofoss.entities.Project;
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

    @GetMapping("/new")
    public String newProjectForm() {
        return "projects/new";
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());

}