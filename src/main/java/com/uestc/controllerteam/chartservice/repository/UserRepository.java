package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.UserDao;
import com.uestc.controllerteam.chartservice.dao.UserRedisDao;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class UserRepository implements InitializingBean {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRedisDao userRedisDao;

    /**
     * 内存维护所有用户信息
     */
    private ConcurrentHashMap<String,UserDto> usersCache;

    public UserDto queryUser(String userName){
        UserDto userDto = usersCache.get(userName);
//        if(userDto == null){
//            userDto = userRedisDao.queryUser(userName);
//            if(userDto != null){
//                usersCache.put(userName,userDto);
//            }
//        }
        return userDto;
    }

    public boolean saveUser(UserDto userDto){
//        boolean success = userDao.saveUser(userDto) <= 1;
        userRedisDao.createUser(userDto);
        boolean success = true;
        if(success){
            try {
                usersCache.put(userDto.getUsername(),userDto);
            } catch (Exception e) {
                // TODO: 2021/7/26 写内存失败再试一次，再失败就不管了，后面优化
                usersCache.put(userDto.getUsername(),userDto);
            }
        }
        return success;
    }

    //用户名与房间关系放到缓存，暂时不用
    @Deprecated
    public boolean updateUser(int roomId , String username){
        return userDao.updateUser(roomId , username) <= 1;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        usersCache = new ConcurrentHashMap<>(2048);
//        List<UserDto> roomDtos = userDao.queryAll();
        List<UserDto> roomDtos = userRedisDao.queryAll();
        if(!CollectionUtils.isEmpty(roomDtos)){
            for(UserDto userDto: roomDtos){
                usersCache.put(userDto.getUsername(),userDto);
            }
        }
    }

}
