package kz.kartel.dutyscheduler.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import kz.kartel.dutyscheduler.components.special_date.DutyStatistics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    public static ByteArrayInputStream customersToExcel(List<DutyStatistics> dutyStatisticsList, String dateString, String calendarName) throws IOException {
        String[] COLUMNs = {"ФИО", "Кол-во дежурств будни", "Кол-во дежурств вых/празд"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("duty statistics");

            Font headerFont0 = workbook.createFont();
            headerFont0.setBold(true);
            headerFont0.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle0 = workbook.createCellStyle();
            headerCellStyle0.setFont(headerFont0);

            Row headerRow0 = sheet.createRow(0);
            Cell cell0 = headerRow0.createCell(0);
            cell0.setCellValue("Отчет дежурств за " + dateString + ". \n" + calendarName);
            cell0.setCellStyle(headerCellStyle0);

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(1);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                //CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 2;
            for (DutyStatistics dutyStatistics : dutyStatisticsList) {
                Row row = sheet.createRow(rowIdx++);

                Cell cell1 = row.createCell(0);
                CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER);
                cell1.setCellValue(dutyStatistics.getFullName());

                Cell cell2 = row.createCell(1);
                CellUtil.setAlignment(cell2, HorizontalAlignment.CENTER);
                cell2.setCellValue(dutyStatistics.getDutiesCnt());

                Cell cell3 = row.createCell(2);
                CellUtil.setAlignment(cell3, HorizontalAlignment.CENTER);
                cell3.setCellValue(dutyStatistics.getHolidayDutiesCnt());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static String getMonthName(int month){
        String months[] = {"Январь","Февраль","Март","Апрель","Март","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        if(month >= 0 && month < months.length){
            return months[month];
        }

        return "none";
    }
}