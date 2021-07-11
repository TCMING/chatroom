package com.uestc.controllerteam.chartservice.service;

import java.util.*;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.repository.CacheRepository;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	// TODO: 2021/7/11 从缓存中获取
//	public Set<String> queryRoomUsers(int roomId){
//		return cacheRepository.queryRoomUsers(roomId);
//	}

	public List<String> queryRoomUsers(int roomId){
		return cacheRepository.queryRoomUsers(roomId);
	}

	// TODO: 2021/7/5 并发同步？ 缓存数据一致性？ 接口幂等性？重复多次传入已进入的房间号，返回成功吗？
	public synchronized boolean enterRoom(int roomId,String username){
		try {
			//1.判断房间存在
			RoomDto roomDto = roomRepository.queryRoomById(roomId);
			if(roomDto == null || StringUtils.isEmpty(roomDto.getName())){
				return false;
			}
			//2.登录房间
			UserDto user = userRepository.queryUser(username);
			int oldRoomId = user.getRoomId();
			if(oldRoomId!=roomId){
				if(userRepository.updateUser(roomId,username)){
//					//删除旧缓存，不抛异常即成功
//					cacheRepository.deleteRoomUsers(oldRoomId,username);
//					//更新缓存
//					cacheRepository.addRoomUsers(roomId , username);
				}

			}
			return true;
		} catch (Exception e) {
			logger.error("error" , e);
			return false;
		}
	}

	public synchronized boolean roomLeave(String username){
		try {
			// TODO: 2021/7/4 题意不要求持久化在线人数   内存或者redis或者mysql  数据一致性  性能问题
			//1.获取用户所在房间信息
			UserDto user = userRepository.queryUser(username);
			if(user.getRoomId()<=0){
				return true;
			}
			//先处理缓存
//			cacheRepository.deleteRoomUsers(user.getRoomId() , username);
			return userRepository.updateUser(0,username);
		} catch (Exception e) {
			logger.error("error" , e);
			return false;
		}
	}

	public List<RoomDto> queryAll(){
		return roomRepository.queryAll();
	}
	
}
