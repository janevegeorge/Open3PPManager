package me.janeve.oss.ofoss.controllers;

import me.janeve.oss.ofoss.dao.ProjectRepository;
import me.janeve.oss.ofoss.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private ProjectRepository repository;

    @PostMapping("/new")
    public String createNewProject(Model model, Project project) {
        logger.info("Adding : " + project.toString());
        repository.insert(project);
        return homePage(model);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("projects", repository.findAll());
        return "projects/list";
    }

    @GetMapping("/new")
    public String newProjectForm() {
        return "projects/new";
    }

}