package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.service.CategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);

        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }
@GetMapping("/")
    public ResponseEntity<PageableResponse>getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIR,required = false) String direction

    ){
        PageableResponse<CategoryDto> allCategory = this.categoryServiceI.getAllCategory(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId){
        CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(categoryId);
        return new ResponseEntity<CategoryDto>(singleCategory,HttpStatus.OK);
    }

@DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        ApiResponse api=new ApiResponse();
        api.setMessage("Category Deleted Successfully");
        api.setStatusCode(HttpStatus.OK);
        api.setStatus(true);
        this.categoryServiceI.deleteCategory(categoryId);
        return new ResponseEntity<>(api,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto dto,@PathVariable String categoryId){
        CategoryDto categoryDto1 = this.categoryServiceI.updateCategory(dto, categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }






}
