package onlyudp;

import java.io.IOException;
import java.net.*;

/**
 * UDP 통신을 확인하기 위한 EchoServer
 * @author SeongYun
 */
public class EchoServer {
	
	// 개방 할 포트 번호와 버퍼의 최대 크기 설정
	private static int port = 2020;		// Server Port
	private static int MAX_LEN = 512;	// Maximum Length of Buffer
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// DatagramSocket 초기화와 로그 메시지
			socket = new DatagramSocket(port);
			System.out.println("--- EchoServer Socket Created! ---");
			
			// 버퍼로 사용하기 위한 바이트 배열 생성
			byte buffer[] = new byte[MAX_LEN];
			
			// 방금 생성한 바이트 배열을 인자값으로 넣어 DatagramPacket 생성, UDP 패킷을 수신했을 때 저장 할 패킷
			DatagramPacket clientPacket = new DatagramPacket(buffer, buffer.length);
			System.out.println("--- Waiting for Receiving ---");
			
			// UDP 패킷이 수신될 때까지 대기, 수신 되면 다음 코드로 넘어감
			socket.receive(clientPacket);
			
			// 수신 받은 패킷에 담겨 있는 송신자의 IP, Port번호, Data 를 구분하여 저장
			InetAddress clientIP = clientPacket.getAddress();
			int clientPort = clientPacket.getPort();
			String clientMessage = new String(clientPacket.getData()).trim();	// trim() : String의 처음과 끝에 있는 공백을 삭제
			System.out.println(clientIP + ":" + port + " >> " + clientMessage);
			
			buffer = clientMessage.getBytes(); // getBytes() : String 을 Byte Array로 변환
			clientPacket = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			
			// 패킷 전송
			socket.send(clientPacket);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("!!! EchoServer Socket Failed! !!!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! DatagramPacket IOException !!!");
		}
		
		socket.close();
		System.out.println("--- EchoServer Socket Closed! ---");
		System.exit(0);
	}
}