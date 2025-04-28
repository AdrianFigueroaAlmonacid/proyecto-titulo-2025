package com.food_easy_back.backend_food_easy.service.SaleServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.SaleDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.product.SaleListDto;
import com.food_easy_back.backend_food_easy.model.entity.SaleEntity;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.ISaleService;

@Service
public class SaleServiceImpl implements ISaleService{

    private final SaleDao saleDao;
    private final UserDao userDao;
    
    @Autowired
    public SaleServiceImpl(SaleDao saleDao, UserDao userDao) {
        this.saleDao = saleDao;
        this.userDao = userDao;
    }



    @Override
    public SaleEntity saveSale(SaleEntity sale) {
        return saleDao.save(sale);
    }

     public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }



     @Override
     public List<SaleListDto> findSalesInMonth(int month, int year) {
        UserEntity user = findByUsername(getCurrentUsername());
        StoreEntity store = user.getStore();
        

        return saleDao.findSalesInMonth(month, year, store.getIdStore());
     }
    
     @Override
    public UserEntity findByUsername(String username) {
         UserEntity user = userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return user;
    }

}
