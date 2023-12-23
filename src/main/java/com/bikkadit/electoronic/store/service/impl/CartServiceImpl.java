package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.BadApiRequest;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.model.Cart;
import com.bikkadit.electoronic.store.model.CartItem;
import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.CartDto;
import com.bikkadit.electoronic.store.repository.CartItemRepository;
import com.bikkadit.electoronic.store.repository.CartRepository;
import com.bikkadit.electoronic.store.repository.ProductRepository;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        log.info("Entering the Dao call for Add the Item into Cart ");
        Integer quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequest(AppConstants.NOT_VALID_QUANTITY);

        }

        //Find A Product
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        // Find User From Cart
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + userId));

        // We Find Cart From User
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            // If User Has No Cart
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());

            cart.setCreatedAt(new Date());
        }

        // Perform Cart Operation
        // If Cart item Already Present then update
        List<CartItem> items = cart.getItems();

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        items = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //Iteam Already Present in Cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }

            return item;
        }).collect(Collectors.toList());

//        cart.setItems(updatedList);


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

        log.info("Completed the Dao call for Add the Item into Cart ");
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartId) {

        log.info("Entering the Dao call for Remove  the Item From Cart with Cart Id :{}", cartId);
        CartItem cartItem = this.cartItemRepository.findById(cartId).orElseThrow(()
                -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        log.info("Completed the Dao call for Remove  the Item From Cart with Cart Id :{}", cartId);
        cartItemRepository.delete(cartItem);

    }

    @Override
    public void clearCart(String userId) {

        log.info("Entering the Dao call for Clear Cart with UserId :{}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));


        log.info("Completed the Dao call for Clear Cart with UserId :{}", userId);
        cart.getItems().clear();

        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        log.info("Entering the Dao call for Get Cart with UserId  :{}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        log.info("Completed the Dao call for Get Cart with UserId  :{}", userId);
        return modelMapper.map(cart, CartDto.class);
    }
}
