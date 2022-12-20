package com.backend.server.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "medicine")
public class MedicineModel {
    @Id
    private String medicine_id;

    @Column
    private String title;

    @Column
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime update_at;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;

    public MedicineModel() {
        this.medicine_id = UUID.randomUUID().toString();
    }
}
