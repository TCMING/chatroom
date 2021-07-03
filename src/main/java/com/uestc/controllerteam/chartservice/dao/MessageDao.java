package com.uestc.controllerteam.chartservice.dao;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {

	MessageDto queryMessage(String messageId);

	int saveMessage(MessageDto messageDto);

}
