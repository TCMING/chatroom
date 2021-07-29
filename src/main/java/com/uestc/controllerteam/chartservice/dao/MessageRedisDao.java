package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MessageRedisDao {

    @Resource
    private RedisTemplate redisTemplate;

    private String messageListKey = "messageList";

    private String messageIdSet = "messageIdSet";

    public void saveMessage(MessageDto messageDto) {
        redisTemplate.opsForList().leftPush(messageDto.getRoomId()+messageListKey, messageDto);
        // TODO: 2021/7/25 事务？
        redisTemplate.opsForSet().add(messageIdSet,messageDto.getId());
    }

    public boolean queryMessage(String id){
        return redisTemplate.opsForSet().isMember(messageIdSet,id);
    }

    public List<MessageRetrive> queryAllMessage(HashMap<String,Integer> params){
        String key = params.get("roomId")+messageListKey;
        int startIndex = params.get("startIndex");
        int endIndex = startIndex+params.get("pageSize")-1;
        if (endIndex<0){
            return new ArrayList<MessageRetrive>();
        }
        return redisTemplate.opsForList().range(key,params.get("startIndex"),endIndex);
    }

}
