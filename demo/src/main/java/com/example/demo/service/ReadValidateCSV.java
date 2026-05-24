package com.example.demo.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

import javax.annotation.processing.FilerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CourseDto;
@Service
public class ReadValidateCSV implements  ReadValidateFile {

    private static final Logger logger = LoggerFactory.getLogger(ReadValidateCSV.class);
    
    @Override
    public List<CourseDto> readFile(){
        File file = new File("D:/courses.csv");
        
        List<CourseDto> list =new ArrayList<>();
         System.out.println("Default Charset: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
          String hindi = "हिन्दी भाषा परीक्षण";
        
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
        out.println("Hindi text: " + hindi);
       try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
     CSVParser csvParser = new CSVParser(reader,
             CSVFormat.DEFAULT
                     .withFirstRecordAsHeader() // use header row
                     .withIgnoreHeaderCase()    // case-insensitive headers
                     .withTrim())) {  

            for (CSVRecord record : csvParser) {
                CourseDto coursedto = new CourseDto(); 
                coursedto.setTypeOfEdp(record.get("Type of EDP"));
                coursedto.setEdpId(Integer.valueOf(record.get("EDP Id")));
                coursedto.setQualificationCodeOld(record.get("Qualification Code (Old)"));
                coursedto.setQualificationCodeUpdated(record.get("Qualification Code (Updated)"));
                coursedto.setQualificationName(record.get("Qualification Name"));
                coursedto.setShortDescription(record.get("Short Description"));
              
                coursedto.setAboutVideoUrl(record.get("About Video Url"));
                coursedto.setDuration(Integer.valueOf(record.get("Duration")));
                coursedto.setLanguage(record.get("Language"));
                out.println("short description" + record.get("Short Description"));
               list.add(coursedto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean validateFile(List<CourseDto> list) {
    System.out.println("Vikas-start of read");
     String fileName = "D:/courseserror.csv";
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
    .collect(Collectors.groupingBy(
        item -> item.getQualificationCodeOld() + "_" + item.getLanguage(),
        Collectors.toList()
    ))
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
        item -> item.getQualificationCodeUpdated() + "_" + item.getLanguage(),
        Collectors.toList()
    ))
    .values().stream()
    .filter(l -> l.size() > 1)
    .flatMap(Collection::stream)
    .collect(Collectors.toList());

    if (!dupList.isEmpty()) {
        System.out.println("Found duplicates by Qualification Code Updated: " + dupList.size());
        writeFile(dupList, "The following records have duplicate Qualification Code Updated");
    }

    return !duplicates.isEmpty() || !dupList.isEmpty();
}

   private void writeFile(List<CourseDto> list, String message) {
    

     String fileName = "D:/courseserror.csv";
   
try (
        FileWriter writer = new FileWriter(fileName,true);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("TypeOfEdp", "EdpId", "QualificationCodeOld", 
                            "QualificationCodeUpdated", "QualificationName", 
                            "ShortDescription", "AboutVideoUrl", "Duration", "Language"))
    ) {
        // Write the message as the first row
        printer.printRecord(message);

        // Write each CourseDto record
        for (CourseDto item : list) {
            printer.printRecord(
                item.getTypeOfEdp(),
                item.getEdpId(),
                item.getQualificationCodeOld(),
                item.getQualificationCodeUpdated(),
                item.getQualificationName(),
                item.getShortDescription(),
                item.getAboutVideoUrl(),
                item.getDuration(),
                item.getLanguage()
            );
        }

        printer.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}






    