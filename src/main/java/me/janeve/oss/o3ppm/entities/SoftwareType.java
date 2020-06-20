/*!
 * Open3PPManager v0.1-SNAPSHOT (https://github.com/janevegeorge/Open3PPManager)
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

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SoftwareType {
    FOSS("FOSS"),
    COMMERCIAL("Commercial"),
    INTERNAL("Internal"),
    UNKNOWN("Others");

    private final String displayName;

    SoftwareType(String displayName) {
        this.displayName = displayName;
    }
}