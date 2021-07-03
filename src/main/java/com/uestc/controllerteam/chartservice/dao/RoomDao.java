package com.uestc.controllerteam.chartservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Mapper
public interface RoomDao {
	
	List<RoomDto> queryAll();

}
