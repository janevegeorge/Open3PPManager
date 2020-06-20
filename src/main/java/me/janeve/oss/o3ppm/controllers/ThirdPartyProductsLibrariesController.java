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

    @PostMapping("/new")
    public String create3PPLibrary(@RequestParam String projectId, @RequestParam String releaseVersion, @Validated LibraryVersion libraryVersion) {

        logger.info("LibraryVersion: " + libraryVersion);

        if(libraryVersion.getLibrary().getName().trim().isEmpty() || libraryVersion.getVersion().trim().isEmpty()) {
            throw new RuntimeException("Invalid library name or version.");
        }

        Library library = libraryVersion.getLibrary();
        List<LibraryVersion> libraryVersions = library.getVersions();
        if(libraryVersions == null) {
            libraryVersions = new ArrayList<>();
        }
        libraryVersions.add(libraryVersion);
        library = libraryRepository.save(library);
        libraryVersion.setLibrary(library);

        libraryVersion = libraryVersionRepository.save(libraryVersion);


        if(projectId != null && releaseVersion != null) {
            Project project = findProject(projectId);
            ProjectRelease projectRelease = getProjectRelease(project, releaseVersion);
            List<LibraryVersion> projectReleaseDependencies = projectRelease.getDependencies();
            if(projectReleaseDependencies == null) {
                projectReleaseDependencies = new ArrayList<>();
            }
            projectReleaseDependencies.add(libraryVersion);
            projectRelease.setDependencies(projectReleaseDependencies);
            projectRepository.save(project);
            return "redirect:/projects/" + projectId + "/releases/" + releaseVersion;
        } else {
            return "redirect:/3pp_libraries/";
        }

    }

}