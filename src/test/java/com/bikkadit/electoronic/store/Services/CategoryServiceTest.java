package com.bikkadit.electoronic.store.Services;

import com.bikkadit.electoronic.store.model.Category;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.repository.CategoryRepository;
import com.bikkadit.electoronic.store.service.CategoryServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceI categoryServiceI;

    @Autowired
    private ModelMapper modelMapper;
    Category category;

    Category category1;

    @BeforeEach
    public void init(){
        category= Category.builder()
                .categoryId("Abc123")
                .title("It Contains Apple Phone")
                .coverImage("Category.png")
                .build();

        category1= Category.builder()
                .categoryId("S123")
                .title("It Contains Mi Phone")
                .coverImage("Mi.png")
                .build();

    }


    @Test
    public void saveCategoryTest(){

        Mockito.when(this.categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto category1 = this.categoryServiceI.createCategory(modelMapper.map(category, CategoryDto.class));

//        System.out.println(category1.getTitle());

        Assertions.assertNotNull(category1);

    }

    @Test
    public void getAllCategoryTest(){

        List<Category> categories = Arrays.asList(category, category1);
        Page<Category> page=new PageImpl<>(categories);

        Mockito.when(this.categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        Sort.by("title").ascending();

    }

    @Test
    public void updateCategoryTest(){
        CategoryDto categoryDto=CategoryDto.builder()
                .title("Mi")
                .description("This Is Xiomi")
                .coverImage("MI.png").build();

        Mockito.when(this.categoryRepository.findById("categoryId")).thenReturn(Optional.of(category));
        Mockito.when(this.categoryRepository.save(category)).thenReturn(category);

        CategoryDto acctualResult = this.categoryServiceI.updateCategory(categoryDto, "categoryId");
        Assertions.assertNotNull(acctualResult);
        Assertions.assertEquals("Mi",acctualResult.getTitle());
    }

    @Test
    public void getSingleCategoryTest(){

        String categoryId="123";

        Mockito.when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto acttualRes = this.categoryServiceI.getSingleCategory(categoryId);

        Assertions.assertNotNull(acttualRes);
        Assertions.assertEquals(category.getTitle(),acttualRes.getTitle());

    }



    @Test
    public void deleteCategoryTest(){

        String categoryId="123";

        Mockito.when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        this.categoryServiceI.deleteCategory(categoryId);

        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }


















}
