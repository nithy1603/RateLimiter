package com.interview.ratelimiter.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ratelimiter.service.RateLimiterService;


@RestController
public class RateLimiterController {
	
	
	@Autowired
	RateLimiterService rservice;
	
	 private static final Logger logger = LoggerFactory.getLogger(RateLimiterController.class);


	@GetMapping(value="/ratelimiter/getMessage/{maxReqPerSec}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMessage(@PathVariable int maxReqPerSec){
		try {
			    String str=  "The max request per second is "+ maxReqPerSec;
				return new ResponseEntity(str,HttpStatus.OK);
			
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	
	@PostMapping(value="/ratelimiter/addUser/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@PathVariable String clientId){
		try {
			    rservice.addUser(clientId);
				return new ResponseEntity("User Added",HttpStatus.OK);
			
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	
	@PostMapping(value="/ratelimiter/removeUser/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeUser(@PathVariable String clientId){
		try {
			    rservice.removeUser(clientId);
				return new ResponseEntity("User Removed",HttpStatus.OK);
			
		}catch(Exception ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	
	@GetMapping(value="/ratelimiter/getIsAllowed/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getIsAllowed(@PathVariable String clientId){
		try {
			    Boolean str=  rservice.isReqAllowed(clientId);
				return new ResponseEntity(str,HttpStatus.OK);
			
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	
}   		
