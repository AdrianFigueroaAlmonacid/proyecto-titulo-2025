package com.food_easy_back.backend_food_easy.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dto.product.ProductExpiringDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductListDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductLowDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSaveDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSellDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductUpdateDto;
import com.food_easy_back.backend_food_easy.model.dto.product.SaleListDto;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;
import com.food_easy_back.backend_food_easy.model.entity.SaleEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.IProductService;
import com.food_easy_back.backend_food_easy.service.ISaleService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;
    private final ISaleService saleService;

    

    @Autowired
    public ProductController(IProductService productService, ISaleService saleService) {
        this.productService = productService;
        this.saleService = saleService;

        
    }

    @GetMapping
    public ResponseEntity<?> getProducts(Pageable pageable){
        try {

            Page<ProductEntity> productsPage = productService.getProducts( pageable);
            Page<ProductListDto> dtoPage = productsPage.map(u -> ProductListDto.builder()
                                                                .categoryChange(u.getCategory().getIdCategory())
                                                                .category(u.getCategory().getName())
                                                                .name(u.getName())
                                                                .price(u.getPrice())
                                                                .id(u.getIdProduct())
                                                                .quantity(u.getQuantity())
                                                                .expirationDate(u.getExpirationDate())
                                                                .build());
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos recuperados correctamente")
                                .object(dtoPage)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    //Metodo get para obtener productos con su categoria
    @GetMapping("/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category,Pageable pageable){
        try {

            Page<ProductEntity> productsPage = productService.getProductsByCategory(category, pageable);
            Page<ProductListDto> dtoPage = productsPage.map(u -> ProductListDto.builder()
                                                                .categoryChange(u.getCategory().getIdCategory())
                                                                .category(u.getCategory().getName())
                                                                .name(u.getName())
                                                                .price(u.getPrice())
                                                                .id(u.getIdProduct())
                                                                .quantity(u.getQuantity())
                                                                .expirationDate(u.getExpirationDate())
                                                                .build());
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos recuperados correctamente")
                                .object(dtoPage)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener la lista de productos con bajo stock
    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowProducts(){
        try {

            
            List<ProductLowDto> low = productService.showLowProducts();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos con bajo stock recuperados correctamente")
                                .object(low)
                                .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos con bajo stock")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener la lista de productos a punto de vencer
    @GetMapping("/expiring-soon")
    public ResponseEntity<?> expiringSoonProducts(){
        try {

            
            List<ProductExpiringDto> near= productService.showExpiringProducts();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Cantidad de productos con fecha proxima a expirar recuperados correctamente")
                                .object(near)
                                .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos proximos a expirar")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener el conteo de productos con bajo stock
    @GetMapping("/low-stock/count")
    public ResponseEntity<?> getLowProductsCount(){
        try {

            
            Integer low = productService.showCountLowProducts();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Cantidad de productos con bajo stock recuperados correctamente")
                                .object(low)
                                .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos con bajo stock")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener la lista de productos con bajo stock
    @GetMapping("/expiring-soon/count")
    public ResponseEntity<?> expiringSoonProductsCount(){
        try {

            
            Integer near= productService.countExpiringSoon();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Cantidad de productos con fecha proxima a expirar recuperados correctamente")
                                .object(near)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos proximos a expirar")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener la lista de productos vendidos en el mes
    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredProducts(){
        try {
            List<ProductExpiringDto> products = productService.showExpiredProducts();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos expirados obtenidos")
                                .object(products)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos proximos a expirar")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    //Endpoint para obtener la lista de productos vendidos en el mes
    @GetMapping("/sale/all")
    public ResponseEntity<?> getSellProducts(){
        try {
            LocalDate fechaActual = LocalDate.now();

            int month = fechaActual.getMonthValue();
            int year = fechaActual.getYear();
            List<SaleListDto> saleList = saleService.findSalesInMonth(month, year);
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos vendidos este mes")
                                .object(saleList)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar productos proximos a expirar")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    //Endpoint para guardar producto
    @PostMapping
    public ResponseEntity <?> saveProduct(@RequestBody ProductSaveDto productDto ){
        try{
            ProductEntity product = productService.saveProduct(productDto);
            ProductSaveDto productdto = ProductSaveDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto guardado correctamente")
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }


    //Endpoint para vender un producto
    @PostMapping("/sell")
    public ResponseEntity <?> sellProduct(@RequestBody ProductSellDto productDto ){
        try{
            ProductEntity product = productService.sellProduct(productDto);

            saleService.saveSale(SaleEntity.builder()
                                        .quantity(productDto.getQuantity())
                                        .product(product)
                                        .price(productDto.getQuantity()*product.getPrice())
                                        .build());
            ProductSaveDto productdto = ProductSaveDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .quantity(productDto.getQuantity())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto vendido correctamente por la cantidad de: " + productDto.getQuantity())
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(EntityNotFoundException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al vender producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    //Endpoint para modificar un producto
    @PutMapping
    public ResponseEntity <?> updateProduct(@RequestBody ProductUpdateDto productDto ){
        try{
            ProductEntity product = productService.updateProduct(productDto);
            ProductSaveDto productdto = ProductSaveDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto actualizado correctamente")
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al actualizar producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }


    //Endpoint para eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity <?>  deleteProduct (@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto eliminado correctamente")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(EntityNotFoundException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al eliminar producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
