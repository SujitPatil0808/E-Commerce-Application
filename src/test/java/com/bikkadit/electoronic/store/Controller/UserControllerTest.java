package com.bikkadit.electoronic.store.Controller;

import com.bikkadit.electoronic.store.model.Role;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.service.UserServiceI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @MockBean
    private UserServiceI userServiceI;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    User user;

    UserDto userDto;

    UserDto userDto2;

    Role role;

    String jwtToken="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTdWppdFBhdGlsQEdtYWlsLmNvbSIsImV4cCI6MTcwMzg3MzAxOSwiaWF0IjoxNzAzODcxMjE5fQ.6he59a8PDaPdh5D_bjMdzM4nKF-DrseiX1IJTbeS0kVHW98rY2ZNX4Sn19VIaaIwxi6cadDpTwpdOreLCCPzTA";
    @BeforeEach
    public void init() {
        role=Role.builder().roleId("Abc").roleName("NORMAL").build();
        user = User.builder()
                .name("Sujit")
                .email("SujitPatil3066@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("Sujit#0808")
                .roles(Set.of(role))
                .build();

        userDto = UserDto.builder()
                .name("Rushi")
                .email("Rushi123@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("Rushi#0808")

                .build();

        userDto2 = UserDto.builder()
                .name("Sujit")
                .email("SujitPatil3066@Gmail.com")
                .about("I Am Software Developer")
                .gender("Male")
                .imageName("abc.png")
                .password("Sujit#0808")
                .build();

    }
    private String convertObjTojsonString(User user) throws JsonProcessingException {

        ObjectMapper mapper=new ObjectMapper();
        String valueAsString = mapper.writeValueAsString(user);
        return valueAsString;
    }
    @Test
    public void createProductTest() throws Exception {

        UserDto dto = modelMapper.map(user, UserDto.class);

        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users")
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjTojsonString(user))
                        .accept(MediaType.APPLICATION_JSON)


        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void getAllUsersTest() throws Exception {

        PageableResponse<UserDto> pagResponse=new PageableResponse<>();

        pagResponse.setContent(Arrays.asList(userDto,userDto2));
        pagResponse.setLastPage(false);
        pagResponse.setPageNumber(100);
        pagResponse.setPageSize(10);
        pagResponse.setTotalElements(100l);

        Mockito.when(userServiceI.getAllUsers(Mockito.anyInt(),Mockito.anyInt()
                ,Mockito.anyString(),Mockito.anyString())).thenReturn(pagResponse);

        this.mockMvc
                .perform(
                MockMvcRequestBuilders.get("/api/users/")
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk());


    }

    @Test
    public void getSingleUserTest() throws Exception {

        UserDto dto = modelMapper.map(user, UserDto.class);

        String userId="1234";
        Mockito.when(userServiceI.getSingleUser(userId)).thenReturn(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/id/"+userId)
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
        ).andDo(print()).andExpect(status().isOk());


    }


    @Test
    public void getUserByEmailTest() throws Exception {

        UserDto dto = modelMapper.map(user, UserDto.class);

        String email="SujitPatil@Gmail.com";
        Mockito.when(userServiceI.getUserByEmailId(email)).thenReturn(dto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/email/"+email)
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
        ).andDo(print()).andExpect(status().isOk());


    }


    @Test
    public void getUserByEmailAndPassTest() throws Exception {

        String email="SujitPatil123@Gmail.com";
        String password="12340";

        UserDto dto = modelMapper.map(user, UserDto.class);

        Mockito.when(userServiceI.getUserByEmailAndPassword(email,password)).thenReturn(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/email/"+email+"/pass/"+password)
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
        ).andDo(print()).andExpect(status().isOk());

    }


    @Test
    public void getUserContaingTest() throws Exception {
        String keyword="sujit";

        Mockito.when(userServiceI.searchUser(keyword)).thenReturn(Arrays.asList(userDto,userDto2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/search/"+keyword)
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
        ).andDo(print()).andExpect(status().isOk());
    }



    @Test
    public void deleteUserTest() throws Exception {

        String userId="123";

        Mockito.doNothing().when(userServiceI).deleteUser(userId);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/users/delete/"+userId)
                        .header(HttpHeaders.AUTHORIZATION,jwtToken)
        ).andDo(print()).andExpect(status().isOk());

    }
















}
