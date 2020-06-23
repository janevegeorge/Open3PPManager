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
package me.janeve.oss.o3ppm.svl.excel;

import me.janeve.oss.o3ppm.entities.Project;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProjectDetailsSheetBuilder extends SheetBuilder {

    public void buildProjectDetailsSheet(XSSFWorkbook workbook, Project project) {
        XSSFSheet sheet = workbook.createSheet("Project Info");

        XSSFRow headerRow = sheet.createRow(PADDING);
        addHeaderCell(headerRow, "Project Id", 0);
        addCell(headerRow, project.getId(), 1);

        headerRow = sheet.createRow(PADDING+1);
        addHeaderCell(headerRow, "Project Name", 0);
        addCell(headerRow, project.getName(), 1);

        headerRow = sheet.createRow(PADDING+2);
        addHeaderCell(headerRow, "Owner", 0);
        addCell(headerRow, project.getOwner().getScreenName(), 1);

        headerRow = sheet.createRow(PADDING+3);
        addHeaderCell(headerRow, "Description", 0);
        addCell(headerRow, project.getDescription(), 1);

        autoAdjustColumns(sheet);
    }

}