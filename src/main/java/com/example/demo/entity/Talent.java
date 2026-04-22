package com.example.demo.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import lombok.Data;

@Entity
@Data
public class Talent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String talentName;
    private String reason;
    private String createdBy;
    
    // DB側で自動生成されるため、Java側からは書き込まない設定
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @PrePersist
    public void onPrePersist() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
