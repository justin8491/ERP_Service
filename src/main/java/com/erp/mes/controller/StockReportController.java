package com.erp.mes.controller;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.service.StockReportService;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock-report")
public class StockReportController {
    private final StockReportService stockReportService;


    @GetMapping("/generate")
    public String generateReport(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Integer itemId,
            Model model) {

        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);  // 기본값으로 1달 전
            startDate = cal.getTime();
        }

        if (endDate == null) {
            endDate = new Date();  // 기본값으로 현재 날짜
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (itemId != null) {
            params.put("itemId", itemId);
        }

        List<StockReportDTO> reportData = stockReportService.generateStockReport(params);
        Double totalValue = stockReportService.calculateTotalValue(params);

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalValue", totalValue);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("itemId", itemId);

        return "stock/report";
    }

    @GetMapping("/reportdetail/{stkId}")
    public String viewStockDetails(@PathVariable("stkId") int stkId, Model model) {
        StockReportDTO stockDetail = stockReportService.getStockDetails(stkId);
        if (stockDetail == null) {
            model.addAttribute("error", "해당 재고 정보를 찾을 수 없습니다.");
            return "errorPage";
        }
        model.addAttribute("stock", stockDetail);
        return "stock/reportDetail";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<ByteArrayInputStream> exportToExcel(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Integer itemId) throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (itemId != null) {
            params.put("itemId", itemId);
        }

        List<StockReportDTO> reportData = stockReportService.generateStockReport(params);
        Double totalValue = stockReportService.calculateTotalValue(params);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Stock Report");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Item Name");
        headerRow.createCell(2).setCellValue("Quantity");
        headerRow.createCell(3).setCellValue("Location");
        headerRow.createCell(4).setCellValue("Unit Price");
        headerRow.createCell(5).setCellValue("Total Value");

        // Populate data rows
        int rowNum = 1;
        for (StockReportDTO item : reportData) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getDate().toString());
            row.createCell(1).setCellValue(item.getItemName());
            row.createCell(2).setCellValue(item.getTotalQty());
            row.createCell(3).setCellValue(item.getLocation());
            row.createCell(4).setCellValue(item.getUnitPrice().doubleValue());
            row.createCell(5).setCellValue(item.getTotalValue().doubleValue());
        }

        // Add total row
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(0).setCellValue("Total");
        totalRow.createCell(5).setCellValue(totalValue);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=stock_report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<ByteArrayInputStream> exportToPdf(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Integer itemId) throws IOException, DocumentException {

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (itemId != null) {
            params.put("itemId", itemId);
        }

        List<StockReportDTO> reportData = stockReportService.generateStockReport(params);
        Double totalValue = stockReportService.calculateTotalValue(params);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Stock Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        // Add date range
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph dateRange = new Paragraph("From: " + startDate + " To: " + endDate, dateFont);
        document.add(dateRange);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 4, 2, 3, 3, 3});

        Stream.of("Date", "Item Name", "Quantity", "Location", "Unit Price", "Total Value")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

        for (StockReportDTO item : reportData) {
            table.addCell(item.getDate().toString());
            table.addCell(item.getItemName());
            table.addCell(String.valueOf(item.getTotalQty()));
            table.addCell(item.getLocation());
            table.addCell(String.valueOf(item.getUnitPrice()));
            table.addCell(String.valueOf(item.getTotalValue()));
        }

        // Add table to document
        document.add(table);

        // Add total value
        Paragraph totalValueParagraph = new Paragraph("Total Value: " + totalValue, dateFont);
        totalValueParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalValueParagraph);

        document.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=stock_report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(in);
    }
}
