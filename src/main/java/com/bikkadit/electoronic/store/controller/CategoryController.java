package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.*;
import com.bikkadit.electoronic.store.service.CategoryServiceI;
import com.bikkadit.electoronic.store.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;


    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String path;

    /**
     * @author Sujit Patil
     * @apiNote save Category Into Database
     * @param categoryDto
     * @return CategoryDto
     * @since 1.0v
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CategoryDto> saveCategory( @Valid @RequestBody CategoryDto categoryDto){
        log.info("Enter the  request for Save the Category : {}",categoryDto);
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        log.info("Completed the  request for Save the Category : {}",categoryDto);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    /**
     * @author Sujit Patil
     * @apiNote get All Category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return PageableResponse
     * @since 1.0v
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse>getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam (value = "sortBy",defaultValue = AppConstants.CATEGORY_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIR,required = false) String direction

    ){
        log.info("Enter the  request for Get All the Category :");
        PageableResponse<CategoryDto> allCategory = this.categoryServiceI.getAllCategory(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All the Category :");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get Single User By UserId
     * @param categoryId
     * @return CategoryDto
     * @since 1.0v
     */
    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId){
        log.info("Enter the  request for Get  the Category With Category Id  :{}",categoryId);
        CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(categoryId);
        log.info("Completed the  request for Get  the Category With Category Id  :{}",categoryId);
        return new ResponseEntity<CategoryDto>(singleCategory,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote delete Category By Id
     * @param categoryId
     * @return Api Response
     * @since 1.0v
     */
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        log.info("Enter the  request for Delete  the Category With Category Id  :{}",categoryId);
        ApiResponse api=new ApiResponse();
        api.setMessage(AppConstants.DELETE);
        api.setStatusCode(HttpStatus.OK);
        api.setStatus(true);
        this.categoryServiceI.deleteCategory(categoryId);
        log.info("Completed the  request for Delete  the Category With Category Id  :{}",categoryId);
        return new ResponseEntity<>(api,HttpStatus.OK);
    }

    /**
     * @athor Sujit Patil
     * @apiNote uodate Category By Id
     * @param dto
     * @param categoryId
     * @return CategoryDto
     * @since 1.0v
     */

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,@PathVariable String categoryId){
        log.info("Enter the  request for Update  the Category With Category Id  :{}",categoryId);
        CategoryDto categoryDto1 = this.categoryServiceI.updateCategory(dto, categoryId);
        log.info("Completed the  request for Update  the Category With Category Id  :{}",categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote Upload the image with CategoryId
     * @param image
     * @param catId
     * @return
     * @throws IOException
     * @since 1.0v
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/{catId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable    String catId) throws IOException {
        log.info("Enter the request for Upload Image with categoryId : {}",catId);
        String file = this.fileService.uploadFile(image, path);

        CategoryDto singleCategory = this.categoryServiceI.getSingleCategory(catId);

        singleCategory.setCoverImage(file);

        CategoryDto categoryDto = this.categoryServiceI.updateCategory(singleCategory, catId);

        ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();

        log.info("Completed the request for Upload Image with categoryId : {}",catId);
        return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);

}

    /**
     * @author Sujit Patil
     * @apiNote get image with CategoryId
     * @param categoryId
     * @param response
     * @throws IOException
     * @since 1.0v
     */
    @GetMapping("/image/{categoryId}")
    public void getUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info("Enter the request for Get Image with categoryId : {}",categoryId);
        CategoryDto singleCategory = categoryServiceI.getSingleCategory(categoryId);
        log.info(" UserImage Name : {}",singleCategory.getCoverImage());
        InputStream resource = fileService.getResource(path, singleCategory.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed the request for Get Image with categoryId : {}",categoryId);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
