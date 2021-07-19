package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.UserDao;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.utils.BizCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class UserRepository {

    @Autowired
    private UserDao userDao;


    public UserDto queryUser(String userName){
        BizCheckUtils.checkNull(userName,"Invalid input");

        return userDao.queryUser(userName);
    }

    public boolean saveUser(UserDto userDto){
        return userDao.saveUser(userDto) <= 1;
    }

    public boolean updateUser(int roomId , String username){
        return userDao.updateUser(roomId , username) <= 1;
    }

}
