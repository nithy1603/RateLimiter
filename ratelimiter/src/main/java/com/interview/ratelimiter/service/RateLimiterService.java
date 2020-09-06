package com.interview.ratelimiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.ratelimiter.util.SlidingWindowCounter;


@Service
public class RateLimiterService {
	
	@Autowired
	SlidingWindowCounter scounter;

	public void addUser(String clientId) throws Exception {
         scounter.addUser(clientId);
	}
	
	
	public void removeUser(String clientId) throws Exception {
        scounter.removeUser(clientId);
	}
	
	public boolean isReqAllowed(String clientId) throws Exception {
        return scounter.isReqAllowed(clientId);
	}
	
	
	
}
