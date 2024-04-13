package com.oop.springbootmvc.controllers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import com.oop.springbootmvc.model.DashboardDataDTO;
import com.oop.springbootmvc.repository.DashboardDataDTORepository;

@RestController
public class DashboardDataExportController {
    @Autowired
    private DashboardDataDTORepository dashboardDataDTORepository;

    @GetMapping("/export/dashboard-data/excel")
    public ResponseEntity<byte[]> exportDashboardDataToExcel(@RequestParam(required = false) Long eventId) {
        try {
            List<DashboardDataDTO> dashboardData;
            String filename = "DashboardData_AllEvents.xlsx";  // Default filename for all events

            if (eventId != null) {
                
                dashboardData = dashboardDataDTORepository.getDashboardData(eventId);
                filename = "DashboardData_EventID_" + eventId + ".xlsx"; 
            } else {
                
                dashboardData = dashboardDataDTORepository.getAllDashboardData();
            }

            byte[] excelData = exportTicketsToExcel(dashboardData);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(excelData.length);
        
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private byte[] exportTicketsToExcel(List<DashboardDataDTO> dashoboardData) throws IOException {
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DashboardData");
            int rowNum = 0;

            // Create header row
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Ticket ID", "Ticket Guid", "Ticket Status", "Transaction ID", "Transaction Cancellation Cost", "Transaction Status", 
            "Transaction Purchased Date Time", "Transaction Cost", "Seat ID", "Seat Type", "Seat Cost", "Number of Seats", "Event ID", "Event Name", 
            "Event Description", "Event Category", "Event Venue", "Event Cancellation Fee",  "Event Start Date", "Event Start Time", "Event End Time", 
            "Ticket Sale Start Date Time", "Ticket Sale End Date Time", "Event Status"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate data rows
            for (DashboardDataDTO data : dashoboardData) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // Ticket
                row.createCell(colNum++).setCellValue(data.getTicketId());
                row.createCell(colNum++).setCellValue(data.getTicketGuid());
                row.createCell(colNum++).setCellValue(data.getTicketStatus());

                // Transaction
                row.createCell(colNum++).setCellValue(data.getTransactionId());
                row.createCell(colNum++).setCellValue(data.getTransactionCancellationCost());
                row.createCell(colNum++).setCellValue(data.getTransactionStatus());
                row.createCell(colNum++).setCellValue(data.getTransactionPurchasedDateTime().toString());
                row.createCell(colNum++).setCellValue(data.getTransactionCost());

                // Seat
                row.createCell(colNum++).setCellValue(data.getSeatId());
                row.createCell(colNum++).setCellValue(data.getSeatType());
                row.createCell(colNum++).setCellValue(data.getSeatCost());
                row.createCell(colNum++).setCellValue(data.getNumberOfSeats());

                // Event
                row.createCell(colNum++).setCellValue(data.getEventId());
                row.createCell(colNum++).setCellValue(data.getEventName());
                row.createCell(colNum++).setCellValue(data.getEventDescription());
                row.createCell(colNum++).setCellValue(data.getEventCategory());
                row.createCell(colNum++).setCellValue(data.getEventVenue());
                row.createCell(colNum++).setCellValue(data.getEventCancellationFee());
                row.createCell(colNum++).setCellValue(data.getEventStartDate().toString());
                row.createCell(colNum++).setCellValue(data.getEventStartTime().toString());
                row.createCell(colNum++).setCellValue(data.getEventEndTime().toString());
                row.createCell(colNum++).setCellValue(data.getEventTicketSaleStartDateTime().toString());
                row.createCell(colNum++).setCellValue(data.getEventTicketSaleEndDateTime().toString());
                row.createCell(colNum++).setCellValue(data.getEventStatus());
            }

            // Resize columns to fit content
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the workbook content to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }

    }

}
