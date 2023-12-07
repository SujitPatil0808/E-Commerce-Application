package com.bikkadit.electoronic.store.Controller;

import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.service.UserServiceI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserControllerTest.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceI userServiceI;

    @Autowired
    ModelMapper modelMapper;

    User user;

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

    public static String convertObjToJsonString(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String obj = mapper.writeValueAsString(user);
           return obj;
    }

//    @Test
//    public void saveUserTest() throws JsonProcessingException {
//
//        UserDto dto = modelMapper.map(user, UserDto.class);
//
//        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
//                .content(String.valueOf(MediaType.APPLICATION_JSON))
//                .content(convertObjToJsonString(user))
//                .accept(MediaType.APPLICATION_JSON)
//
//        ).andDo(print())
//
//        )
//
//
//    }

}
