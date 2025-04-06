package com.food_easy_back.backend_food_easy.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.UserDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public UserEntity save(UserDto userdto) {

        UserEntity user = UserEntity.builder()
                         .idUser(userdto.getIdUser())
                         .name(userdto.getName())
                         .lastName(userdto.getLastName())
                         .email(userdto.getEmail())
                         .password(userdto.getPassword())
                         .username(userdto.getUsername())
                         .build();
        return userDao.save(user);
    }

    @Transactional
    @Override
    public UserEntity findById(Integer id) {
        return userDao.findById(id).orElse(null);
        
    }

    @Transactional
    @Override
    public void delete(UserEntity user) {
        userDao.delete(user);
    }

    @Transactional
    @Override
    public boolean existUsername(String username){
        if(userDao.findByUsername(username).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
         UserEntity user = findByUsername(username);
        return user;
    }



}
