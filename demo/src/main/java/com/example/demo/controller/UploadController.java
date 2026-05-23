package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDto;
import com.example.demo.service.ReadValidateCSV;
import com.example.demo.service.ReadValidateExcel;
import com.example.demo.service.ReadValidateFile;
import com.example.demo.service.UploadFile;


//Main rest controller.for csv
@RestController
public class UploadController {

    @Autowired
    private ReadValidateCSV validateCSVFileService;

    @Autowired
    private ReadValidateExcel validateExcelFileService;


    @Autowired
    private UploadFile uploadFileService;

    
    private ReadValidateFile validateFileService;

    // Endpoint to read file
    @GetMapping("/readfile")
    public List<CourseDto> readAndValidateFile() {
        validateFileService = validateCSVFileService;
        List<CourseDto> csvFullList = validateFileService.readFile();
        

        if (validateFileService.validateFile(csvFullList)==false) {
            System.out.println("in upload");
            for (CourseDto temp : csvFullList) {
                uploadFileService.validateRows(temp);
            }
            return csvFullList; // return after processing all rows
        } else{return Collections.emptyList();}
    }
    
}

    
