package com.salesmanagement.management.download;
import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.sales.Sales;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelGenerator {
    public static ByteArrayOutputStream generateOutwardExcel(List<Outward> outwardList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Outward");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Bail Number", "Item Size", "Item Type", "Date", "Dozen", "Piece"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Outward outward : outwardList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(outward.getOutwardId().getOutwardBailNumber());
            row.createCell(1).setCellValue(outward.getOutwardId().getOutwardItemSize());
            row.createCell(2).setCellValue(outward.getOutwardId().getOutwardItemType());
            row.createCell(3).setCellValue(outward.getOutwardDate());
            row.createCell(4).setCellValue(outward.getOutwardDozen());
            row.createCell(5).setCellValue(outward.getOutwardPiece());
        }

        // Write the output to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out;
    }
    public static ByteArrayOutputStream generateInwardExcel(List<Inward> inwardList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inward");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Memo Number", "Item Size", "Item Type", "Date", "Dozen", "Piece"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Inward inward : inwardList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(inward.getInwardId().getInwardMemoNumber());
            row.createCell(1).setCellValue(inward.getInwardId().getInwardItemSize());
            row.createCell(2).setCellValue(inward.getInwardId().getInwardItemType());
            row.createCell(3).setCellValue(inward.getInwardDate());
            row.createCell(4).setCellValue(inward.getInwardDozen());
            row.createCell(5).setCellValue(inward.getInwardPiece());
        }

        // Write the output to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out;
    }
    public static ByteArrayOutputStream generateStockExcel(List<Sales> salesList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Item Size", "Item Type", "Dozen", "Piece"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Sales sales : salesList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sales.getSalesId().getSalesItemSize());
            row.createCell(1).setCellValue(sales.getSalesId().getSalesItemType());
            row.createCell(2).setCellValue(sales.getSalesDozen());
            row.createCell(3).setCellValue(sales.getSalesPiece());
        }

        // Write the output to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out;
    }
}
