package server;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {
	private static int warningCount = 0;
	public static void main(String[] args) throws IOException {
		ServerUDP socket = new ServerUDP(3000);
		String userName = null;
		String sendMessage = null;
		
		ServerGame gameOperator = new ServerGame();
		
		Timer alertTimer = null;
		
		while(true) {
			try {
				DatagramPacket dp = socket.receiveMessage();
				
				String receivedMessage = new String(dp.getData()).trim();
				System.out.println("Received Log : " + receivedMessage);
				socket.setIpPort(dp.getAddress(), dp.getPort());
				
				if(userName==null) { // 첫 접속 : userName 저장
					userName = receivedMessage;
					sendMessage = "--- Welcome! Please Enter the number between 100 and 1000 ---";
					System.out.println("--- [" + userName + "] is Entered ---");
					gameOperator.createGoal();	// 문제 생성
				}
				else {
					alertTimer.cancel();
					
					if(receivedMessage.equals("STOP")) {
						sendMessage = "STOP";
					}
					else if(receivedMessage.equals("EXIT")) {
						break;		// Client가 접속을 끊은 것을 확인하고 해제
					}
					else if(receivedMessage.equals("HINT")) {
						sendMessage = gameOperator.makeHintMessage();
					}
					else if(100 < Integer.parseInt(receivedMessage) && Integer.parseInt(receivedMessage) < 1000) {
						System.out.println("Received Number : " + receivedMessage);
						sendMessage = gameOperator.calcNumberBaseball(receivedMessage);
					}
					else {
						sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
					}
					
					if(warningCount > 0 && warningCount < 3) {
						sendMessage += (" [Warning" + warningCount + "]"); 
					}
					else if(warningCount >= 3) {
						sendMessage = "STOP";
					}
				}
				socket.sendMessage(sendMessage);
				
				alertTimer = new Timer();
				TimerTask alertTask = new TimerTask() {
					@Override
					public void run() {
						warningCount++;
					}
				};
				
				alertTimer.scheduleAtFixedRate(alertTask, 10000, 10000);
				
				if(sendMessage.equals("GOAL!")) break;
			} catch (IOException e) {
				break;
			} catch (NumberFormatException nfe) {
				sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
				socket.sendMessage(sendMessage);
			}
		}
		socket.closeUDP();
		System.exit(0);
	}
}