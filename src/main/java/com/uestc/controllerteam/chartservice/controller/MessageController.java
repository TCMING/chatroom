package com.uestc.controllerteam.chartservice.controller;

import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import com.uestc.controllerteam.chartservice.model.QueryControlData;
import com.uestc.controllerteam.chartservice.model.UserRequest;
import com.uestc.controllerteam.chartservice.repository.MessageRepository;
import com.uestc.controllerteam.chartservice.repository.UserRepository;
import com.uestc.controllerteam.chartservice.service.MessageService;
import com.uestc.controllerteam.chartservice.utils.BizCheckUtils;
import com.uestc.controllerteam.chartservice.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MessageController  extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageRepository messageRepository;

	@RequestMapping(value="/message/send")
	public void receive(@RequestBody MessageRetrive message , @RequestParam(value="username")String username) {

		BizCheckUtils.check(messageService.recvMessage(username,message.getId(),message.getText())
				, "无效输入");
	}

	@RequestMapping(value="/message/retrieve")
	public String pullMessage(@RequestBody QueryControlData queryControlData , @RequestParam(value="username")String username){
		UserDto userDto = userRepository.queryUser(username);
		if(userDto==null){
			BizCheckUtils.check(false,"无效输入");
		}

		List<MessageRetrive> messageRetrives = messageService.pullMessage(userDto.getRoomId() , queryControlData);
		return GsonUtils.toJsonString(messageRetrives);
	}

}
