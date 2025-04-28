package com.food_easy_back.backend_food_easy.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

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
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;
import com.food_easy_back.backend_food_easy.service.IUserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public final JwUtil jwUtil;
    public final AuthenticationManager authenticationManager;
    private final IUserService userService;


    public AuthController(JwUtil jwUtil, AuthenticationManager authenticationManager, IUserService userService) {
        this.jwUtil = jwUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }



    @PostMapping
    public ResponseEntity<?> LogIn(@RequestBody LoginRequestDto login){

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());

            Authentication authentication = this.authenticationManager.authenticate(token);

            System.out.println("Usuario autenticado: " + authentication.getPrincipal());

            List<UserRoleEntity> userRole = userService.findByUsername(login.getUsername()).getRoles();

            List<String> roleNames = new ArrayList<>();
            for (UserRoleEntity role : userRole) {
                roleNames.add(role.getRole());
            }

            String jwt = this.jwUtil.create(login.getUsername(),roleNames);

            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION,jwt).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
