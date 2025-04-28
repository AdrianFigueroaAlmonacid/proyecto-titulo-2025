package com.food_easy_back.backend_food_easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.IStoreService;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    private final IStoreService storeService;

    @Autowired
    public StoreController(IStoreService storeService) {
        this.storeService = storeService;
    }

    //AQUI ESTAN LOS DELETE

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        try { 
            storeService.delete();  
            ResponseMessage response = ResponseMessage.builder()
                                        .message("Negocio con usuarios eliminado con exito")
                                        .object(null)
                                        .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al eliminar: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    

}
