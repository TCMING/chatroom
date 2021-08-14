package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.MessageDao;
import com.uestc.controllerteam.chartservice.dao.MessageRedisDao;
import com.uestc.controllerteam.chartservice.dto.MessageDto;
import com.uestc.controllerteam.chartservice.model.MessageRetrive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 17:45
 * @Version 1.0
 */
@Service
public class MessageRepository {
    @Autowired
    private MessageRedisDao messageRedisDao;

    @Autowired
    private MessageDao messageDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    int workNum = 1;
    int big = 10;
    int med = 5;
    int small = 1;

    private  ArrayDeque queue = new ArrayDeque<MessageDto>();

    private HashSet<String> addedSet = new HashSet<String>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition saveCondition = lock.newCondition();

    private Condition queueCondition = lock.newCondition();

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

//    private  boolean isInitWorker = initWorker(workNum);

    public boolean saveMessage(MessageDto messageDto){
        messageRedisDao.saveMessage(messageDto);
        return true;
    }

//    public boolean saveMessage(MessageDto messageDto){
//        boolean isRuning = true;
//        try {
//            lock.lock();
//            queue.add(messageDto);
//            if(queue.size()>=big){
//                queueCondition.signalAll();
//            }
//            while (isRuning){
//                saveCondition.await();
//                if(addedSet.contains(messageDto.getId())){
//                    isRuning = false;
//                    addedSet.remove(messageDto.getId());
//                }
//            }
//        } catch (Exception e) {
//            logger.error("---",e);
//            return false;
//        }finally {
//            lock.unlock();
//        }
//        return true;
//    }

    public boolean queryMessage(String id){
        return messageRedisDao.queryMessage(id);
    }

    public List<MessageRetrive> queryMessages(int roomId, int pageIndex, int pageSize){
        HashMap<String,Integer> params = new HashMap<>();
        params.put("roomId" , roomId);
        params.put("startIndex" , (-1-pageIndex)*pageSize);
        params.put("pageSize" , pageSize);
//        return messageDao.queryAllMessage(params);
        return messageRedisDao.queryAllMessage(params);

    }

//    public boolean initWorker(int workNum){
//        //批量线程
//        for(int i=0 ; i<workNum ; i++){
//            Thread thread = new Thread(new MessageTask());
//            thread.start();
//        }
//
//        service.scheduleAtFixedRate(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                lock.lock();
//                                                int size = queue.size();
//                                                if(size>=1){
//                                                    List<MessageDto> list = new ArrayList<>(size);
//                                                    Set<String> tempMId = new HashSet<String>(size);
//                                                    for(int i=0 ; i<size ; i++){
//                                                        MessageDto messageDto = (MessageDto) queue.remove();
//                                                        tempMId.add(messageDto.getId());
//                                                        list.add(messageDto);
//                                                    }
//                                                    messageRedisDao.saveMessage(list);
//                                                    addedSet.addAll(tempMId);
//                                                    saveCondition.signalAll();
//                                                }
//                                            } catch (Exception e) {
//                                                logger.error("---",e);
//                                            } finally {
//                                                lock.unlock();
//                                            }
//                                        }
//                                    },
//                1000, 100, TimeUnit.MILLISECONDS);
//
//        return true;
//    }

//    public class MessageTask implements Runnable{
//
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    lock.lock();
//                    int size = queue.size();
//                    if(queue.size()>=1){
//                        List<MessageDto> list = new ArrayList<>(size);
//                        Set<String> tempMId = new HashSet<String>(size);
//                        for(int i=0 ; i<size ; i++){
//                            MessageDto messageDto = (MessageDto) queue.remove();
//                            tempMId.add(messageDto.getId());
//                            list.add(messageDto);
//                        }
//                        messageRedisDao.saveMessage(list);
//                        addedSet.addAll(tempMId);
//                        saveCondition.signalAll();
//                    }else{
//                        queueCondition.await();
//                    }
//
//                } catch (Exception e) {
//                    logger.error("---",e);
//                }finally {
//                    lock.unlock();
//                }
//            }
//
//        }
//    }


}
