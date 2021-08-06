package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

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

    public void saveMessage(List<MessageDto> list) {

//        redisTemplate.opsForList().leftPushAll(roomId+messageListKey ,list);

        List<String> result = redisTemplate.executePipelined(new SessionCallback() {
            //执行流水线
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //批量处理的内容
                for (int i = 0; i < list.size(); i++) {
                    MessageDto tmp = list.get(i);
                    operations.opsForList().leftPush(tmp.getRoomId()+messageListKey,tmp);
                    operations.opsForSet().add(messageIdSet , tmp.getId());
                }
                //注意这里一定要返回null，最终pipeline的执行结果，才会返回给最外层
                return null;
            }
        });
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
