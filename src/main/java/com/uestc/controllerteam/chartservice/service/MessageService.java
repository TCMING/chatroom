package com.uestc.controllerteam.chartservice.service;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.model.User;
import com.uestc.controllerteam.chartservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	public boolean recvMessage(User user, String id, String text){
		MessageDto messageDto = messageRepository.queryMessage(id);
		if(messageDto != null){
			return true;
		}
		long curTime = System.currentTimeMillis();
		messageDto = new MessageDto(id,text,user.getUsername(),user.getRoomId(),curTime);
		return messageRepository.saveMessage(messageDto);
	}

	public List<MessageRetrive> pullMessage(User user, QueryControlData controlData){
		List<MessageDto> messageDtoList =  messageRepository.queryMessages(user.getUsername(),user.getRoomId(),
				controlData.getPageIndex(),controlData.getPageSize());

		List<MessageRetrive> messageRetrives = new LinkedList<>();
		if(!CollectionUtils.isEmpty(messageDtoList)){
			for(MessageDto messageDto : messageDtoList){
				MessageRetrive message = new MessageRetrive(
						messageDto.getId(),messageDto.getText(),messageDto.getTimestamp());
				messageRetrives.add(message);
			}
		}
		return messageRetrives;
	}

}
