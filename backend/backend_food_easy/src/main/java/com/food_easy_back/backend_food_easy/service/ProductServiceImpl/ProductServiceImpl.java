package com.food_easy_back.backend_food_easy.service.ProductServiceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dao.ProductDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductExpiringDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductLowDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSaveDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSellDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.ICategoryService;
import com.food_easy_back.backend_food_easy.service.IProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class ProductServiceImpl implements IProductService {

    private ProductDao productDao ;
    private ICategoryService categoryService;
    private UserDao userDao;

    
    @Autowired
    public ProductServiceImpl(ProductDao productDao, ICategoryService categoryService, UserDao userDao) {
        this.productDao = productDao;
        this.categoryService = categoryService;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public ProductEntity saveProduct(ProductSaveDto productDto) {



        CategoryEntity category = categoryService.findById(productDto.getCategory());


        ProductEntity product = ProductEntity.builder()
                                            .name(productDto.getName())
                                            .price(productDto.getPrice())
                                            .quantity(productDto.getQuantity())
                                            .category(category)
                                            .expirationDate(productDto.getExpirationDate())
                                            .build();
        return productDao.save(product);
    }

    @Transactional
    @Override
    public ProductEntity updateProduct(ProductUpdateDto productDto) {



        CategoryEntity category = categoryService.findById(productDto.getCategory());


        ProductEntity product = ProductEntity.builder()
                                            .idProduct(productDto.getId())
                                            .name(productDto.getName())
                                            .price(productDto.getPrice())
                                            .quantity(productDto.getQuantity())
                                            .category(category)
                                            .build();
        return productDao.save(product);
    }



    @Override
    public void deleteProduct(Long id) {
        
        ProductEntity product = productDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        CategoryEntity category = product.getCategory();

        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado para hacer la acción."));

        if(category.getStore().equals(user.getStore())){
            productDao.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo puedes eliminar productos de tu negocio." );
        }
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }



    @Transactional
    @Override
    public ProductEntity sellProduct(ProductSellDto productDto) {
        ProductEntity product = productDao.findById(productDto.getId()).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        if((product.getQuantity() - productDto.getQuantity())<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes vender mas productos de los que hay en stock." );
        }else{
            product.setQuantity(product.getQuantity()-productDto.getQuantity());
            return productDao.save(product);
        }
    }




    @Override
    public Page<ProductEntity> getProductsByCategory(String category,Pageable pageable) {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();
        List<CategoryEntity> categories = store.getCategories();
        CategoryEntity categoryFinal = null;
        
        for(CategoryEntity cat: categories){
            if(cat.getName().equals(category)){
                categoryFinal = cat;
                break;
            }

        }
        if(categoryFinal==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la categoria en tu negocio" );
        }

        return productDao.findAllByCategory(categoryFinal, pageable);
    }

    @Override
    public Integer showCountLowProducts() {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();
        Integer id = store.getIdStore();
        return productDao.showCountLowProducts(id);
    }

    @Override
    public Integer countExpiringSoon() {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();
        Integer id = store.getIdStore();
        LocalDate limit = LocalDate.now().plusDays(5);
        System.out.println(LocalDate.now().plusDays(5));
        System.out.println(limit);
        return productDao.countExpiringSoon(limit, id);
    }

    @Override
    public List<ProductLowDto> showLowProducts() {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();
        Integer id = store.getIdStore();
        return productDao.showLowProducts(id);
    }

    @Override
    public List<ProductExpiringDto> showExpiringProducts() {
        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado"));
        StoreEntity store = user.getStore();
        Integer id = store.getIdStore();
        LocalDate limit = LocalDate.now().plusDays(5);
        System.out.println(LocalDate.now().plusDays(5));
        System.out.println(limit);
        return productDao.ExpiringSoon(limit, id);
    }


}
