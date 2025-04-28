package com.food_easy_back.backend_food_easy.service.StoreServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dao.StoreDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.StoreCreateDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.IStoreService;


@Service
public class StoreServiceImpl implements IStoreService{

    private final StoreDao storedao;
    private UserDao userDao;

    
    @Autowired
    public StoreServiceImpl(StoreDao storedao, UserDao userDao) {
        this.storedao = storedao;
        this.userDao = userDao;
    }

    @Override
    public StoreEntity saveStore(StoreCreateDto storedto) {

        String name = storedto.getName();
        if(existStore(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El negocio ya existe" );
        }
        StoreEntity storeEntity= StoreEntity.builder()
                                            .name(storedto.getName())
                                            .build();

        return storedao.save(storeEntity);
    }


    @Override
    public StoreEntity findById(Integer id) {
        return storedao.findById(id).orElseThrow(null);
    }

    @Override
    public void delete() {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();

        storedao.delete(store);
    }

    @Override
    public boolean existStore(String name) {
        if(storedao.findByName(name).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public StoreEntity findByName(String name) {
        StoreEntity storentity = storedao.findByName(name).orElseThrow(null);
        return storentity;
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
