package com.erp.mes.controller;

import com.erp.mes.dto.StockReportDTO;
import com.erp.mes.service.StockReportService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.PathVariable;
=======
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock-report")
public class StockReportController {
    private final StockReportService stockReportService;

<<<<<<< Updated upstream
    // 전체 재고 상태 보고서
    @GetMapping("/list")
    public String getStockReport(@RequestParam Map<String, Object> params, Model model) {
        List<StockReportDTO> stockReportList = stockReportService.getStockReport(params);
        model.addAttribute("stockReportList", stockReportList);
        return "stockreport/list";
=======
    @GetMapping("/generate")
    public String generateReport(@RequestParam Map<String, Object> params, Model model) {
        List<StockReportDTO> reportData = stockReportService.generateStockReport(params);
        model.addAttribute("reportData", reportData);
        return "stock/report";
    }

    @GetMapping("/detail")
    public String getStockDetail(@RequestParam("stkId") int stkId, Model model) {
        StockReportDTO stockDetail = stockReportService.getStockDetail(stkId);
        model.addAttribute("stock", stockDetail);
        return "stock/detail";
>>>>>>> Stashed changes
    }

    @GetMapping("/period-total")
    public String getPeriodTotal(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {
        List<StockReportDTO> periodReport = stockReportService.getPeriodReport(startDate, endDate);
        BigDecimal totalAmount = periodReport.stream()
                .map(StockReportDTO::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("periodReport", periodReport);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "stock/period-report";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel(@RequestParam Map<String, Object> params) throws IOException {
        List<StockReportDTO> reportData = stockReportService.generateStockReport(params);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Stock Report");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Item Name");
            headerRow.createCell(2).setCellValue("Item Code");
            headerRow.createCell(3).setCellValue("Total Quantity");
            headerRow.createCell(4).setCellValue("Location");
            headerRow.createCell(5).setCellValue("Expiration Date");
            headerRow.createCell(6).setCellValue("Unit Price");
            headerRow.createCell(7).setCellValue("Total Value");

            int rowNum = 1;
            for (StockReportDTO data : reportData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getDate().toString());
                row.createCell(1).setCellValue(data.getItemName());
                row.createCell(2).setCellValue(data.getItemCode());
                row.createCell(3).setCellValue(data.getTotalQty());
                row.createCell(4).setCellValue(data.getLocation());
                row.createCell(5).setCellValue(data.getExpirationDate().toString());
                row.createCell(6).setCellValue(data.getUnitPrice().doubleValue());
                row.createCell(7).setCellValue(data.getTotalValue().doubleValue());
            }

            workbook.write(out);

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=stock_report.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(in));
        }
    }
}