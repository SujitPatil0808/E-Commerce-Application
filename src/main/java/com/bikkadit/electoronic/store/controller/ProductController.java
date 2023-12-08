package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;

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

}
