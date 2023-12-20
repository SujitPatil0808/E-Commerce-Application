package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.ImageResponse;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.service.FileService;
import com.bikkadit.electoronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Path;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {


    @Autowired
    private ProductService productService;

    @Value("${product.profile.image.path}")
    private String path;

    @Autowired
    private FileService fileService;

    /**
     * @Autor Sujit Patil
     * @apiNote save Product Into Database
     * @param productDto
     * @return ProductDto
     * @since 1.0v
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Enter the  request for Save the Product : {}",productDto);
        ProductDto productDto1 = this.productService.saveProduct(productDto);
        log.info("Completed the  request for Save the Product : {}",productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    /**
     * @author Sujit Patil
     * @apiNote get product With userId
     * @param productId
     * @return productDto
     * @since 1.0v
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        log.info("Enter the  request for Get the Product With Product Id : {}",productId);
        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        log.info("Completed the  request for Get the Product With Product Id : {}",productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote delete product with productId
     * @param productId
     * @return
     * @since 1.0v
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) {
        log.info("Enter the  request for Delete  the Product with Product Id : {}",productId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.DELETE).status(true).statusCode(HttpStatus.OK).build();
        this.productService.deleteProduct(productId);
        log.info("Completed the  request for Delete  the Product with Product Id : {}",productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    /**
     * @author Sujit Patil
     * @apiNote update product with productId
     * @param productId
     * @param dto
     * @return
     * @since 1.0v
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDto dto) {
        log.info("Enter the  request for Update  the Product with Product Id : {}",productId);
        ProductDto productDto = this.productService.updateProduct(productId, dto);
        log.info("Completed the  request for Update  the Product with Product Id : {}",productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get All Products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0v
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction

    ) {
        log.info("Enter the  request for Get All Products : ");
        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All Products : ");
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }

    /**
     * @author Sujit Patil
     * @apiNote get All Live True Products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0v
     */
    @GetMapping("/trueLiveProducts")
    public ResponseEntity<PageableResponse> findAllLiveTrueProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
    ) {
        log.info("Enter the  request for Get All Live True  Products : ");
        PageableResponse<ProductDto> allLIveProducts = this.productService.findByliveTrue(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All Live True  Products : ");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get All Live Products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0v
     */

    @GetMapping("/liveProducts")
    public ResponseEntity<PageableResponse> getLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
    ) {
        log.info("Enter the  request for Get All Live   Products : ");
        PageableResponse<ProductDto> allLIveProducts = this.productService.getAllLIveProducts(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All Live   Products : ");
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);

    }

    /**
     * @auhor Sujit Patil
     * @apiNote get products Containing Keyword
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @param keyword
     * @return
     * @since 1.0v
     */
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse> getProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
            ,@PathVariable String keyword ){
        log.info("Enter the  request for Get All  Products With Keyword :{} ",keyword);
        PageableResponse<ProductDto> productByTitle = this.productService.getProductByTitle(keyword, pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All  Products With Keyword :{} ",keyword);
        return new ResponseEntity<>(productByTitle,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote upload Image for Product with productId
     * @param image
     * @param productId
     * @return
     * @throws IOException
     * @since 1.0v
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image,@PathVariable String productId) throws IOException {
        log.info("Enter the  request for Upload Image With ProductId :{} ",productId);
        String file = this.fileService.uploadFile(image, path);

        ProductDto product = this.productService.getSingleProduct(productId);

        product.setImage(file);

        ProductDto updatedProduct = this.productService.updateProduct(productId, product);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();
        log.info("Completed the  request for Upload Image With ProductId :{} ",productId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    /**
     * @author Sujit Patil
     * @apiNote get Image with UserId
     * @param productId
     * @param response
     * @throws IOException
     * @since 1.0v
     */
    @GetMapping("/image/{productId}")
    public void getProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        log.info("Enter the  request for Get Image With ProductId :{} ",productId);
        ProductDto product = this.productService.getSingleProduct(productId);
        InputStream resource = this.fileService.getResource(path, product.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed the  request for Get Image With ProductId :{} ",productId);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    // Create product with category Id

    /**
     * @author Sujit Patil
     * @apiNote  get All Products With Category Id
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0v
     */
    @GetMapping("/allByCategoryId/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductByCategoryId(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction

    ){
        log.info("Enter the  request for Get All Product With Category Id :{} ",categoryId);
        PageableResponse<ProductDto> allProducts = this.productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All Product With Category Id :{} ",categoryId);

        return new ResponseEntity<>(allProducts,HttpStatus.OK);
     }















































}
