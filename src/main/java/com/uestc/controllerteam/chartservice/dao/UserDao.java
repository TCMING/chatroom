package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

	List<RoomDto> queryAll();

	UserDto queryUser(String userName);

	int saveUser(UserDto userDto);

	int updateUser(int roomId , String username);

	List<String> queryUsersByRoomId(int roomId);

}
