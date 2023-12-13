package com.bikkadit.electoronic.store.Services;

import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.repository.ProductRepository;
import com.bikkadit.electoronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    Product product;

    @BeforeEach
    public void init(){
    product=Product.builder()
            .productId("Abc123")
            .title("Apple 14 pro Max")
            .description("It Introduce In 2022")
            .price(15000.00)
            .discountedPrice(14000.00)
            .quantity(15)
            .live(true)
            .stock(false)
            .image("Apple.png")
            .build();
    }


    @Test
    public void saveProductTest(){

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto = this.productService.saveProduct(modelMapper.map(product, ProductDto.class));

        Assertions.assertNotNull(productDto);

        Assertions.assertEquals("Abc123",productDto.getProductId());
    }

    @Test
    public void getSingleProductTest(){

    }

}
