package com.java4raju.vt.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadController {
	
    private static final Logger log = LoggerFactory.getLogger(LoadController.class);
	
	AtomicInteger rc = new AtomicInteger(1);
	
	@GetMapping("/{secondsPaush}")
	public ResponseEntity<String> get(@PathVariable int secondsPaush) throws InterruptedException {
		
		Thread.sleep(1000*secondsPaush);
		Thread thread = Thread.currentThread();
		
		//log.info("Blocked for: {} seconds ", rc.getAndAdd(secondsPaush));
		
		log.info("Thread in execution: {} ", thread);
		
		return new ResponseEntity<String>("Hello: "+thread, HttpStatus.OK);
	}

}
