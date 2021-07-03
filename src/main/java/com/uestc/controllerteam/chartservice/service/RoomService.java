package com.uestc.controllerteam.chartservice.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.model.User;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class RoomService {



	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	private Map<Integer,List<String>> roomInfo;


	public List<String> queryRoomUsers(int roomId){
		return roomInfo.getOrDefault(roomId,new LinkedList<>());
	}

	public boolean enterRoom(int roomId,String username){
		//1.判断房间存在
		RoomDto roomDto = roomRepository.queryRoomById(roomId);
		if(roomDto == null || StringUtils.isEmpty(roomDto.getName())){
			return false;
		}
		//2.登录房间
		List<String> userList = roomInfo.get(roomId);
		if(CollectionUtils.isEmpty(userList)){
			userList = new ArrayList<>();
			userList.add(username);
			roomInfo.put(roomId,userList);
		}else{
			userList.add(username);
		}
		return true;
	}

	public boolean roomLeave(User user){
		//1.获取用户所在房间信息
		//可用redis缓存，或者直接存到数据库中的字段上面，假设存在数据库字段中
		if(user.getRoomId() <= 0){
			return true;
		}
		user.setRoomId(0);
		return userRepository.updateUser(user);
	}

	List<RoomDto> queryAll(){
		return roomRepository.queryAll();
	}

}
