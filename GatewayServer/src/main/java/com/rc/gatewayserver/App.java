package com.rc.gatewayserver;

/**
 * Main application class for Gateway server. 
 *
 */
public class App 
{
    public static void main(String[] args) throws InterruptedException {
    	
    	if (args.length != 4 || args[0].equals("-h")) {
    		System.out.println("Usage: java -jar <jarname> <TCP server port> <UDP server port>"
    				+ " <UDP multicast port> <UDP multicast group address>");
    		return;
    	}
    	
    	int tcpPort = Integer.parseInt(args[0]);
    	int udpPort = Integer.parseInt(args[1]);
    	int udpClientPort = Integer.parseInt(args[2]);
    	String multicastGroupAddr = args[3];
    	
    	Buffer communicationBuffer = new Buffer();
    	Thread reader = new TCPReader(tcpPort, communicationBuffer);
    	Thread multicaster = new UDPWriter(udpPort, multicastGroupAddr, 
    			udpClientPort, communicationBuffer);
    	
    	reader.start();
    	multicaster.start();    	
    	
    	System.out.println("Gateway Server started.");
    	System.out.println("Listening for TCP connections on port " + tcpPort);
    	System.out.println("Broadcasting messages recieved to group " + multicastGroupAddr
    			+ ":" + udpClientPort);
    	
    	reader.join();
    	
    	// Once reader is finished, tell writer thread to stop, and wait.
    	((UDPWriter) multicaster).markForStop();
    	multicaster.join();
    	
    	System.out.println("Server terminated.");
    }
}
