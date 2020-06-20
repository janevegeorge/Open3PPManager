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