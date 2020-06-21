/*!
 * Open3PPManager (https://github.com/janevegeorge/Open3PPManager)
 * Copyright 2020 Janeve.Me (http://www.janeve.me)
 *
 * This file is part of "Open3PPManager".
 *
 * Open3PPManager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open3PPManager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open3PPManager. If not, see <http://www.gnu.org/licenses/>.
 */
package me.janeve.oss.o3ppm.controllers;

import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import me.janeve.oss.o3ppm.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/projects")
public class ProjectsController extends BaseController {

    @PostMapping("/new")
    public String createNewProject(Model model, @Validated Project project) {
        if(project.getId() != null) {
            Project dbProjectEntry = findProject(project.getId());
            dbProjectEntry.setDescription(project.getDescription());
            dbProjectEntry.setName(project.getName());
            project = dbProjectEntry;
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.isAuthenticated()) {
                User authenticatedUser = userRepository.findByUsername(auth.getName());
                if(authenticatedUser == null) {
                    throw new RuntimeException("Unauthorized access.");
                }
                project.setOwner(authenticatedUser);
                logger.info("Adding : " + project.toString());
            } else {
                logger.severe("User not authenticated.");
            }
        }

        projectRepository.save(project);

        return homePage(model);
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projects/list";
    }

    @GetMapping("/{id}")
    public String getProjectDetails(Model model, @PathVariable String id) {
        Optional<Project> entry = projectRepository.findById(id);
        entry.ifPresent(project -> model.addAttribute("project", project));
        return "projects/details";
    }

    @PostMapping("/{projectId}/releases/new")
    public String createNewProjectRelease(Model model, @PathVariable String projectId, @Validated ProjectRelease release) {
        Project project = findProject(projectId);
        TreeSet<ProjectRelease> releases = project.getReleases();
        if(releases == null) {
            releases = new TreeSet<>();
        }

        for(ProjectRelease existingRelease:releases) {
            if(existingRelease.compareTo(release) > 0) {
                break;
            }
            if(existingRelease.compareTo(release) == 0) {
                existingRelease.setBaseVersion(release.getBaseVersion());
                release = existingRelease;
            }
        }

        ProjectRelease baseRelease = getProjectRelease(project, release.getBaseVersion());
        if(baseRelease != null) {
            if(release.getDependencies() == null) {
                release.setDependencies(new ArrayList<>());
            }
            if(baseRelease.getDependencies() != null) {
                release.getDependencies().addAll(baseRelease.getDependencies());
            }
        }

        releases.add(release);
        project.setReleases(releases);

        logger.info("Adding release " + release + " to propject " + projectId);
        projectRepository.save(project);

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{projectId}/releases/{version}")
    public String getNewProjectRelease(Model model, @PathVariable String projectId, @PathVariable String version) {
        Project project = findProject(projectId);
        ProjectRelease projectRelease = getProjectRelease(project, version);

        if(projectRelease == null) {
            throw new RuntimeException("Project Version - 404 Not Found!");
        }

        model.addAttribute("project", project);
        model.addAttribute("release", projectRelease);

        return "/project_releases/details";
    }

}