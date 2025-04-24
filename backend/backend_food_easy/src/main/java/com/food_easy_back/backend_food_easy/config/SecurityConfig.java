package com.food_easy_back.backend_food_easy.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwFilter jwFilter;

    

    @Autowired
    public SecurityConfig(JwFilter jwFilter) {
        this.jwFilter = jwFilter;
    }


    //Metodo que hace de security filter chain
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(customizeRequests -> {
                customizeRequests
                        .requestMatchers("/api/v1/auth").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/user").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/user/store").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/user/admin").hasRole("ADMIN_SYSTEM")
                        .requestMatchers(HttpMethod.POST,"/api/v1/user").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/store").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/product").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{id}").hasAnyRole("OWNER", "ADMIN")
                        .anyRequest()
                        .authenticated();
                }
                )
            .addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }


    //Metodo para codificar el password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Metodo para autenticar al usuario
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
