package com.bikkadit.electoronic.store.controller;


import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.ImageResponse;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.service.FileService;
import com.bikkadit.electoronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;



    @Value("${user.profile.image.path}")
     private String path;

    /**
     * @author Sujit Patil
     * @apiNote save user into database
     * @param user
     * @return UserDto
     * @since 1.0v
     */

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto user) {
        log.info("Enter the  request for Save the User : {}",user);
        UserDto user1 = this.userServiceI.createUser(user);
        log.info("Completed the request for Save the User : {}",user);
        return new ResponseEntity<UserDto>(user1, HttpStatus.CREATED);
    }

    /**
     * @author Sujit Patil
     * @apiNote get All Users
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return PageableResponse
     * @since 1.0v
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse> getAllUsers(

            @RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam (value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIR,required = false) String direction)

    {
        log.info("Enter the  request for get all users  ");
        PageableResponse allUsers = this.userServiceI.getAllUsers(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the request for get all users  ");
        return new ResponseEntity<PageableResponse>(allUsers, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get a Single User By Id
     * @param userId
     * @return UserDto
     * @since 1.0v
     */
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId) {
        log.info("Enter the  request for get the user with userId :{} ",userId);
        UserDto singleUser = this.userServiceI.getSingleUser(userId);
        log.info("Completed the  request for get the user with userId :{} ",userId);
        return new ResponseEntity<UserDto>(singleUser, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get User By Email Id
     * @param email
     * @return UserDto
     * @since 1.0v
     *
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Enter the  request for get the user with EmailId :{} ",email);
        UserDto userByEmailId = this.userServiceI.getUserByEmailId(email);
        log.info("Completed the  request for get the user with EmailId :{} ",email);
        return new ResponseEntity<UserDto>(userByEmailId, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get User By Email Id And Password
     * @param email
     * @param password
     * @return UserDto
     * @since 1.0v
     */
    @GetMapping("/email/{email}/pass/{password}")
    public ResponseEntity<UserDto> getUserByEmailAndPass(@PathVariable String email, @PathVariable String password) {

        log.info("Enter the  request for get the user with EmailId And Password :{} :{} ",email,password);
        UserDto userByEmailAndPassword = this.userServiceI.getUserByEmailAndPassword(email, password);
        log.info("Completed the  request for get the user with EmailId And Password :{} :{} ",email,password);
        return  new ResponseEntity<UserDto>(userByEmailAndPassword,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote get User By Containing keyword
     * @param keyword
     * @return List<UserDto>
     * @since 1.0v
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUserContaing(@PathVariable String keyword) {

        log.info("Enter the  request for get the user containing :{} ",keyword);
        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);
        log.info("Completed the  request for get the user containing :{} ",keyword);

        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }


    /**
     * @author Sujit Patil
     * @apiNote Delete User By User Id
     * @param userid
     * @return Api Response
     * @since 1.0v
     */
    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userid) {
        log.info("Enter the  request for Delete the user with UserId :{} ",userid);

        this.userServiceI.deleteUser(userid);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(AppConstants.DELETE +userid);
        apiResponse.setStatus(true);
        apiResponse.setStatusCode(HttpStatus.OK);
        log.info("Completed  the  request for Delete the user with UserId :{} ",userid);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @param userDto
     * @param str
     * @return UserDto
     * @since 1.0v
     */
    @PutMapping("/{str}")
    public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto, @PathVariable String str){
        log.info("Enter the  request for update  the user with UserId :{} ",str);
        UserDto userDto1 = this.userServiceI.updateUser(userDto,str);
        log.info("Completed the  request for update  the user with UserId :{} ",str);
        return  new ResponseEntity<UserDto>(userDto1,HttpStatus.OK);
    }

    /**
     * @author Sujit Patil
     * @apiNote Upload The Image
     * @param image
     * @param userId
     * @return ImageResponse
     * @throws IOException
     * @since 1.0V
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image, @PathVariable    String userId) throws IOException {
    log.info("Enter the request for Upload Image with UserId : {}",userId);
    String file = this.fileService.uploadFile(image, path);

    UserDto user = this.userServiceI.getSingleUser(userId);

    user.setImageName(file);

    UserDto updatedUser = this.userServiceI.updateUser(user, userId);

    ImageResponse imageResponse = ImageResponse.builder().message("Image Uploaded").imageName(file).status(true).statusCode(HttpStatus.CREATED).build();

        log.info("Completed the request for Upload Image with UserId : {}",userId);
    return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);

}

    /**
     * @author Sujit Patil
     * @apiNote to Get Image with UserId
     * @param userId
     * @param response
     * @throws IOException
     * @since 1.0v
     */
    @GetMapping("/image/{userId}")
    public void getUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Enter the request for Get Image with UserId : {}",userId);
        UserDto user = userServiceI.getSingleUser(userId);
        log.info(" UserImage Name : {}",user.getImageName());
        InputStream resource = fileService.getResource(path, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Completed the request for Get Image with UserId : {}",userId);
        StreamUtils.copy(resource,response.getOutputStream());
    }



}
