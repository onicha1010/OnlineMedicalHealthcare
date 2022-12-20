package com.backend.server.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.server.models.UsersModel;



public interface UsersRepository extends CrudRepository<UsersModel, String> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    UsersModel findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    UsersModel findByUser_id(String id);

    @Modifying
    @Transactional
    @Query(value = "insert into users(user_id, email, password) values(?1, ?2, ?3)", nativeQuery = true)
    void createUser(String user_id, String email, String password);
}
