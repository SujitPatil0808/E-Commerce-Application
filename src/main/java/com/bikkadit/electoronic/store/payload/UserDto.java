package com.bikkadit.electoronic.store.payload;

import com.bikkadit.electoronic.store.validator.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private String userId;

    @NotBlank
    @Size(min = 5, max = 20, message = "UserName Should be Min 5 Character And Max 20 Character")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$", message = "Enter The Valid Email Id")
    private String email;

//    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.)(?=.[@#$%^&+=]).*$", message = "password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @NotBlank
    @Size(min = 3, max = 6, message = "Enter Valid Gender For User")
    private String gender;

    @NotBlank
    @Size(message = "Please Enter About User")
    private String about;

    @ImageNameValid(message = "Image Name Must Not  Be Blank")
    private String imageName;
}
