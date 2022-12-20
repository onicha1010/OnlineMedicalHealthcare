package com.backend.server.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.server.middleware.TokenHandler;
import com.backend.server.models.UsersModel;
import com.backend.server.repository.UsersRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@CrossOrigin
@RestController
@RequestMapping(value = "/api", consumes = "application/json")
public class UsersController {
    
    @Autowired
    private UsersRepository usersRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @PostMapping(value="/register")
    public void getMethodName(@RequestBody UsersModel usersModel) {

        usersModel.setPassword(passwordEncoder().encode(usersModel.getPassword()));

        usersRepository.createUser(usersModel.getUser_id(), usersModel.getEmail(), usersModel.getPassword());
    }


    @PostMapping(value="/login")
    public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody UsersModel entity) {
        String token;
        UsersModel user = usersRepository.findByEmail(entity.getEmail());
        if(user != null){
            if(passwordEncoder().matches(entity.getPassword(), user.getPassword())){
                token = new TokenHandler().generateToken(entity.getEmail(), "ADMIN", user.getUser_id());
                return ResponseEntity.ok(Map.of("token", token));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/authorization")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String bearerToken) {
        Map<String, Object> res = new HashMap<String, Object>();
        if(bearerToken.length() > 10){
            bearerToken = bearerToken.substring(7);
            try {
                DecodedJWT verfily = new TokenHandler().verifyToken(bearerToken);
                UsersModel userModel = usersRepository.findByUser_id(verfily.getAudience().get(0));
                res.put("status", 200);
                res.put("message", "valid token");
                res.put("user_id", userModel.getUser_id());
                return ResponseEntity.ok(res);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }else {
            res.put("status", 400);
            res.put("message", "token error");
            return ResponseEntity.badRequest().build();
        }
    }
    
}
