package com.uestc.controllerteam.chartservice.dao;

import java.util.List;

import com.uestc.controllerteam.chartservice.model.PageDto;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import org.apache.ibatis.annotations.Mapper;

import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Mapper
public interface RoomDao {

	List<RoomDto> queryAll();

	void insert(RoomDto roomDto);

	List<RoomDto> queryRoomRecord(PageDto pageDto);

	RoomDto queryRoomById(int roomId);

	RoomDto queryRoomByName(String name);

}
