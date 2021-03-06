package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.RoomDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.Room;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.repository.RoomRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import com.uestc.controllerteam.chartservice.service.RoomService;
import com.uestc.controllerteam.chartservice.service.UserService;
import com.uestc.controllerteam.chartservice.utils.BizCheckUtils;
import com.uestc.controllerteam.chartservice.utils.ChatException;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import com.uestc.controllerteam.chartservice.utils.PassToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@ResponseBody
public class RoomController extends AbstractController{

	private Logger	logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@RequestMapping(value="/room",method = {POST})
	public String room(@RequestBody Room room) {
		try {
			BizCheckUtils.check(room != null && StringUtils.isNotBlank(room.getName()),"Invalid input");
			return roomRepository.saveRoom(room.getName());
		} catch (Exception e) {
			logger.error("error",e);
			throw new ChatException("Invalid input");
		}
	}

	@RequestMapping(value="/room/{roomid}/enter",method = {PUT})
	public void enterRoom(@PathVariable String roomid , @RequestAttribute(value="username")String username) {
		BizCheckUtils.check(roomService.enterRoom(Integer.parseInt(roomid) , username) , "???????????????");
	}

	@RequestMapping(value="/roomLeave",method = PUT)
	public void roomLeave(@RequestAttribute(value="username")String username) {
		// TODO: 2021/7/4
		BizCheckUtils.check(roomService.roomLeave(username) , "??????");
	}

	@PassToken
	@RequestMapping(value="/room/{roomid}",method = {GET})
	public String roomIdList(@PathVariable String roomid) {
		int roomId = Integer.parseInt(roomid);
		try {
			RoomDto roomDto = roomRepository.queryRoomById(roomId);
			BizCheckUtils.checkNull(roomDto,"invalid roomId");
			return roomDto.getName();
		}catch (Exception e){
			throw new ChatException("invalid roomId");
		}
	}

	@PassToken
	@RequestMapping(value="/room/{roomid}/users",method = {GET})
	public String roomUserList(@PathVariable String roomid) {
		int roomId = Integer.parseInt(roomid);
//		RoomDto roomDto = roomRepository.queryRoomById(roomId);
//		if(roomDto==null){
//			throw new ChatException("Invalid Room ID");
//		}
		return GsonUtils.toJsonString(roomService.queryRoomUsers(roomId));
	}

	@PassToken
	@RequestMapping(value="/roomList",method = {POST})
	public String roomList(@RequestBody QueryControlData roomControl) {
		if(roomControl.getPageIndex()<0){
			throw new ChatException("invalid input");
		}
		List<RoomDto> roomDtoList = roomRepository.queryRoomRecord(roomControl);
		return GsonUtils.toJsonString(roomDtoList);
	}

	// TODO: 2021/7/6 ?????????
	@PassToken
	@RequestMapping(value="/roomAll",method = {POST})
	public String roomAll(@RequestBody QueryControlData roomControl) {
		List<RoomDto> roomDtoList = roomRepository.queryAll();
		return GsonUtils.toJsonString(roomDtoList);
	}


}
