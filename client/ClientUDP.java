package client;

import java.io.IOException;
import java.net.*;

public class ClientUDP {
	private InetAddress serverIp;	// 대상 서버 IP
	private DatagramSocket socket;
	private static int SERVERPORT = 3000;
	
	/**
	 * ClientUDP 생성자 : serverIp를 저장하고 소켓 생성
	 * @param ip 서버 IP
	 * @param port 클라이언트가 개방 할 Port
	 */
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
	
	/**
	 * 메시지를 패킷화하여 전송하는 메소드
	 * @param message	전송할 메시지
	 * @throws IOException 패킷 전송 때 발생할 수 있는 예외
	 */
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverIp, SERVERPORT);
		socket.send(dp);
	}
	
	/**
	 * 패킷을 수신하여 그 안에 메시지를 반환하는 메소드
	 * @return	패킷 데이터을 문자열로 변환
	 * @throws IOException
	 */
	public String receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return new String(dp.getData());
	}
	
	/**
	 * UDP 통신 종료 메소드
	 */
	public void closeUDP() {
		socket.close();
		System.out.println("--- Client Socket Closed ---");
	}
}