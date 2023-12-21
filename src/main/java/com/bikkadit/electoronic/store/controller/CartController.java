package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.CartDto;
import com.bikkadit.electoronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest item){
        CartDto cartDto = this.cartService.addItemToCart(userId, item);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId){
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.DELETE)
                .status(true)
                .statusCode(HttpStatus.OK)
                .build();
        this.cartService.removeItemFromCart(userId,itemId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId){
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.DELETE)
                .status(true)
                .statusCode(HttpStatus.OK)
                .build();
        this.cartService.clearCart(userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){
        CartDto cartByUser = this.cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser, HttpStatus.OK);
    }








}
