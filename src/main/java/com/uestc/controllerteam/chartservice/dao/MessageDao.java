package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MessageDao {

	MessageDto queryMessage(String id);

	int saveMessage(MessageDto messageDto);

	List<MessageRetrive> queryAllMessage(HashMap<String,Integer> params);

}
