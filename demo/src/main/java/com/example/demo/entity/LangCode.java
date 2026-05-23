package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "langcode")
public class LangCode {

    @Id
    private int langcode_id;

    @Column(name = "langcode")
    private String langCode;

    @Column(name = "langcode_des")
    private String langcodeDes;
    @CreationTimestamp
    @Column(name = "create_timestamp")
    private LocalDateTime createTimestamp;
    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;


    // Constructors
    public LangCode() {
    }
    
   

  
// Getter and Setter for langcode_id
    public int getLangcode_id() {
        return langcode_id;
    }

    public void setLangcode_id(int langcode_id) {
        this.langcode_id = langcode_id;
    }

    // Getter and Setter for langCode
    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    // Getter and Setter for langcodeDes
public String getLangcodeDes() {
    return langcodeDes;
}

public void setLangcodeDes(String langcodeDes) {
    this.langcodeDes = langcodeDes;
}

// Getter and Setter for createTimestamp
public LocalDateTime getCreateTimestamp() {
    return createTimestamp;
}

public void setCreateTimestamp(LocalDateTime createTimestamp) {
    this.createTimestamp = createTimestamp;
}

// Getter and Setter for updateTimestamp
public LocalDateTime getUpdateTimestamp() {
    return updateTimestamp;
}

public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
}



}

    