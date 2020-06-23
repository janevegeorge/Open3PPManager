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
package me.janeve.oss.o3ppm.svl;

import me.janeve.oss.o3ppm.dao.ProjectRepository;
import me.janeve.oss.o3ppm.entities.Project;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public abstract class BasicSVLDownloadGenerator implements SVLDownloadGenerator {

    @Autowired ProjectRepository projectRepository;

    public Download generateSVL(String projectId, String projectVersion){
        ByteArrayInputStream stream = null;
        Project project = null;
        ProjectRelease requestedSVLRelease = null;

        Optional<Project> result = projectRepository.findById(projectId);
        if(result.isPresent()) {
            project = result.get();

            for(ProjectRelease release: project.getReleases()) {
                if(release.getVersion().equals(projectVersion)){
                    requestedSVLRelease = release;
                    break;
                }
            }
            stream = generateSVL(project, requestedSVLRelease);
        }


        return new Download(project, requestedSVLRelease, stream);
    }
}