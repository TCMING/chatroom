package com.uestc.controllerteam.chartservice.service;

import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.model.UserResponse;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 20:14
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto queryUserByName(String userName){
        UserDto userDto = userRepository.queryUser(userName);
        return userDto;
    }

    public boolean userPasswordCheck(String username, String password){
        UserDto userDto = userRepository.queryUser(username);
        if(userDto != null && StringUtils.equals(password,userDto.getPassword())){
            return true;
        }
        return false;
    }

    public boolean registryUser(UserRequest user){
        UserDto userDto = userRepository.queryUser(user.getUsername());
        if(userDto != null){
            return false;
        }
        userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userRepository.saveUser(userDto);

    }

}
