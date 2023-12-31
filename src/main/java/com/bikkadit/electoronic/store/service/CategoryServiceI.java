package com.bikkadit.electoronic.store.service;

import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;

public interface CategoryServiceI {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    // getall
   PageableResponse  getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String direction);

    //getById
    CategoryDto getSingleCategory(String categoryId);
    //delete
    void deleteCategory(String categoryId);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);



}
