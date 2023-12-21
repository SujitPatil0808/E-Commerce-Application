package com.bikkadit.electoronic.store.service;

import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    void removeItemFromCart(String userId,Integer cartId);

    void clearCart(String userId);



}
