package server;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * UDP 통신 + 숫자야구게임 + 기타 기능을 구현한 Server
 * @author SeongYun
 *
 */
public class ServerMain {
	// Warning 횟수를 전역변수로 선언
	private static int warningCount = 0;
	public static void main(String[] args) throws IOException {
		ServerUDP socket = new ServerUDP(3000);	// ServerUDP 생성
		String userName = null;	// 유저 이름을 저장 할 변수
		String sendMessage = null;	// Client에게 보낼 메시지
		
		ServerGame gameOperator = new ServerGame();	// 숫자야구게임 진행과 관련된 객체 인스턴스

		Timer alertTimer = null;	// 대기 시간 Timer
		
		while(true) {
			try {
				// 수신 대기, 수신 했을 경우 dp 에 저장
				DatagramPacket dp = socket.receiveMessage();
				
				// 수신한 패킷의 데이터와 ip, port 를 분리하여 저장한다.
				String receivedMessage = new String(dp.getData()).trim();
				System.out.println("Received Log : " + receivedMessage);
				socket.setIpPort(dp.getAddress(), dp.getPort());
				
				// userName이 초기화 했을 때와 똑같다면 처음으로 수신된 상태이므로 첫 메시지는 userName으로 저장
				if(userName==null) { 
					userName = receivedMessage;
					sendMessage = "--- Welcome! Please Enter the number between 100 and 1000 ---";	// 환영 문구
					System.out.println("--- [" + userName + "] is Entered ---"); // 로그 메시지
					gameOperator.createGoal();	// 문제 생성
				}
				else {
					alertTimer.cancel();	// 10초 단위로 WarningCount 를 1씩 증가하는 Timer 종료
					
					if(receivedMessage.equals("STOP")) {	// STOP 명령어가 수신됐을 경우
						sendMessage = "STOP";	// STOP 을 전송하고 Client로부터 EXIT가 수신되면 종료
					}
					else if(receivedMessage.equals("EXIT")) {
						break;		// Client가 접속을 끊은 것을 확인하고 해제
					}
					else if(receivedMessage.equals("HINT")) {	// HINT 명령어가 수신됐을 경우
						sendMessage = gameOperator.makeHintMessage();
					}
					else if(100 < Integer.parseInt(receivedMessage) && Integer.parseInt(receivedMessage) < 1000) {	// 예상값이 수신 됐을 경우
						System.out.println("Received Number : " + receivedMessage);
						sendMessage = gameOperator.calcNumberBaseball(receivedMessage);
					}
					else {	// 그 외 메시지가 수신될 경우 안내 메시지
						sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
					}
					
					// Message를 송신하기 전에 WarningCount를 확인
					if(warningCount > 0 && warningCount < 3) {	// Warning Count가 0 초과 3 미만일 경우
						sendMessage += (" [Warning" + warningCount + "]"); // Client에게 전송하는 메시지 끝에 WarningCount를 붙여서 전송 
					}
					else if(warningCount >= 3) {	// WarningCount가 3이상일 경우 STOP 전송
						sendMessage = "STOP";
					}
				}
				socket.sendMessage(sendMessage);	// 메시지 전송
				
				// alertTimer가 cancel 되면 Timer와 TimerTask가 종료되기 때문에 새로 인스턴스 해주어야 한다.
				alertTimer = new Timer();	// Timer 초기화
				TimerTask alertTask = new TimerTask() {
					@Override
					public void run() {
						warningCount++;
					}
				};
				
				alertTimer.scheduleAtFixedRate(alertTask, 10000, 10000);	// 10초가 지나면 WarningCount가 증가하고, 그 이후에 10초가 지날때마다 WarningCount가 증가한다.
				
				if(sendMessage.equals("GOAL!")) break;	// 전송한 메시지가 GOAL일 경우 서버 종료(게임 종료)
			} catch (IOException e) {
				break;
			} catch (NumberFormatException nfe) {
				// Integer.parseInt() 메소드에서 문자열을 Integer로 해석할 때 정수가 아닌 문자열이면 발생한다.
				sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
				socket.sendMessage(sendMessage);
			}
		}
		socket.closeUDP();
		System.exit(0);
	}
}