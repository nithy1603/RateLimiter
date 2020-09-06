package com.interview.ratelimiter.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TimestampCount {
	
	private Map<Long, Integer> tcountMap;
    private int totalCount;

    public TimestampCount() {
    	tcountMap = new ConcurrentHashMap<>();
    	totalCount = 0;
    }
	public void removeOldTimestamps(long curTime) {
		for(Long timestamp : tcountMap.keySet()) {
			if(curTime - timestamp >= 1) {
				totalCount -= tcountMap.get(timestamp);
				tcountMap.remove(timestamp);
			}
		}
	}

	public Map<Long, Integer> getTimeCountMap() {
		return tcountMap;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int i) {
		totalCount = i;
	}

}
