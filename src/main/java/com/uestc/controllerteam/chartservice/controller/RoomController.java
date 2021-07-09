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
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import com.uestc.controllerteam.chartservice.utils.PassToken;
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
	public void room(@RequestBody Room room) {
		roomRepository.saveRoom(room.getName());
	}

	@RequestMapping(value="/room/{roomid}/enter",method = {PUT})
	public void enterRoom(@PathVariable int roomid , @RequestParam(value="username")String username) {
		// TODO: 2021/7/4
//		RoomDto roomDto = roomRepository.queryRoomById(roomid);
//		BizCheckUtils.checkNull(roomDto,"房间不存在");
//		// TODO: 2021/7/7
//		//查询用户房间号，暂未使用缓存
//		UserDto userDto = userRepository.queryUser(username);
//		if(userDto.getRoomId()!=roomid){
//			userDto.setRoomId(roomid);
//			userRepository.updateUser(roomid,username);
//		}

		BizCheckUtils.check(roomService.enterRoom(roomid , username) , "房间不存在");

	}

	@RequestMapping(value="/roomLeave",method = PUT)
	public void roomLeave(@RequestParam(value="username")String username) {
		// TODO: 2021/7/4
		BizCheckUtils.check(roomService.roomLeave(username) , "异常");
	}

	@PassToken
	@RequestMapping(value="/room/{roomid}",method = {GET})
	public String roomIdList(@PathVariable String roomid) {
		int roomId = Integer.parseInt(roomid);
		RoomDto roomDto = roomRepository.queryRoomById(roomId);
		return roomDto.getName();
	}

	@PassToken
	@RequestMapping(value="/room/{roomid}/users",method = {GET})
	public String roomUserList(@PathVariable String roomid) {
		int roomId = Integer.parseInt(roomid);
		Set<String> users = roomService.queryRoomUsers(roomId);
		return GsonUtils.toJsonString(users);
	}

	@PassToken
	@RequestMapping(value="/roomList",method = {POST})
	public String roomList(@RequestBody QueryControlData roomControl) {
		List<RoomDto> roomDtoList = roomRepository.queryRoomRecord(roomControl);
		return GsonUtils.toJsonString(roomDtoList);
	}

	// TODO: 2021/7/6 自测用
	@PassToken
	@RequestMapping(value="/roomAll",method = {POST})
	public String roomAll(@RequestBody QueryControlData roomControl) {
		List<RoomDto> roomDtoList = roomRepository.queryAll();
		return GsonUtils.toJsonString(roomDtoList);
	}


}
