package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDto;
import com.example.demo.factory.FileType;
import com.example.demo.factory.ReadValidateFileFactory;
import com.example.demo.service.ReadValidateFile;
import com.example.demo.service.UploadFile;

@RestController
public class UploadController {

    private final ReadValidateFileFactory readValidateFileFactory;
    private final UploadFile uploadFileService;

    public UploadController(ReadValidateFileFactory readValidateFileFactory, UploadFile uploadFileService) {
        this.readValidateFileFactory = readValidateFileFactory;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/readCSVfile")
    public List<CourseDto> readAndValidateCsvFile() {
        return processFile(FileType.CSV);
    }

    @GetMapping("/readexcelfile")
    public List<CourseDto> readAndValidateExcelFile() {
        return processFile(FileType.EXCEL);
    }

    private List<CourseDto> processFile(FileType fileType) {
        ReadValidateFile reader = readValidateFileFactory.createReader(fileType);
        List<CourseDto> courses = reader.readFile();

        if (!reader.validateFile(courses)) {
            for (CourseDto row : courses) {
                uploadFileService.validateRows(row);
            }
            return courses;
        }
        return Collections.emptyList();
    }
}
