package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CourseDto;

@Service
public interface  ReadValidateFile{

public List<CourseDto> readFile();

public boolean validateFile(List<CourseDto> list);

}






    