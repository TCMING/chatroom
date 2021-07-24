package com.uestc.controllerteam.chartservice.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.repository.CacheRepository;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RoomService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CacheRepository cacheRepository;

	//暂时不用内存淘汰，维护全量的room-user信息
	private ConcurrentHashMap<Integer, Set<String>> roomUsersCache = new ConcurrentHashMap<>(1000);

	public List<String> queryRoomUsers(int roomId){
		Set<String> usernames = roomUsersCache.getOrDefault(roomId,new HashSet<>());
		return new ArrayList<>(usernames);
	}

	// TODO: 2021/7/5 并发同步？ 缓存数据一致性？ 接口幂等性？
	public synchronized boolean enterRoom(int roomId,String username){
		try {
			//1.判断房间存在
			RoomDto roomDto = roomRepository.queryRoomById(roomId);
			if(roomDto == null || StringUtils.isEmpty(roomDto.getName())){
				return false;
			}
			//2.用户状态更新
			UserDto user = userRepository.queryUser(username);
			int originRoomId = user.getRoomId();
			//3.最新房间
			user.setRoomId(roomId);
			//4.退出房间
			if(originRoomId > 0){
				Set<String> originUsers = roomUsersCache.get(originRoomId);
				if(originUsers != null && originUsers.contains(username)){
					originUsers.remove(username);
				}
			}
			//5.登录房间
			Set<String> users = roomUsersCache.get(roomId);
			if(users == null){
				users = new HashSet<>();
				roomUsersCache.put(roomId,users);
			}
			users.add(username);
			return true;
		} catch (Exception e) {
			logger.error("error" , e);
			return false;
		}
	}

	//尝试取消sync
	public boolean roomLeave(String username){
		try {
			// TODO: 2021/7/4 题意不要求持久化在线人数   内存或者redis或者mysql  数据一致性  性能问题
			//1.获取用户所在房间信息
			UserDto user = userRepository.queryUser(username);
			if(user.getRoomId()<=0){
				return true;
			}
			user.setRoomId(0);
			return true;
		} catch (Exception e) {
			logger.error("error" , e);
			return false;
		}
	}

	public List<RoomDto> queryAll(){
		return roomRepository.queryAll();
	}
	
}
