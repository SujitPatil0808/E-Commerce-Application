package com.bikkadit.electoronic.store.Services;

import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ModelMapper modelMapper;
    User user;
    User user1;
    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Sujit")
                .email("SujitPatil3066@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("Sujit@8878")
                .build();


    }



    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto userDto1 = userServiceI.createUser(modelMapper.map(user, UserDto.class));

        System.out.println(userDto1.getName());
        Assertions.assertNotNull(userDto1);

        Assertions.assertEquals("Sujit", userDto1.getName());

    }

    @Test
    public void updateUserTest() {

        UserDto userDto = UserDto.builder()
                .name("Pavan")
                .email("Pavan@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("pp@8878")
                .build();

        String userId = "abc";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto updatedUser = userServiceI.updateUser(userDto, userId);
        System.out.println(updatedUser.getName());
        System.out.println(updatedUser.getPassword());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(updatedUser.getName(),userDto.getName());
    }

    @Test
    public void deleteUserTest(){

            String userId="Abc";

            Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            userServiceI.deleteUser(userId);

            Mockito.verify(userRepository,Mockito.times(1)).delete(user);
        // How Many Time This Will Call On That Basis We Check

    }

    @Test
    public void getUserByEmailIdTest(){
        String emailId="Sujit@Gmail.com";

        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userServiceI.getUserByEmailId(emailId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getEmail(),userDto.getEmail(),"Email Is Mismatch");
    }

    @Test
    public void getSingleUserTest() {


        String userId = "Abc";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userServiceI.getSingleUser(userId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getUserId(), user.getUserId(), "User Id Is Mismatch");
    }

    @Test
    public void searchUserTest(){

        String keyword="Patil";

        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(List.of(user,user1));

        List<UserDto> userDtos = userServiceI.searchUser(keyword);
        Assertions.assertEquals(2,userDtos.size(),"Users Size Is Mismatch");

    }

    @Test
    public void getUserByEmailAndPasswordTest(){

        String email="Abc@Gmail.com";
        String pass="Sp@122";

        Mockito.when(userRepository.findByEmailAndPassword(email,pass)).thenReturn(Optional.of(user));
        UserDto userDto = userServiceI.getUserByEmailAndPassword(email, pass);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getPassword(),user.getPassword(),"PassWord Is Not Match");
        Assertions.assertEquals(userDto.getEmail(),user.getEmail(),"Email Is Not Match");
    }

//    @Test
//    public void getAllUsers(){//
//    user1 = User.builder()
//                .name("Pavan Patil")
//                .email("SujitPatil3066@Gmail.com")
//                .about("I Am Software Developer")
//                .gender("Male")
//                .imageName("abc.png")
//                .password("Sujit@8878")
//                .build();
//
//
//        Mockito.when(userRepository.findAll().thenReturn(List.of(user,user1));
//        PageableResponse allUsers = userServiceI.getAllUsers(1, 5, "name", "Asc");
//        Assertions.assertNotNull(allUsers);
//
//
//    }
}
