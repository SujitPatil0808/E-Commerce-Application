package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.helper.Helper;
import com.bikkadit.electoronic.store.model.Category;
import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.ProductDto;
import com.bikkadit.electoronic.store.repository.CategoryRepository;
import com.bikkadit.electoronic.store.repository.ProductRepository;
import com.bikkadit.electoronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        Product newProduct = this.productRepository.save(product);
        return modelMapper.map(newProduct,ProductDto.class);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.getLive());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.getStock());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setImage(productDto.getImage());

        Product save = this.productRepository.save(product);

        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort = (direction.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = productRepository.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(all,ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> findByliveTrue(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byLiveTrue = productRepository.findByLiveTrue(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byLiveTrue, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLIveProducts(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> allProducts = productRepository.findAll(pages);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(allProducts, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getProductByTitle(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byTitleContaining = productRepository.findByTitleContaining(pages,keyword);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byTitleContaining, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Product product  = this.modelMapper.map(productDto, Product.class);

        Date date=new Date();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        product.setAddedDate(date);
        product.setCategories(category);
        Product newProduct = this.productRepository.save(product);
        return modelMapper.map(newProduct,ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        Sort sort=(direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        PageRequest pages=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> product = this.productRepository.findByCategories(category, pages);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(product, ProductDto.class);

        return pageableResponse;
    }


    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));



        product.setCategories(category);
        Product save = this.productRepository.save(product);

        ProductDto dto = modelMapper.map(save, ProductDto.class);

        return dto;
    }


}
