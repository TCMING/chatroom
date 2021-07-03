package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

	List<RoomDto> queryAll();

	UserDto queryUser(String userName);

	boolean updateUser(UserDto userDto);

}
