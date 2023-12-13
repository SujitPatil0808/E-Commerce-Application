package com.bikkadit.electoronic.store.Controller;

import com.bikkadit.electoronic.store.model.Category;
import com.bikkadit.electoronic.store.payload.CategoryDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.service.CategoryServiceI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CategotyControllerTest {

    @MockBean
    private CategoryServiceI categoryServiceI;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    Category category;

    @BeforeEach
    public void init(){
        category=Category.builder()
                .title("Apples")
                .description("This is Apples Mobiles")
                .coverImage("Apple.png").build();
    }

    private String convertObjectToJsonString(Category category) throws JsonProcessingException {
        ObjectMapper obj=new ObjectMapper();
        String object = obj.writeValueAsString(category);
        return object;
    }

    @Test
    public void createCategoryTest() throws Exception {

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryServiceI.createCategory(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/category/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void updateCategoryTest() throws Exception {

        String categoryId="123";
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryServiceI.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/category/"+categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void getSingleCategory() throws Exception {
        String categoryId="123";
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryServiceI.getSingleCategory(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/category/categoryId/"+categoryId)
                        .contentType(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());


    }
    @Test
    public void getAllCategories() throws Exception {

        CategoryDto dto = CategoryDto.builder().title("Apple").description("this is Apple phones").coverImage("Apple.png").build();
        CategoryDto dto1 = CategoryDto.builder().title("Mi").description("this is Mi phones").coverImage("Mi.png").build();
        CategoryDto dto2 = CategoryDto.builder().title("Redmii").description("this is Redmi phone").coverImage("Redmi.png").build();

        PageableResponse<CategoryDto> pageResponse=new PageableResponse<>();
        pageResponse.setContent(Arrays.asList(dto,dto1,dto2));
        pageResponse.setLastPage(false);
        pageResponse.setPageNumber(1);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElements(15l);

        Mockito.when(this.categoryServiceI.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageResponse);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());


    }

    @Test
    public void deleteCategoryTest() throws Exception {

        String categoryId="1234";

        Mockito.doNothing().when(this.categoryServiceI).deleteCategory(categoryId);

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/category/delete/"+categoryId)
        ).andDo(print()).andExpect(status().isOk());

    }
}
