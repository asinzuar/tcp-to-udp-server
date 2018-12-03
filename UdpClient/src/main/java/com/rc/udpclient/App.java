package com.rc.udpclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Reads datagrams from gateway server.
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	if (args.length != 2 || args[0].equals("-h")) {
    		System.out.println("Usage: java -jar <jarname> <Multicast Group address>"
    				+ " <Multicast port>");
    		return;
    	}
    	
    	String multicastGroup = args[0];
    	int port = Integer.parseInt(args[1]);    	
    	
    	InetAddress address = null;
    	MulticastSocket socket = null;
    	try {
    		socket = new MulticastSocket(port);
    		address = InetAddress.getByName(multicastGroup);
    		
    		socket.joinGroup(address);
    		
    		System.out.println("UDP client started. Listening on multicast group " + multicastGroup);
    		
    		while (true) {
    			byte[] buf = new byte[1024];
    			DatagramPacket datagram = new DatagramPacket(buf, buf.length);
    			socket.receive(datagram);
    			
    			String received = new String(datagram.getData(), datagram.getOffset(),
    					datagram.getLength());
                System.out.println("Received from server - " + received);
    		}
    	} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			if (socket != null) {
				try {
					socket.leaveGroup(address);
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket.close();
			}
			
		}
    }
}
