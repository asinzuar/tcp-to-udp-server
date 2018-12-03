package com.rc.gatewayserver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Bounded Queue based Message Buffer for sharing string messages between reader
 * and writer threads.
 * 
 *
 */
public class Buffer {

	private BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
	
	/**
	 * Get message from Buffer. This method blocks until data is available.
	 * 
	 */
	public String getMessage() throws InterruptedException {
		return queue.take();
	}
	
	/**
	 * Get message from buffer. If no data is available, this method waits
	 * for specified amount of time, then returns.
	 * 
	 */
	public String getMessage(long timeout,
		       TimeUnit unit) throws InterruptedException {
		return queue.poll(timeout, unit);
	}
	
	/**
	 * Add message to buffer. This method blocks if buffer is full.
	 * 
	 */
	public void addMessage(String message) throws InterruptedException {
		queue.put(message);
	}	
	
}
