package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "coursevideo", schema = "public")
public class CourseVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "video_duration")
    private int videoDuration;   // or String if stored as text in DB

    @Column(name = "video_lang")
    private int videoLang;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    // Constructors
    public CourseVideo() {}

    public CourseVideo(String videoUrl, int videoDuration, int videoLang) {
        this.videoUrl = videoUrl;
        this.videoDuration = videoDuration;
        this.videoLang = videoLang;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getVideoLang() {
        return videoLang;
    }

    public void setVideoLang(int videoLang) {
        this.videoLang = videoLang;
    }

    public LocalDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    
}
