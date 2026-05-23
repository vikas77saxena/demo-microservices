package com.example.demo.dto;

public class CourseDto {

    private String typeOfEdp;
    private int edpId;
    private String qualificationCodeOld;
    private String qualificationCodeUpdated;
    private String qualificationName;
    private String shortDescription;
    private String aboutVideoUrl;
    private int duration;       // duration in minutes (or seconds depending on your use case)
    private String language;

    // Constructors
    public CourseDto() {}

    public CourseDto(String typeOfEdp, int  edpId, String qualificationCodeOld,
                  String qualificationCodeUpdated, String qualificationName,
                  String shortDescription, String aboutVideoUrl,
                  int duration, String language) {
        this.typeOfEdp = typeOfEdp;
        this.edpId = edpId;
        this.qualificationCodeOld = qualificationCodeOld;
        this.qualificationCodeUpdated = qualificationCodeUpdated;
        this.qualificationName = qualificationName;
        this.shortDescription = shortDescription;
        this.aboutVideoUrl = aboutVideoUrl;
        this.duration = duration;
        this.language = language;
    }

    // Getters and Setters
    public String getTypeOfEdp() { return typeOfEdp; }
    public void setTypeOfEdp(String typeOfEdp) { this.typeOfEdp = typeOfEdp; }

    public int getEdpId() { return edpId; }
    public void setEdpId(int edpId) { this.edpId = edpId; }

    public String getQualificationCodeOld() { return qualificationCodeOld; }
    public void setQualificationCodeOld(String qualificationCodeOld) { this.qualificationCodeOld = qualificationCodeOld; }

    public String getQualificationCodeUpdated() { return qualificationCodeUpdated; }
    public void setQualificationCodeUpdated(String qualificationCodeUpdated) { this.qualificationCodeUpdated = qualificationCodeUpdated; }

    public String getQualificationName() { return qualificationName; }
    public void setQualificationName(String qualificationName) { this.qualificationName = qualificationName; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getAboutVideoUrl() { return aboutVideoUrl; }
    public void setAboutVideoUrl(String aboutVideoUrl) { this.aboutVideoUrl = aboutVideoUrl; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}