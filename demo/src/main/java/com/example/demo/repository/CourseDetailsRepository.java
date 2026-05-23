package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CourseDetails;


@Repository
public interface CourseDetailsRepository extends JpaRepository<CourseDetails, Integer> {

   @Query("SELECT COUNT(c) > 0 FROM CourseDetails c WHERE c.qualificationId = :i")
boolean existsByQualificationId(@Param("i") int i);
    
   
}