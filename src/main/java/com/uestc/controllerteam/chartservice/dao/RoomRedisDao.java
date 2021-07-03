package com.uestc.controllerteam.chartservice.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Component
public class RoomRedisDao {

	@Resource
    private RedisTemplate<String, RoomDto> redisTemplate;
	
	private String roomListKey = "roomList";
	
	public void createRoom(RoomDto room) {
		redisTemplate.opsForList().rightPush(roomListKey, room);
	}
	
	public List<RoomDto> getRoomList() {
		return redisTemplate.opsForList().range(roomListKey, 0, -1);
	}

}
