package client;

import java.io.IOException;
import java.net.*;

/*
 * 클라이언트가 사용할 UDP 통신을 담당하는 클래스
 * 
 *  1. Username 전송
 *  2. Start 명령어 전송
 *  3. 3자리 숫자 전송
 *  4. 도중에 Stop 명령어 전송 가능
 * 
 */
public class ClientUDP {
	private InetAddress serverIp;	// 대상 서버 IP
	private DatagramSocket socket;
	private static int SERVERPORT = 3000;
	
	public ClientUDP(String ip, int port){
		try {
			serverIp = InetAddress.getByName(ip);
			socket = new DatagramSocket(port);
			System.out.println("--- Client Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Client Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverIp, SERVERPORT);
		socket.send(dp);
	}
	
	public String receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return new String(dp.getData());
	}
	
	public void closeUDP() {
		socket.close();
		System.out.println("--- Client Socket Closed ---");
	}
}
