package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.UserDao;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class UserRepository {

    @Autowired
    private UserDao userDao;

    /**
     * 内存维护所有用户信息
     */
    private ConcurrentHashMap<String,UserDto> usersCache = new ConcurrentHashMap<>(1000);


    public UserDto queryUser(String userName){
        UserDto userDto = usersCache.get(userName);
        if(userDto == null){
            userDto = userDao.queryUser(userName);
            if(userDto != null){
                usersCache.put(userName,userDto);
            }
        }
        return userDto;
    }

    public boolean saveUser(UserDto userDto){
        boolean success = userDao.saveUser(userDto) <= 1;
        if(success){
            usersCache.put(userDto.getUsername(),userDto);
        }
        return success;
    }

    //用户名与房间关系放到缓存，暂时不要
    @Deprecated
    public boolean updateUser(int roomId , String username){
        return userDao.updateUser(roomId , username) <= 1;
    }

}
