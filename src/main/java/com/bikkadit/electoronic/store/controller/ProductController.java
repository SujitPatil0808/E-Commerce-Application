package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.ImageResponse;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.service.FileService;
import com.bikkadit.electoronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Value("${product.profile.image.path}")
    private String path;

    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto productDto1 = this.productService.saveProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        ProductDto singleProduct = this.productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) {

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.DELETE).status(true).statusCode(HttpStatus.OK).build();
        this.productService.deleteProduct(productId);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId, @RequestBody ProductDto dto) {

        ProductDto productDto = this.productService.updateProduct(productId, dto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction

    ) {

        PageableResponse<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }

    @GetMapping("/trueLiveProducts")
    public ResponseEntity<PageableResponse> findAllLiveTrueProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
    ) {

        PageableResponse<ProductDto> allLIveProducts = this.productService.findByliveTrue(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);
    }

    @GetMapping("/liveProducts")
    public ResponseEntity<PageableResponse> getLiveProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
    ) {

        PageableResponse<ProductDto> allLIveProducts = this.productService.getAllLIveProducts(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allLIveProducts, HttpStatus.OK);

    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<PageableResponse> getProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.PRODUCT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.SORT_DIR, required = false) String direction
            ,@PathVariable String keyword ){

        PageableResponse<ProductDto> productByTitle = this.productService.getProductByTitle(keyword, pageNumber, pageSize, sortBy, direction);

        return new ResponseEntity<>(productByTitle,HttpStatus.OK);
    }


    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image,@PathVariable String productId) throws IOException {

        String file = this.fileService.uploadFile(image, path);

        ProductDto product = this.productService.getSingleProduct(productId);

        product.setImage(file);

        ProductDto updatedProduct = this.productService.updateProduct(productId, product);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }


    @GetMapping("/image/{productId}")
    public void getProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        ProductDto product = this.productService.getSingleProduct(productId);
        InputStream resource = this.fileService.getResource(path, product.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


    @PostMapping("/category/{categoryId}")
    public ResponseEntity<ProductDto> saveProductWithCategoryId(@PathVariable String categoryId,@RequestBody ProductDto productDto){

        ProductDto product = this.productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<>(product,HttpStatus.CREATED);

    }
    


    @GetMapping("/categoryId/{categoryId}/productId/{productId}")
    public ResponseEntity<ProductDto> getProductWithCategoryId(@PathVariable String categoryId,@PathVariable String productId ){

        ProductDto product = this.productService.getProductWithProductIdCategoryId(categoryId, productId);

        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }






}
