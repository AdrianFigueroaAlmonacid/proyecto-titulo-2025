package com.food_easy_back.backend_food_easy.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_easy_back.backend_food_easy.config.JwUtil;
import com.food_easy_back.backend_food_easy.model.dto.LoginRequestDto;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public final JwUtil jwUtil;
    public final AuthenticationManager authenticationManager;

    

    public AuthController(JwUtil jwUtil, AuthenticationManager authenticationManager) {
        this.jwUtil = jwUtil;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping
    public ResponseEntity<?> LogIn(@RequestBody LoginRequestDto login){

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());

            Authentication authentication = this.authenticationManager.authenticate(token);

            System.out.println("Usuario autenticado: " + authentication.getPrincipal());

            String jwt = this.jwUtil.create(login.getUsername());

            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION,jwt).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
