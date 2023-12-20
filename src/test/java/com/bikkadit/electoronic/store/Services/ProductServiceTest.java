package com.bikkadit.electoronic.store.Services;

import com.bikkadit.electoronic.store.model.Category;
import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.repository.CategoryRepository;
import com.bikkadit.electoronic.store.repository.ProductRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
            private CategoryRepository categoryRepository;

    Product product;

    Product product2;
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

        product2=Product.builder()
                .productId("123")
                .title("Apple 12 pro Max")
                .description("It Introduce In 2021")
                .price(90000.00)
                .discountedPrice(80000.00)
                .quantity(10)
                .live(false)
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

    String id="123";
    
    Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));

        ProductDto product = this.productService.getSingleProduct(id);

        Assertions.assertNotNull(product);

        Assertions.assertEquals(product.getProductId(),product.getProductId());
    }

    @Test
    public void deleteProductTest(){
        String id="123";

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));

        this.productService.deleteProduct(id);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }

    @Test
    public void updateProductTest(){


        ProductDto product1 = ProductDto.builder()
                .productId("123")
                .image("Apple.png")
                .live(true)
                .stock(true)
                .description("It Contain All Apple Phone")
                .title("ApplePhones")
                .price(15000.00)
                .discountedPrice(10000.00)
                .build();

        String id="121";

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductDto productDto = this.productService.updateProduct(id, product1);
        Assertions.assertNotNull(productDto);

        Assertions.assertEquals(productDto.getTitle(),product.getTitle());


    }

    @Test
    public void getAllProductsTest(){
        


        List<Product> products = Arrays.asList(product, product2);
        Page<Product> page=new PageImpl<>(products);

        Mockito.when(this.productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(1, 10, "title", "desc");
        Assertions.assertNotNull(allProducts);
        Assertions.assertEquals(2,allProducts.getContent().size());


    }

    @Test
    public void findByliveTrueTest(){

        List<Product> products = Arrays.asList(product, product2);
        Page<Product> page=new PageImpl<>(products);

        Mockito.when(this.productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allProducts = this.productService.findByliveTrue(1, 10, "title", "desc");
        Assertions.assertNotNull(allProducts);
        List<ProductDto> collect = allProducts.getContent().stream().filter(e -> e.getLive()==true).collect(Collectors.toList());
        Assertions.assertNotNull(collect);

//        Assertions.assertEquals(true,product.getLive());
//        Assertions.assertEquals(true,product2.getLive());

    }

//    @Test
//    public void getAllLIveProductsTest(){
//
//    }
    @Test
    public void getProductByTitleTest(){

        String keyword="Apple";

        List<Product> products = Arrays.asList(product, product2);
        Page<Product> page=new PageImpl<>(products);

       Mockito.when(productRepository.findByTitleContaining(Mockito.any(), Mockito.anyString())).thenReturn(page);
        PageableResponse<ProductDto> allProducts = this.productService.getProductByTitle(keyword,1, 10, "title", "desc");
        Assertions.assertNotNull(allProducts);
        Assertions.assertEquals(2,allProducts.getContent().size());

    }

//    @Test
//    public void createProductWithCategoryTest(){
//
//        String categoryId="12334";
//
//        Category category=Category.builder()
////                .categoryId(categoryId)
//                .title("Mi")
//                .description("This Is Xiomi")
//                .coverImage("MI.png").build();
//
//
//
//        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//
//        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
//
//        ProductDto productDto = this.productService.saveProduct(modelMapper.map(product, ProductDto.class));
//
//        productDto.setCategories(modelMapper.map(category,CategoryDto.class));
//        ProductDto productDto1 = this.productService.saveProduct(productDto);
//        Assertions.assertNotNull(productDto1);

//        Assertions.assertEquals("Abc123",productDto.getProductId());
//        Assertions.assertEquals(categoryId,productDto.getCategories().getCategoryId());


    }

























