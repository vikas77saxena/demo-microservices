package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="edpcategory")
public class EdpCategory{

    @Id
    private int  edp_id;

    @Column(name = "edp_category")
    private String edp_category;

    @Column(name="edp_active")
    private char edp_active;

    @Column(name = "create_timestamp")
    private LocalDateTime created_at;

    @Column(name = "update_timestamp")
     private LocalDateTime updated_at;

    // Constructors
    public EdpCategory() {
    }
    
    // JPA lifecycle callbacks for automatic timestamp management
    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    // Getters and Setters
    public int getEdp_id() {
        return edp_id;
    }

    public void setEdp_id(int edp_id) {
        this.edp_id = edp_id;
    }

    public String getEdp_category() {
        return edp_category;
    }

    public void setEdp_category(String edp_category) {
        this.edp_category = edp_category;
    }

    public char getEdp_active() {
        return edp_active;
    }

    public void setEdp_active(char edp_active) {
        this.edp_active = edp_active;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}