package com.uestc.controllerteam.chartservice.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	//缓存
	private ConcurrentHashMap<Integer, Set<String>> roomUserInfo;

	public Set<String> queryRoomUsers(int roomId){
		return roomUserInfo.getOrDefault(roomId,new HashSet<>());
	}

	public boolean enterRoom(int roomId,String username){
		//1.判断房间存在
		RoomDto roomDto = roomRepository.queryRoomById(roomId);
		if(roomDto == null || StringUtils.isEmpty(roomDto.getName())){
			return false;
		}
		//2.登录房间
		Set<String> userSet = roomUserInfo.get(roomId);
		if(CollectionUtils.isEmpty(userSet)){
			userSet = new HashSet<>();
			userSet.add(username);
			roomUserInfo.put(roomId,userSet);
		}else{
			if(!userSet.contains(username)){
				userSet.add(username);
				roomUserInfo.put(roomId,userSet);
			}
		}
		return true;
	}

	public boolean roomLeave(UserDto user){
		// TODO: 2021/7/4 题意不要求持久化在线人数   内存或者redis或者mysql  数据一致性  性能问题
		//1.获取用户所在房间信息
		//可用redis缓存，或者直接存到数据库中的字段上面，假设存在数据库字段中
		if(user.getRoomId() <= 0){
			return true;
		}
		user.setRoomId(0);
		return userRepository.updateUser(user);

	}

	public List<RoomDto> queryAll(){
		return roomRepository.queryAll();
	}
	
}
