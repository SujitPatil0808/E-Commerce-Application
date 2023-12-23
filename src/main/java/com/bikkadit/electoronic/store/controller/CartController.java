package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.CartDto;
import com.bikkadit.electoronic.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest item){
        log.info("Enter the  request for Create the Cart With UserId :{}",userId);
        CartDto cartDto = this.cartService.addItemToCart(userId, item);
        log.info("Completed the  request for Create the Cart With UserId :{}",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId){
        log.info("Enter the  request for Remove the Cart With UserId And ItemId :{} :{}",userId,itemId);
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.DELETE)
                .status(true)
                .statusCode(HttpStatus.OK)
                .build();
        this.cartService.removeItemFromCart(userId,itemId);
        log.info("Completed the  request for Remove the Cart With UserId And ItemId :{} :{}",userId,itemId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId){
        log.info("Enter the  request for Clear the Cart With UserId :{}",userId);
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.DELETE)
                .status(true)
                .statusCode(HttpStatus.OK)
                .build();
        this.cartService.clearCart(userId);
        log.info("Completed the  request for Clear the Cart With UserId :{}",userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){
        log.info("Enter the  request for Get the Cart With UserId :{}",userId);
        CartDto cartByUser = this.cartService.getCartByUser(userId);
        log.info("Completed the  request for Get the Cart With UserId :{}",userId);
        return new ResponseEntity<>(cartByUser, HttpStatus.OK);
    }








}
