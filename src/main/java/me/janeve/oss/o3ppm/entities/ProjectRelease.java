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
package me.janeve.oss.o3ppm.entities;

import lombok.*;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.TreeSet;

@Getter @Setter
@ToString
@RequiredArgsConstructor
public class ProjectRelease implements Comparable<ProjectRelease>{
    @NotEmpty private String version;
    private String baseVersion;
    @DBRef private TreeSet<LibraryVersion> dependencies;
    @Indexed(direction = IndexDirection.DESCENDING) protected ZonedDateTime createdAt;
    @Indexed(direction = IndexDirection.DESCENDING) protected ZonedDateTime lastUpdatedAt;
    @DBRef protected User createdBy;
    @DBRef protected User lastUpdatedBy;

    @Override
    public int compareTo(ProjectRelease o) {
        if(o == null) {
            return -1;
        }

        if(o.equals(this)) {
            return 0;
        }

        return o.getLastUpdatedAt().compareTo(getLastUpdatedAt());
    }
}