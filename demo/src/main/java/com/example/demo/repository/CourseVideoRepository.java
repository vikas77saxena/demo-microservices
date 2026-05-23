package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CourseVideo;

public interface CourseVideoRepository extends JpaRepository<CourseVideo, Integer> {



    Optional<CourseVideo> findByVideoUrl(String videoUrl);

}

