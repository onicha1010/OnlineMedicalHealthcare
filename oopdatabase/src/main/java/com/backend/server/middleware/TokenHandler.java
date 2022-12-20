package com.backend.server.middleware;


import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenHandler {
    public String generateToken(String email, String role, String audience) {
        Algorithm algorithm = Algorithm.HMAC256("HELLOWORLD");

        String token = JWT.create()
            .withSubject(email)
            .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 )) // 1 hours format + minute * secount/minius * millisecound
            .withIssuedAt(new Date(System.currentTimeMillis()))
            .withIssuer(role)
            .withAudience(audience)
            .sign(algorithm);

        return token;
    }

    public DecodedJWT verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("HELLOWORLD");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        
        return decodedJWT;
    }
}
