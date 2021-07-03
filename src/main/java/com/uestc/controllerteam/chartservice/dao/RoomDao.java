package com.uestc.controllerteam.chartservice.dao;

import java.util.List;

import com.uestc.controllerteam.chartservice.model.RoomControlData;
import org.apache.ibatis.annotations.Mapper;

import com.uestc.controllerteam.chartservice.dto.RoomDto;

@Mapper
public interface RoomDao {

	List<RoomDto> queryAll();

	int insert(String name);

	List<RoomDto> queryRoomRecord(RoomControlData controlData);

}
