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
	private ConcurrentHashMap<Integer, Set<String>> roomUserCache;

	public Set<String> queryRoomUsers(int roomId){
		return roomUserCache.getOrDefault(roomId,new HashSet<>());
	}

	// TODO: 2021/7/5 并发同步？ 
	public synchronized boolean enterRoom(int roomId,String username){
		//1.判断房间存在
		RoomDto roomDto = roomRepository.queryRoomById(roomId);
		if(roomDto == null || StringUtils.isEmpty(roomDto.getName())){
			return false;
		}
		//2.登录房间
		// TODO: 2021/7/5 先不考虑缓存
//		Set<String> userSet = roomUserCache.get(roomId);
//		if(CollectionUtils.isEmpty(userSet)){
//			userSet = new HashSet<>();
//			userSet.add(userName);
//			roomUserCache.put(roomId,userSet);
//		}else{
//			if(!userSet.contains(userName)){
//				userSet.add(userName);
//			}
//		}
		UserDto user = userRepository.queryUser(username);
		if(user.getRoomId()!=roomId){
			userRepository.updateUser(roomId,username);
		}
		return true;
	}

	public synchronized boolean roomLeave(String username){
		// TODO: 2021/7/4 题意不要求持久化在线人数   内存或者redis或者mysql  数据一致性  性能问题
		//1.获取用户所在房间信息
		UserDto user = userRepository.queryUser(username);
		if(user.getRoomId()<=0){
			return true;
		}
		return userRepository.updateUser(0,username);
	}

	public List<RoomDto> queryAll(){
		return roomRepository.queryAll();
	}
	
}
