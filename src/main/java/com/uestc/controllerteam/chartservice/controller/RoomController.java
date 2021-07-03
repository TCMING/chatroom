package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.model.Room;
import com.uestc.controllerteam.chartservice.model.RoomControlData;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@ResponseBody
public class RoomController {

	private Logger         logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping(value="/room",method = {POST})
	public void room(@RequestBody Room room) {
		roomRepository.saveRoom(room.getName());
	}

	@RequestMapping(value="/roomAll",method = {POST})
	public String roomAll(@RequestBody RoomControlData roomControl) {
		List<RoomDto> roomDtoList = roomRepository.queryAll();
		return GsonUtils.toJsonString(roomDtoList);
	}

	@RequestMapping(value="/roomList",method = {POST})
	public String roomList(@RequestBody RoomControlData roomControl) {
		List<RoomDto> roomDtoList = roomRepository.queryRoomRecord(roomControl);
		return GsonUtils.toJsonString(roomDtoList);
	}


}
