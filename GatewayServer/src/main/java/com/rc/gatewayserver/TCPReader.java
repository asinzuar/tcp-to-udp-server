package com.rc.gatewayserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPReader extends Thread {
	
	private final int port;
	
	private final Buffer buffer;

	public TCPReader(int port, Buffer buffer) {
		this.port = port;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		try (
			ServerSocket serverSocket = new ServerSocket(this.port);
			Socket clientSocket = serverSocket.accept();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()))
		) {
			System.out.println("TCP Connection established.");
			String line;
			while ((line = in.readLine()) != null) {
				this.buffer.addMessage(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
