package com.uestc.controllerteam.chartservice.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Component
public class RoomRedisDao {

	@Resource(name="redisTemplate")
//    private RedisTemplate<String, RoomDto> redisTemplate;
	private RedisTemplate redisTemplate;

	@Resource(name="redisTemplate2")
	private RedisTemplate redisTemplate2;

	private String roomListKey = "roomList";

	private String idKey = "roomIdKey";
	
	public void createRoom(RoomDto room) {
		redisTemplate.opsForList().rightPush(roomListKey, room);
	}

	public List<RoomDto> queryRoomRecord(long startIndex , long size){
		long endIndex = startIndex+size-1;
		if(endIndex<0){
			return new ArrayList<RoomDto>();
		}
		try {
			return redisTemplate.opsForList().range(roomListKey,startIndex,endIndex);
		} catch (Exception e) {
			return new ArrayList<RoomDto>();
		}

	}

	public List<RoomDto> queryAll() {
		return redisTemplate.opsForList().range(roomListKey, 0, -1);
	}

	public int createRoomId(){
		long id=  redisTemplate.opsForValue().increment(idKey);
		int idInt = (int) id;
		return idInt;
	}

	public void initRoomId(){
		boolean res = redisTemplate.opsForValue().setIfAbsent(idKey,0);
	}

}
