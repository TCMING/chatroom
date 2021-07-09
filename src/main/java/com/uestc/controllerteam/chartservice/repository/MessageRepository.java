package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.MessageDao;
import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public MessageDto queryMessage(String id){
        return messageDao.queryMessage(id);
    }

    public List<MessageRetrive> queryMessages(int roomId, int pageIndex, int pageSize){
        HashMap<String,Integer> params = new HashMap<>();
        params.put("roomId" , roomId);
        params.put("pageIndex" , pageIndex);
        params.put("pageSize" , pageSize);
        return messageDao.queryAllMessage(params);
    }


}
