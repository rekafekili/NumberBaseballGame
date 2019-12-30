package server;


import java.io.IOException;
import java.net.*;

/*
 * 
 */
public class ServerUDP {
	private InetAddress serverIp;	// 대상 서버 IP
	private DatagramSocket socket;
	
	public ServerUDP(int port){
		try {
			socket = new DatagramSocket(port);
			System.out.println("--- Server Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Server Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message, InetAddress ip, int port) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ip, port);
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
