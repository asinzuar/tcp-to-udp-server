package com.rc.gatewayserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class UDPWriter extends Thread {

	private final int port;
	
	private final int clientPort;

	private final Buffer buffer;
	
	private final String multicastGroup;
	
	private boolean stop;

	public UDPWriter(int port, String multicastGroup, int clientPort, Buffer buffer) {
		this.port = port;
		this.buffer = buffer;
		this.multicastGroup = multicastGroup;
		this.clientPort = clientPort;
	}
	
	public void markForStop() {
		this.stop = true;
	}

	@Override
	public void run() {
		try ( DatagramSocket socket = new DatagramSocket(this.port) ) {
			while (!stop) {
				String message = this.buffer.getMessage(10, TimeUnit.SECONDS);	
				if (message == null) {
					continue;
				}
				
				byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
				
				InetAddress address = InetAddress.getByName(this.multicastGroup);
				
				DatagramPacket datagram = new DatagramPacket(bytes, bytes.length,
						address, this.clientPort);
				
				try {
					socket.send(datagram);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
