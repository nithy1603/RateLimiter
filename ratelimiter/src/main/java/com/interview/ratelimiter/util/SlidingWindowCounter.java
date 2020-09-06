/**
 * 
 */
package com.interview.ratelimiter.util;

/**
 * @author 16578
 *
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Example DataStore class that provides access to user data.
 * Pretend this class accesses a database.
 */

@Service
public class SlidingWindowCounter {

	@Value("${ratelimiter.maxreqpersec}")
	private int requestLimit;
	
	private Map<String, TimestampCount> userMap;
	
    public SlidingWindowCounter() {
    	userMap =  new ConcurrentHashMap<>();
    }
    
    public void addUser(String userId) throws Exception {
    	if(userMap.containsKey(userId)) {
    		throw new Exception("User Already Exists");
    	}
    	userMap.put(userId, new TimestampCount());
    }
    
    public void removeUser(String userId) {
    	if(userMap.containsKey(userId)) {
    		userMap.remove(userId);
    	}
    }
    
	public boolean isReqAllowed(String userId) throws Exception {
		if(!userMap.containsKey(userId)) {
			throw new Exception("User Does not exist! Add the User to Continue!");
		}
		
		TimestampCount tcounts = userMap.get(userId);
		
	    synchronized (tcounts) {
	    	long curTime = System.currentTimeMillis() / 1000 ; //seconds
	        tcounts.removeOldTimestamps(curTime);
	        tcounts.getTimeCountMap().put(curTime,tcounts.getTimeCountMap().getOrDefault(curTime,0)+1);
	        tcounts.setTotalCount(tcounts.getTotalCount()+1);
	        return tcounts.getTotalCount() <= requestLimit;
	    }
	  }
	
	
}