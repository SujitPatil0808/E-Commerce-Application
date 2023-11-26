package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.helper.Helper;
import com.bikkadit.electoronic.store.model.Category;
import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.repository.CategoryRepository;
import com.bikkadit.electoronic.store.service.CategoryServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepository.save(category);

        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort desc = (direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        
        PageRequest pagable= PageRequest.of(pageNumber,pageSize,desc);

        Page<Category> all = this.categoryRepository.findAll(pagable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(all, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        return dto;
    }

    @Override
    public void deleteCategory(String categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        this.categoryRepository.delete(category);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        this.categoryRepository.save(category);
        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        return dto;
    }
}
