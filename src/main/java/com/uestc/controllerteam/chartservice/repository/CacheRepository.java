package com.uestc.controllerteam.chartservice.repository;

import com.uestc.controllerteam.chartservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheRepository {
    //缓存
    private ConcurrentHashMap<Integer, Set<String>> roomUserCache;

    @Autowired
	private UserRepository userRepository;

    @Autowired
	private UserDao userDao;

//    public Set<String> queryRoomUsers(int roomId){
//        return roomUserCache.getOrDefault(roomId,new HashSet<>());
//    }

	public List<String> queryRoomUsers(int roomId){
		return userDao.queryUsersByRoomId(roomId);
	}

    public void addRoomUsers(int roomId, String username){
        Set<String> userSet = roomUserCache.get(roomId);
		if(CollectionUtils.isEmpty(userSet)){
			userSet = new HashSet<>();
			userSet.add(username);
			roomUserCache.put(roomId,userSet);
		}else{
			if(!userSet.contains(username)){
				userSet.add(username);
			}
		}
    }

	public void deleteRoomUsers(int roomId, String username){
		Set<String> userSet = roomUserCache.get(roomId);
		if(CollectionUtils.isEmpty(userSet)){
			return;
		}else if (!userSet.contains(username)) {
			return;
		} else {
			userSet.remove(username);
			return;
		}
	}
}
