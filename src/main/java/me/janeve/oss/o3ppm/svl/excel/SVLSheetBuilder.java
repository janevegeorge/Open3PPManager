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

import me.janeve.oss.o3ppm.entities.LibraryVersion;
import me.janeve.oss.o3ppm.entities.ProjectRelease;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

public class SVLSheetBuilder extends SheetBuilder {

    public static final XSSFColor TRADE_COMPLIANCE_CELL_COLOR = new XSSFColor(new java.awt.Color(185, 227, 166), new DefaultIndexedColorMap());
    private static final XSSFColor SOFTWARE_DATA_CELL_COLOR = new XSSFColor(new java.awt.Color(254, 229, 187), new DefaultIndexedColorMap());

    public void buildProjectReleaseSheet(XSSFWorkbook workbook, ProjectRelease releaseInformation) {
        XSSFSheet sheet = workbook.createSheet("SVL");

        XSSFRow headerCategoriesRow = sheet.createRow(PADDING);
        addHeaderCell(headerCategoriesRow, "Name", 0);
        addHeaderCell(headerCategoriesRow, "Software Data", 1);
        addHeaderCell(headerCategoriesRow, "Trade Compliance", 7);

        XSSFRow headerRow = sheet.createRow(PADDING+1);
        String[] headers = {"Vendor", "Website", "Software Type", "Version", "Download Url", "Platform",
                "Country of Origin", "EU ECCN", "US ECCN", "BIS Authorization",
                "EAR Regulation & Restrictions", "Encryption Protocol", " Protocol Encryption Algorithms"};
        for (int i = 0; i < headers.length; i++) {
            addHeaderCell(headerRow, headers[i], i+1);
        }

        int rowIndex = 2;
        if(releaseInformation.getDependencies() != null ) {
            for (LibraryVersion library : releaseInformation.getDependencies()) {
                XSSFRow libraryRow = sheet.createRow(PADDING + rowIndex);

                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                addCell(libraryRow, library.getLibrary().getName(), 0, cellStyle);

                fillSoftwareData(workbook, libraryRow, library);
                fillTradeComplianceData(workbook, libraryRow, library);
                rowIndex++;
            }
        }
        autoAdjustColumns(sheet);

        sheet.addMergedRegion(
            new CellRangeAddress(
                    PADDING,
                    PADDING,
                    PADDING+1,
                    PADDING+6
            )
        );

        sheet.addMergedRegion(
            new CellRangeAddress(
                PADDING,
                PADDING,
                PADDING+7,
                PADDING+13
            )
        );

        sheet.addMergedRegion(
            new CellRangeAddress(
                PADDING,
                PADDING + 1,
                PADDING,
                PADDING
            )
        );
    }

    private void fillSoftwareData(XSSFWorkbook workbook, XSSFRow libraryRow, LibraryVersion library) {
        if (library.getLibrary().getSoftwareData() != null) {
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(SOFTWARE_DATA_CELL_COLOR);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setVerticalAlignment(VerticalAlignment.TOP);

            addCell(libraryRow, library.getLibrary().getSoftwareData().getVendor(), 1, cellStyle);
            addCell(libraryRow, library.getLibrary().getSoftwareData().getWebsite(), 2, cellStyle);
            addCell(libraryRow, library.getLibrary().getSoftwareData().getSoftwareType().getDisplayName(), 3, cellStyle);
            addCell(libraryRow, library.getVersion(), 4, cellStyle);
            addCell(libraryRow, library.getDownloadUrl(), 5, cellStyle);
            addCell(libraryRow, library.getLibrary().getSoftwareData().getPlatform(), 6, cellStyle);
        }
    }

    private void fillTradeComplianceData(XSSFWorkbook workbook, XSSFRow libraryRow, LibraryVersion library) {
        if(library.getLibrary().getTradeCompliance() != null) {
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(TRADE_COMPLIANCE_CELL_COLOR);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setVerticalAlignment(VerticalAlignment.TOP);

            addCell(libraryRow, library.getLibrary().getTradeCompliance().getCountryCode(), 7, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getEuECCN(), 8, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getUsECCN(), 9, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getBisAuthorization(), 10, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getEarRegulationRestrictions(), 11, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getEncryptionProtocol(), 12, cellStyle);
            addCell(libraryRow, library.getLibrary().getTradeCompliance().getEncryptionAlgorithms(), 13, cellStyle);
        }
    }

}