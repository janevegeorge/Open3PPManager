package me.janeve.oss.o3ppm.controllers;

import me.janeve.oss.o3ppm.dao.LibraryRepository;
import me.janeve.oss.o3ppm.dao.LibraryVersionRepository;
import me.janeve.oss.o3ppm.dao.ProjectRepository;
import me.janeve.oss.o3ppm.dao.UserRepository;
import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class BaseController {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired protected ProjectRepository projectRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected LibraryRepository libraryRepository;
    @Autowired protected LibraryVersionRepository libraryVersionRepository;

    protected Project findProject(final String projectId) {
        Optional<Project> queryResult = projectRepository.findById(projectId);
        Project project;
        if(queryResult.isPresent()){
            project = queryResult.get();
        } else {
            return null;
        }
        return project;
    }

    protected ProjectRelease getProjectRelease(Project project, @PathVariable String version) {
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
        return projectRelease;
    }

    @InitBinder
    public void allowEmptyDateBinding( WebDataBinder binder )
    {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));

    }
}