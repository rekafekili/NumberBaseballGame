package server;


import java.io.IOException;
import java.net.*;

public class ServerUDP {
	private DatagramSocket socket;
	private InetAddress clientIp;
	private int clientPort;
	
	/**
	 * ServerUDP 생성자로써, DatagramSocket을 생성한다.
	 * @param port DatagramSocket 인자값
	 */
	public ServerUDP(int port){
		try {
			socket = new DatagramSocket(port);
			System.out.println("--- Server Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Server Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * IP와 Port 번호를 설정하는 메소드
	 * @param ip IP주소
	 * @param port Port번호
	 */
	public void setIpPort(InetAddress ip, int port) {
		this.clientIp = ip;
		this.clientPort = port;
	}
	
	/**
	 * 인자값으로 받은 문자열을 패킷으로 만들고 전송하는 메소드
	 * @param message 패킷으로 보낼 문자열
	 * @throws IOException 입출력에 문제가 생길 때 발생
	 */
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, clientIp, clientPort);
		socket.send(dp);
	}
	
	/**
	 * 수신한 패킷을 반환하는 메소드
	 * @return 수신한 DatagramPacket
	 * @throws IOException
	 */
	public DatagramPacket receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return dp;
	}
	
	/**
	 * UDP 소켓을 해제하고, 로그 메시지 출력
	 */
	public void closeUDP() {
		socket.close();
		System.out.println("--- Server Socket Closed ---");
	}
}


