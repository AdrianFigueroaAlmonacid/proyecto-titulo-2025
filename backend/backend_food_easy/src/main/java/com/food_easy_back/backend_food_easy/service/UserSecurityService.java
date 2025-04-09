package com.food_easy_back.backend_food_easy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;


@Service
public class UserSecurityService implements UserDetailsService {

    private final UserDao userDao;


    @Autowired
    public UserSecurityService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String [] roles = user.getRoles().stream().map(UserRoleEntity::getRole).toArray(String []::new);

        return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(this.grantedAuthorities(roles))
                    .accountLocked(user.getLocked())
                    .disabled(user.getDisabled())
                    .build();
    }

    private String[] getAuthorities(String role) {

        switch(role){
            case "ADMIN_SYSTEM":
                return new String[] {"delete_owner","add_owner"};
            case "OWNER":
                return new String [] {"delete_business"};
            case "ADMIN":
                return new String [] {"generate_report", "delete_user","add_user"};
            case "USER":
                return new String [] {"sell_product","add_product","delete_product","edit_product"};
        }
        return new String[0];
    }

    private List<GrantedAuthority> grantedAuthorities(String [] roles ){
        List <GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role: roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority: this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;
    }

}
