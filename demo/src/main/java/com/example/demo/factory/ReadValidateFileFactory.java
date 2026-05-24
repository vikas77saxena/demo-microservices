package com.example.demo.factory;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.service.ReadValidateCSV;
import com.example.demo.service.ReadValidateExcel;
import com.example.demo.service.ReadValidateFile;

@Component
public class ReadValidateFileFactory {

    private final Map<FileType, ReadValidateFile> readers;

    public ReadValidateFileFactory(ReadValidateCSV csvReader, ReadValidateExcel excelReader) {
        readers = new EnumMap<>(FileType.class);
        readers.put(FileType.CSV, csvReader);
        readers.put(FileType.EXCEL, excelReader);
    }

    public ReadValidateFile createReader(FileType fileType) {
        ReadValidateFile reader = readers.get(fileType);
        if (reader == null) {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
        return reader;
    }
}
