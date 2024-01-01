package com.bikkadit.electoronic.store.Services;

import com.bikkadit.electoronic.store.model.Cart;
import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.AddItemToCartRequest;
import com.bikkadit.electoronic.store.payload.CartDto;
import com.bikkadit.electoronic.store.repository.CartRepository;
import com.bikkadit.electoronic.store.repository.ProductRepository;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.CartService;
import com.bikkadit.electoronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class CartServiceTest {

    @MockBean
    public CartRepository cartRepository;

    @Autowired
    public CartService cartService;

    @Autowired
    public ModelMapper modelMapper;

    @MockBean
    public ProductRepository productRepository;

    @MockBean
    public UserRepository userRepository;


    private Cart cart;
    private Cart updatedCart;

    private User user;

    private Product product;

    @BeforeEach
    public void init() {
        cart = Cart.builder()
                .cartId("wswsahfkaaah")
                .createdAt(new Date())
                .items(new ArrayList<>())
                .user(user)
                .build();

        updatedCart = Cart.builder()
                .cartId("wswsahfkaaah")
                .createdAt(new Date())
                .items(new ArrayList<>())
                .user(user)
                .build();
        user = User.builder()
                .name("Sujit")
                .email("SujitPatil3066@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("Sujit@8878")
                .build();

        product = Product.builder().productId("Abc123")
                .title("Apple 14 pro Max")
                .description("It Introduce In 2022")
                .price(15000)
                .discountedPrice(14000)
                .quantity(15)
                .live(true)
                .stock(false)
                .image("Apple.png")
                .build();
    }
//
//    @Test
//    public void addItemToCart() {
//
//        String userId = "sujit123edlaj";
//        Integer quantity = 10;
//        String productId = "45454asa0";
//
//       AddItemToCartRequest addItemToCartRequest= new AddItemToCartRequest(productId,quantity);
//
//
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
//
////        CartDto expectedCartDto = new CartDto();
////        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(CartDto.class))).thenReturn(expectedCartDto);
//
//        Mockito.when(cartRepository.save(cart)).thenReturn(updatedCart);
//
//        CartDto result = this.cartService.addItemToCart(userId,addItemToCartRequest );
//
////        Assertions.assertNotNull(result);
//
//        Assertions.assertEquals(result.getItems().size(),quantity);
//
//
//
//

//    }


}
