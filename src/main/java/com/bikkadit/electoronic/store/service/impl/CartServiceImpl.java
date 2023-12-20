package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.model.Cart;
import com.bikkadit.electoronic.store.model.CartItem;
import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.CartDto;
import com.bikkadit.electoronic.store.repository.CartRepository;
import com.bikkadit.electoronic.store.repository.ProductRepository;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        Integer quantity = request.getQuantity();
        String productId = request.getProductId();
        //Find A Product
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        // Find User From Cart
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + userId));
        // We Find Cart From User

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        // Perform Cart Operation
        // If Cart item Already Present then update
        List<CartItem> items = cart.getItems();

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> updatedList = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //Iteam Already Present in Cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }

            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedList);

        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }


        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);


        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, String cartId) {



    }

    @Override
    public void clearCart(String userId) {

    }
}
