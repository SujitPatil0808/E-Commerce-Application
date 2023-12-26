package com.bikkadit.electoronic.store.service.impl;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electoronic.store.helper.Helper;
import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.payload.UserDto;
import com.bikkadit.electoronic.store.repository.UserRepository;
import com.bikkadit.electoronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String path;

    @Override
    public UserDto createUser(UserDto userDto) {
        // If We Use String as primary key then we can generate key Like these
        log.info("Entering the Dao call for create the user ");
        String str = UUID.randomUUID().toString();
        userDto.setUserId(str);

        // set Encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = this.modelMapper.map(userDto, User.class);
        this.userRepository.save(user);
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for create the user ");
        return userDto1;

    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        log.info("Entering the Dao call for update the user with userId :{}",id);
        User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND + id));
        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        this.userRepository.save(user);
        UserDto dto = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for update the user with userId :{}",id);
        return dto;
    }

    @Override
    public void deleteUser(String id) {
        log.info("Entering the Dao call for delete the user with userId :{}",id);
        User use = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + id));
        String imageName = use.getImageName();
        String fullPath = path + imageName;

        File image=new File(fullPath);
        if(image.exists()){
        image.delete();
        }

        log.info("Completed the Dao call for delete the user with userId :{}",id);
        this.userRepository.delete(use);

    }

    @Override
    public PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        log.info("Entering the Dao call for getAll the users ");
        Sort desc = (direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        PageRequest pr = PageRequest.of(pageNumber, pageSize, desc);

        Page<User> pages = this.userRepository.findAll(pr);


        List<User> users = pages.getContent();


        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(pages, UserDto.class);

        log.info("Completed the Dao call for getAll the users :");
        return pageableResponse;
    }

    @Override
    public UserDto getSingleUser(String id) {
        log.info("Entering the Dao call for get Single user with userId :{} ",id);
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + id));
        UserDto dto = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for get Single user with userId :{} ",id);
        return dto;
    }

    @Override
    public UserDto getUserByEmailId(String id) {
        log.info("Entering the Dao call for get Single user with emailId :{} ",id);
        User user = this.userRepository.findByEmail(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + id));

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for get Single user with emailId :{} ",id);
        return userDto;
    }


    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Entering the Dao call for get  users with Containing :{} ",keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);

        List<UserDto> dto = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        log.info("Comleted the Dao call for get  users with Containing :{} ",keyword);
        return dto;
    }

    @Override
    public UserDto getUserByEmailAndPassword(String email, String password) {

        log.info("Entering the Dao call for get Single user with Email And Password  :{} :{} ",email,password);
        User user = this.userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + email));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for get Single user with Email And Password  :{} :{} ",email,password);
        return userDto;
    }
}
