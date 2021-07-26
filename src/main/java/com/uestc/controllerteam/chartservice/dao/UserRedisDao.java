package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserRedisDao {
    @Resource
    private RedisTemplate<String, UserDto> redisTemplate;

    private String userListKey = "userList";

    public void createUser(UserDto user) {
        redisTemplate.opsForList().leftPush(userListKey, user);
    }

    public List<UserDto> queryAll() {
        return redisTemplate.opsForList().range(userListKey, 0, -1);
    }

}
