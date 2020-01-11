package onlyudp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * UDP 통신을 확인하기 위한 EchoClient
 * @author SeongYun
 *
 */
public class EchoClient {
	// 포트 번호, 서버 IP, 버퍼 최대 크기를 전역 변수로 초기화
	private static int CLIENTPORT = 2019;	// 송신 소켓 생성할 때 사용할 포트 번호
	private static int SERVERPORT = 2020;	// 패킷을 보낼 때 사용할 서버의 포트 번호
	private static String SERVERIP = "127.0.0.1";	// 서버 IP 주소 지정 (localhost : 테스트용)
	private static int MAX_LEN = 512;	// Buffer의 최대 크기 지정
	
	public static void main(String[] args) {
		// EchoServer 참고
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(CLIENTPORT);
			System.out.println("--- EchoClient Socket Created! ---");
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Enter a Message : ");
			String message = sc.next().trim();
			byte buffer[] = new byte[MAX_LEN];
			buffer = message.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(SERVERIP), SERVERPORT);
			socket.send(packet);
			System.out.println("--- Send the Message to Server ---");
			
			socket.receive(packet);
			System.out.println("SERVER > " + new String(packet.getData()).trim());
			
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("!!! EchoClient Socket Failed! !!!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! DatagramPacket IOException !!!");
		}
		
		socket.close();
		System.out.println("--- EchoClient Socket Closed! ---");
		System.exit(0);
	}

}

