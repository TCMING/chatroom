package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.UserDao;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class UserRepository {

    @Autowired
    private UserDao userDao;


    public boolean updateUser(User user){
        UserDto userDto = new UserDto();
        userDto = new UserDto();
        return userDao.updateUser(userDto);
    }


}
