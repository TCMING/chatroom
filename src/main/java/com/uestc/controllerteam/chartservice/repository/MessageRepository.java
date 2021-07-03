package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.MessageDao;
import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.dto.UserDto;
import com.uestc.controllerteam.chartservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class MessageRepository {

    @Autowired
    private MessageDao messageDao;

    public boolean saveMessage(MessageDto messageDto){
        return messageDao.saveMessage(messageDto) == 0;
    }

    public MessageDto queryMessage(String messageId){
        return messageDao.queryMessage(messageId);
    }

    public List<MessageDto> queryMessages(String userName,int roomId,int pageIndex,int pageSize){
        return new LinkedList<>();
    }


}
