package com.backend.server.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UsersModel {
    @Id
    private String user_id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<NewsModel> newsModel;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MedicineModel> medicineModels;

    public UsersModel() {
        this.user_id = UUID.randomUUID().toString();
    }
}
