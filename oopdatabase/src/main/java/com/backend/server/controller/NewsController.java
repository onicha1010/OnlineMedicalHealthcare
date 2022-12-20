package com.backend.server.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.server.middleware.TokenHandler;
import com.backend.server.models.NewsModel;
import com.backend.server.models.UsersModel;
import com.backend.server.repository.NewsRepository;
import com.backend.server.repository.UsersRepository;
@CrossOrigin
@RestController
@RequestMapping(value = "/api", consumes = "application/json")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping(value="/news")
    public Object getAllNews() {
        return newsRepository.findAllNews();
    }

    @GetMapping(value="/news/{id}")
    public NewsModel getNewsById(@PathVariable("id") String id) {
        return newsRepository.findByNews_id(id);
    }

    @PostMapping(value="/news")
    public void postNews(@RequestHeader("Authorization") String bearerToken, @RequestBody NewsModel entity) {
        bearerToken = bearerToken.substring(7);
        DecodedJWT verfily = new TokenHandler().verifyToken(bearerToken);
        UsersModel usersModel = usersRepository.findByUser_id(verfily.getAudience().get(0));
    
        newsRepository.saveNewsModel(entity.getNews_id(), entity.getTitle(), entity.getDescription(), LocalDateTime.now(), LocalDateTime.now(), usersModel.getUser_id());
    }

    @PutMapping(value="/news/{id}")
    public void putNews(@RequestBody NewsModel entity,@PathVariable String id) {
        newsRepository.updateNewsModel(entity.getTitle(), entity.getDescription(), LocalDateTime.now(), id);
    }


    @DeleteMapping(value="/news/{id}")
    public void deleteNews(@PathVariable String id) {
        newsRepository.deleteById(id);
    }
}
