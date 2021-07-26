package com.uestc.controllerteam.chartservice.service;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.repository.MessageRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import com.uestc.controllerteam.chartservice.utils.BizCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserRepository userRepository;

	public boolean sendMessage(String username, String id, String text){
		//TODO 幂等性是不是返回true
//		MessageDto messageDto = messageRepository.queryMessage(id);
		//幂等性
//		if(messageDto != null){
//			return false;
//		}

		if(messageRepository.queryMessage(id)){
			return false;
		}

		long curTime = System.currentTimeMillis();
		// TODO: 2021/7/8 查用户的房间
		UserDto userDto = userRepository.queryUser(username);
		if(userDto==null || userDto.getRoomId()==0){
			return false;
		}
		MessageDto messageDto = new MessageDto(id,text, username, userDto.getRoomId(),curTime);
		return messageRepository.saveMessage(messageDto);
	}

	public List<MessageRetrive> pullMessage(int roomId, QueryControlData controlData){

		List<MessageRetrive> messageRetriveList =  messageRepository.queryMessages(roomId,
				controlData.getPageIndex(),controlData.getPageSize());
		return messageRetriveList;
	}

}
