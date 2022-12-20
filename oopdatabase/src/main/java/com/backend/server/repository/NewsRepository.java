package com.backend.server.repository;


import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.server.models.NewsModel;

public interface NewsRepository extends CrudRepository<NewsModel, String> {


    @Query(value = "select * from news order by created_at desc", nativeQuery = true)
    Iterable<NewsModel> findAllNews();

    @Query(value = "SELECT * FROM news WHERE news_id = ?1", nativeQuery = true)
    NewsModel findByNews_id(String id);

    @Modifying
    @Transactional
    @Query(value = "insert into news(news_id, title, description, created_at, update_at, user_id) values(?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void saveNewsModel(String news_id, String title, String description, LocalDateTime created_at, LocalDateTime update_at, String user_id);

    @Modifying
    @Transactional
    @Query(value = "delete from news where news_id = ?1", nativeQuery = true)
    void deleteByNews_id(String id);

    @Modifying
    @Transactional
    @Query(value = "update news set title = ?1, description = ?2, update_at = ?3 where news_id = ?4", nativeQuery = true)
    void updateNewsModel(String title, String description, LocalDateTime update_at, String id);
}   
    
