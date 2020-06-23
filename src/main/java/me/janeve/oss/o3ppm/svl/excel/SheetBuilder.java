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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.util.*;

public abstract class SheetBuilder {

    protected static final XSSFColor HEADER_CELL_COLOR = new XSSFColor(new java.awt.Color(52, 58, 64), new DefaultIndexedColorMap());
    protected static final XSSFColor BORDER_COLOR = new XSSFColor(new java.awt.Color(222, 226, 230), new DefaultIndexedColorMap());

    protected static final int PADDING = 1;

    protected final Map<Integer, Integer> column_widths = new TreeMap<>();

    protected static final int MAX_WIDTH = 80;
    protected static final int MIN_WIDTH = 12;
    protected static final int MULTIPLIER = 256;

    protected void addCell(XSSFRow row, Object value, int index) {
        addCell(row, value, index, null);
    }

    protected void addCell(XSSFRow row, Object value, int index, XSSFCellStyle cellStyle) {
        if(value == null) {
            value = "";
        }

        XSSFCell cell = row.createCell(PADDING + index);
        if(cellStyle == null) {
            cellStyle = cell.getCellStyle();
        }
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value.toString());

        setBorders(cellStyle);

        if(column_widths.get(index) != null) {
            column_widths.put(index, Math.max(column_widths.get(index), value.toString().length()));
        } else {
            column_widths.put(index, value.toString().length());
        }
    }

    private void setBorders(XSSFCellStyle cellStyle) {
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        cellStyle.setBottomBorderColor(BORDER_COLOR);
        cellStyle.setLeftBorderColor(BORDER_COLOR);
        cellStyle.setRightBorderColor(BORDER_COLOR);
        cellStyle.setTopBorderColor(BORDER_COLOR);
    }

    protected void addHeaderCell(XSSFRow row, Object value, int index) {
        XSSFWorkbook workbook = row.getSheet().getWorkbook();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        XSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(HEADER_CELL_COLOR);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);

        addCell(row, value, index, headerCellStyle);
    }

    protected void autoAdjustColumns(XSSFSheet sheet) {
        sheet.setDefaultColumnWidth(4);
        for (Map.Entry<Integer, Integer> entry: column_widths.entrySet()) {
            sheet.setColumnWidth( PADDING + entry.getKey(), Math.min( Math.max(MIN_WIDTH, entry.getValue()), MAX_WIDTH ) * MULTIPLIER );
        }
    }

    protected void addRowCell(XSSFSheet sheet, Object value, int rowNumber, int columnNumber) {
        XSSFRow row = sheet.createRow(PADDING + rowNumber);
        addCell(row, value, columnNumber);
    }
}