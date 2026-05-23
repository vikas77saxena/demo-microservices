package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coursedetails")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false, updatable = false)
    private Integer courseId;

    @Column(name = "edp_id")
    private Integer edpId;

    @Column(name = "qualification_id")
    private Integer qualificationId;

    @Column(name = "video_id")
    private Integer videoId;

    @Column(name = "create_timestamp", updatable = false)
    private LocalDateTime createTimestamp = LocalDateTime.now();

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp = LocalDateTime.now();

    // --- Constructors ---
    public CourseDetails() {}

    // --- Getters and Setters ---
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getEdpId() {
        return edpId;
    }

    public void setEdpId(Integer edpId) {
        this.edpId = edpId;
    }

    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(LocalDateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    // --- Lifecycle Hooks ---
    @PrePersist
    protected void onCreate() {
        createTimestamp = LocalDateTime.now();
        updateTimestamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTimestamp = LocalDateTime.now();
    }
}