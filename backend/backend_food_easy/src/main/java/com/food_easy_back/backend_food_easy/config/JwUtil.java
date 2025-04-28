package com.food_easy_back.backend_food_easy.config;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Component
public class JwUtil {

    //Clave secreta de jwt-app
    private static String SECRET_KEY = "user-app";

    //Algoritmo de codificacion
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    //Metodo para crear un jwt con un username
    public String create(String username, List<String> roles){

        return JWT.create()
                .withSubject(username)
                .withIssuer("user-app")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(15)))
                .withClaim("roles", roles)
                .sign(ALGORITHM);
    }

    //Metodo para verificar si un jwt es valido
    public boolean isValid(String jwt){

        try {
            JWT.require(ALGORITHM)
            .build()
            .verify(jwt);

            return true;
            
        } catch (JWTVerificationException e) {
            return false;
        }

    }

    //Metodo para devolver el username del jwt
    public String getUserName(String jwt){

        return JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt)
                    .getSubject();

    }

}
