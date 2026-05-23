package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qualification")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_code_id", nullable = false, updatable = false)
    private Integer qualificationCodeId;

    @Column(name = "qualification_name")
    private String qualificationName;

    @Column(name = "code")
    private String code;

    @Column(name = "qualification_short_description")
    private String qualificationShortDescription;


    @Column(name = "code_active", length = 1)
    private String codeActive = "Y";   // default value

    @Column(name = "code_active_from")
    private LocalDate codeActiveFrom = LocalDate.now();  // default current date

    @Column(name = "create_timestamp", updatable = false)
    private LocalDateTime createdTimestamp = LocalDateTime.now();  // default current timestamp

    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp = LocalDateTime.now();  // default current timestamp

    // --- Constructors ---
    public Qualification() {}

    // --- Getters and Setters ---
    public Integer getQualificationCodeId() {
        return qualificationCodeId;
    }

    public void setQualificationCodeId(Integer qualificationCodeId) {
        this.qualificationCodeId = qualificationCodeId;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

public String getQualificationShortDescription() {
        return qualificationShortDescription;
    }

    public void setQualificationShortDescription(String qualificationShortDescription) {
        this.qualificationShortDescription = qualificationShortDescription;
    }



    public String getCodeActive() {
        return codeActive;
    }

    public void setCodeActive(String codeActive) {
        this.codeActive = codeActive;
    }

    public LocalDate getCodeActiveFrom() {
        return codeActiveFrom;
    }

    public void setCodeActiveFrom(LocalDate codeActiveFrom) {
        this.codeActiveFrom = codeActiveFrom;
    }

    public LocalDateTime getCreatedTimestamp() {
    return createdTimestamp;
}

public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
}

public LocalDateTime getUpdateTimestamp() {
    return updateTimestamp;
}

public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
}

}