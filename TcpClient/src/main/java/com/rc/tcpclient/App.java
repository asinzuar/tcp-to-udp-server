package com.rc.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Writes messages to TCP server.
 *
 */
public class App 
{
    public static void main(String[] args) {
    	if (args.length != 2 || args[0].equals("-h")) {
    		System.out.println("Usage: java -jar <jarname> <TCP server> <TCP server port>");
    		return;
    	}
    	
    	String server = args[0];
    	int port = Integer.parseInt(args[1]);

    	try (   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    			Socket socket = new Socket(server, port);
    			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)
    	) {
    		System.out.println("TCP client started. Type your messages and press "
    				+ "enter to send to server.");
    		String line;
    		
    		while ((line = br.readLine()) != null) {
    			pw.println(line);
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
