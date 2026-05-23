package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {



 @Query("SELECT q FROM Qualification q WHERE q.qualificationName = :s and q.code =:s1 ")
Optional<Qualification> findByQualification(String s,String s1);

@Query("SELECT q FROM Qualification q WHERE q.qualificationName = :s and q.code =:s1 ")
Optional<Qualification> findByQualificationCode(String s,String s1);

 @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualification q WHERE  q.code = :s")
boolean  existsByCode(String s);


}
