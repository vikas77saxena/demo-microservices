package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CourseDto;

@Service
public class ReadValidateExcel implements ReadValidateFile {

    private static final Logger log = LoggerFactory.getLogger(ReadValidateExcel.class);

    private final ObjectProvider<S3FileStorageService> s3FileStorageService;

    @Value("${aws.s3.excel.local-path:D:/courses.xlsx}")
    private String excelFilePath;

    @Value("${aws.s3.excel.key:courses/uploads/courses.xlsx}")
    private String excelS3Key;

    public ReadValidateExcel(ObjectProvider<S3FileStorageService> s3FileStorageService) {
        this.s3FileStorageService = s3FileStorageService;
    }

    private void uploadExcelToS3() {
        S3FileStorageService storage = s3FileStorageService.getIfAvailable();
        if (storage == null) {
            throw new IllegalStateException(
                    "S3 is not configured. Set aws.s3.enabled=true and restart the application.");
        }
        String location = storage.uploadFile(excelFilePath, excelS3Key);
        log.info("Excel file uploaded to {}", location);
    }

@Override
public List<CourseDto> readFile() {

    uploadExcelToS3();

    List<CourseDto> courses = new ArrayList<>();
    log.info("Reading Excel file from {}", excelFilePath);
  


    try (FileInputStream fis = new FileInputStream(excelFilePath);
         Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheetAt(0); // first sheet
        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("Default Charset: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
          String hindi = "हिन्दी भाषा परीक्षण";
        System.out.println("Hindi text: " + hindi);



        // Assuming first row is header, start from row 1
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            CourseDto course = new CourseDto();
            course.setTypeOfEdp(getCellValue(row.getCell(0)));
      //      course.setEdpId(Integer.valueOf(getCellValue(row.getCell(1))));
       Cell cell = row.getCell(1);
if (cell != null) {
    switch (cell.getCellType()) {
        case NUMERIC:
            // Numeric cell → safe cast
            course.setEdpId((int) cell.getNumericCellValue());
            break;

        case STRING:
            String edpIDStr = cell.getStringCellValue();
            if (edpIDStr != null && !edpIDStr.trim().isEmpty()) {
                try {
                    course.setEdpId(Integer.parseInt(edpIDStr.trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid EDP ID: " + edpIDStr);
                    course.setEdpId(-1); // fallback for invalid string
                }
            } else {
                course.setEdpId(-2); // fallback for empty string
            }
            break;

       
        case BLANK:
            course.setEdpId(-2); // treat blank as default
            break;

        default:
            System.err.println("Unexpected cell type for EDP ID: " + cell.getCellType());
            course.setEdpId(-4); // fallback for other types
            break;
    }
}
            course.setQualificationCodeOld(getCellValue(row.getCell(2)));
            course.setQualificationCodeUpdated(getCellValue(row.getCell(3)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
            String qualificationName = null;
            cell = row.getCell(4);
            if (cell != null) {
               if(cell.getCellType() == CellType.STRING){
                qualificationName = cell.getStringCellValue(); // preserves Hindi
               } 
                else{
                    qualificationName = cell.toString();
                }
            }
                course.setQualificationName(qualificationName != null ? qualificationName.trim() : null);
                out.println("qualifi" + qualificationName);
            
            cell = row.getCell(5);
            
            String shortDesc = null;

            if (cell != null) {
                if (cell.getCellType() == CellType.STRING) {
                    shortDesc = cell.getStringCellValue(); // preserves Hindi
                } else {
                    shortDesc = cell.toString(); // fallback for non-string cells
                }
            }
            

            out.println("shortdesc" + shortDesc + row.getCell(5));
            course.setShortDescription(shortDesc != null ? shortDesc.trim() : null);

            course.setAboutVideoUrl(getCellValue(row.getCell(6)));
          //  course.setDuration(Integer.valueOf(getCellValue(row.getCell(7))));
           cell = row.getCell(7);
if (cell != null) {
    if (cell.getCellType() == CellType.NUMERIC) {
        // If it's numeric, cast directly
        course.setDuration((int) cell.getNumericCellValue());
    } else {
        String durationStr = cell.getStringCellValue();
        if (durationStr != null && !durationStr.trim().isEmpty()) {
            try {
                course.setDuration(Integer.parseInt(durationStr.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid duration: " + durationStr);
                course.setDuration(-1); // or a default like 0
            }
        } else {
            course.setDuration(-2); // or default
        }
    }
}

            course.setLanguage(getCellValue(row.getCell(8)));
           

            courses.add(course);
        }
    

    
}catch(IOException e){
    e.printStackTrace();
}
return courses;
}

// Helper method to safely extract cell values
private String getCellValue(Cell cell) {
    if (cell == null) return "";
    switch (cell.getCellType()) {
        case STRING: return cell.getStringCellValue();
        case NUMERIC: return String.valueOf(cell.getNumericCellValue());
        case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
        case FORMULA: return cell.getCellFormula();
        default: return "";
    }
}


@Override
  public boolean validateFile(List<CourseDto> list) {
    PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);

    out.println("Vikas-start of read");
    String fileName = "D:/courseserror.xlsx";
    File file = new File(fileName);


    // Delete previous file if it exists
    if (file.exists()) {
        if (file.delete()) {
            System.out.println("Deleted old error file: " + fileName);
        } else {
            System.out.println("Could not delete old error file: " + fileName);
        }
    }

    // Check duplicates by Qualification Code Old
    List<CourseDto> duplicates = list.stream()
            .collect(Collectors.groupingBy(item -> item.getQualificationCodeOld() + "_" + item.getLanguage(), Collectors.toList()))
            .values().stream()
            .filter(l -> l.size() > 1)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    if (!duplicates.isEmpty()) {
        System.out.println("Found duplicates by Qualification Code Old: " + duplicates.size());
        writeFile(duplicates, "The following records have duplicate Qualification Code Old");
        // don’t return here if you also want to check Updated codes
    }

    // Check duplicates by Qualification Code Updated
   List<CourseDto> dupList = list.stream()
    .collect(Collectors.groupingBy(
        item -> item.getQualificationCodeUpdated() + "_" + item.getLanguage(), // combine as key
        Collectors.toList()
    ))
    .values().stream()
    .filter(l -> l.size() > 1)   // only groups with duplicates
    .flatMap(Collection::stream) // flatten back into a list
    .collect(Collectors.toList());

    if (!dupList.isEmpty()) {
        System.out.println("Found duplicates by Qualification Code Updated: " + dupList.size());
        writeFile(dupList, "The following records have duplicate Qualification Code Updated");
    }

    return !duplicates.isEmpty() || !dupList.isEmpty();
}

 

private void writeFile(List<CourseDto> list, String message) {
    String fileName = "D:/courseserror.xlsx";

    // Ensure necessary Apache POI classes are imported at the top:
    // import org.apache.poi.ss.usermodel.*;
    // import org.apache.poi.xssf.usermodel.XSSFWorkbook;

    File file = new File(fileName);



    try (Workbook workbook = file.exists()
                ? new XSSFWorkbook(new FileInputStream(file))
                : new XSSFWorkbook()) {

        Sheet sheet = workbook.getSheet("CoursesError");
        if (sheet == null) {
            sheet = workbook.createSheet("CoursesError");
        }


        int rowIndex = sheet.getPhysicalNumberOfRows();
 
        System.out.println("yahoo" + rowIndex + message);
        // Write the message as the first row
        Row messageRow = sheet.createRow(rowIndex++);
        Cell messageCell = messageRow.createCell(0);
        messageCell.setCellValue(message);

        // Write header row
        Row headerRow = sheet.createRow(rowIndex++);
        String[] headers = {
            "TypeOfEdp", "EdpId", 
            "QualificationCodeUpdated", "QualificationCodeOld", "QualificationName",
            "ShortDescription", "AboutVideoUrl", "Duration", "Language"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Write each CourseDto record
        for (CourseDto item : list) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(item.getTypeOfEdp());
            row.createCell(1).setCellValue(item.getEdpId());
            row.createCell(2).setCellValue(item.getQualificationCodeUpdated());
            row.createCell(3).setCellValue(item.getQualificationCodeOld());
            row.createCell(4).setCellValue(item.getQualificationName());
            row.createCell(5).setCellValue(item.getShortDescription());
            row.createCell(6).setCellValue(item.getAboutVideoUrl());
            row.createCell(7).setCellValue(item.getDuration());
            row.createCell(8).setCellValue(item.getLanguage());
        }

        // Auto-size columns for readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}






    