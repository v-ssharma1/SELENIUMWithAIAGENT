package com.qa.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public static void main(String[] args) {
        createExcelWithData();
    }

    public static void createExcelWithData() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestData");

        // Create header row
        Row headerRow = sheet.createRow(0);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("user name");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Password");

        // Create data rows
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("user1");
        dataRow1.createCell(1).setCellValue("pass1");

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("user2");
        dataRow2.createCell(1).setCellValue("pass2");

        Row dataRow3 = sheet.createRow(3);
        dataRow3.createCell(0).setCellValue("admin");
        dataRow3.createCell(1).setCellValue("admin123");

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream("src/test/resources/testdata.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Excel file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}