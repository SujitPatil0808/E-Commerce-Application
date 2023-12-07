package com.bikkadit.electoronic.store.service;

import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;

public interface ProductService {


        ProductDto saveProduct(ProductDto productDto);

        ProductDto getSingleProduct(String productId);

        void deleteProduct(String productId);

        ProductDto updateProduct(String productId,ProductDto productDto);

        PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction);

        //findByLive

        PageableResponse<Product> findByliveTrue(Integer pageNumber, Integer pageSize, String sortBy, String direction);

        //getalllive

        PageableResponse<ProductDto> getAllLIveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction);

        //search By Title

        PageableResponse<ProductDto> getProductByTitle(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String direction);

}
