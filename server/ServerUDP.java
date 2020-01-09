package server;


import java.io.IOException;
import java.net.*;

public class ServerUDP {
	private DatagramSocket socket;
	private InetAddress clientIp;
	private int clientPort;
	
	public ServerUDP(int port){
		try {
			socket = new DatagramSocket(port);
			System.out.println("--- Server Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Server Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	public void setIpPort(InetAddress ip, int port) {
		this.clientIp = ip;
		this.clientPort = port;
	}
	
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, clientIp, clientPort);
		socket.send(dp);
	}
	
	public void sendMessage(int port, String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, clientIp, port);
		socket.send(dp);
	}
	
	public DatagramPacket receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return dp;
	}
	
	public void closeUDP() {
		socket.close();
		System.out.println("--- Server Socket Closed ---");
	}
}


