package com.bikkadit.electoronic.store.service;

import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;

import java.util.List;

public interface UserServiceI {

             UserDto createUser(UserDto userDto);

             UserDto updateUser(UserDto userDto, String id);

             void deleteUser(String id);

            PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction);

             UserDto getSingleUser(String id);

             UserDto getUserByEmailId(String id);

             List<UserDto> searchUser(String keyword);

             UserDto getUserByEmailAndPassword(String email, String password);

}
