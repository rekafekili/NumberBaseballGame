package client;

import java.io.IOException;
import java.util.Scanner;

/**
 * UDP 통신 + 숫자야구게임 + 기타 기능을 구현한 Client
 * @author SeongYun
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		// 키보드로부터 입력받기 위해 Scanner 인스턴스
		Scanner sc = new Scanner(System.in);
		String receivedMessage;	// 수신 받은 데이터를 저장할 문자열 변수

		// UDP 통신 시작, 소켓 생성
		ClientUDP client = new ClientUDP("127.0.0.1", 2019);

		// 첫 송신은 UserName을 전송한다.
		System.out.print("Enter your name : ");
		String username = sc.next();
		try {
			client.sendMessage(username); // 메시지 전송
			while (true) {
				receivedMessage = client.receiveMessage().trim(); // 수신 받은 메시지 저장
				
				System.out.println("SERVER >> " + receivedMessage); // 수신 받은 메시지 출력
				
				if(receivedMessage.equals("GOAL!")){	// 수신 받은 메시지가 GOAL일 경우
					break;	// 종료
				}
				else if(receivedMessage.equals("STOP")) {	// 수신 받은 메시지가 STOP일 경우
					client.sendMessage("EXIT");	// EXIT 전송
					break;	// 종료
				}
				else {
					// 사용자로부터 입력 받음
					System.out.print(username + " : ");
					String message = sc.next() + "";
					
					// 메시지 전송
					client.sendMessage(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

		client.closeUDP();
		System.exit(0);
	}
}