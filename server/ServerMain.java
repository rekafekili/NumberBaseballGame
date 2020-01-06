package server;

import java.io.IOException;
import java.net.*;

public class ServerMain {
	private static int[] goal;
	private static int hintLimit = 1;
	
	public static void main(String[] args) throws IOException {
		ServerUDP socket = new ServerUDP(3000);
		String userName = null;
		goal = createGoal();
		InetAddress clientIp = null;
		int clientPort = 0;
		String sendMessage = null;
		while(true) {
			try {
				DatagramPacket dp = socket.receiveMessage();
				
				String receivedMessage = new String(dp.getData()).trim();
				System.out.println("Received Log : " + receivedMessage);
				clientIp = dp.getAddress();
				clientPort = dp.getPort();
				
				if(userName==null) { // 이미 접속한 적이 없는 경우 : userName 저장
					userName = receivedMessage;
					sendMessage = "--- Welcome! Please Enter the number between 100 and 1000 ---";
					System.out.println("--- [" + userName + "] is Entered ---");
				}
				else if(receivedMessage.equals("STOP")) {
					sendMessage = "STOP";
				}
				else if(receivedMessage.equals("EXIT")) {
					break;
				}
				else if(receivedMessage.equals("HINT")) {
					if(hintLimit >= 1) {
						sendMessage = makeHintMessage();
					}
					else {
						sendMessage = "Sorry, Hint is Only Once!";
					}	
				}
				else if(100 < Integer.parseInt(receivedMessage) && Integer.parseInt(receivedMessage) < 1000) {
					System.out.println("Received Number : " + receivedMessage);
					sendMessage = calcNumberBaseball(receivedMessage);
				}
				else {
					sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
				}
				
				socket.sendMessage(sendMessage, clientIp, clientPort);
				
				if(sendMessage.equals("GOAL!")) break;
			} catch (IOException e) {
				break;
			} catch (NumberFormatException nfe) {
				sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
				socket.sendMessage(sendMessage, clientIp, clientPort);
			}
		}
		
		socket.closeUDP();
		System.exit(0);
	}

	private static int[] createGoal() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] goal = new int[3];
		for(int i=0; i<3; i++) {
			while(true) {
				int randomIndex = (int)(Math.random()*10);
				if(arr[randomIndex] != -1) {
					goal[i] = arr[randomIndex];
					arr[randomIndex] = -1;
					break;
				}
			}
		}
		System.out.println("Goal : " + goal[0] + goal[1] + goal[2]);
		return goal;
	}

	private static String calcNumberBaseball(String inputNumber) {
		int[] input = new int[3];
		for(int i=0; i<3; i++) {
			input[i] = inputNumber.charAt(i) - '0';
		}
		
		int strike = 0;
		int ball = 0;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(goal[i]==input[j] && i==j) {
					strike++;
				}
				else if(goal[i]==input[j] && i!=j) {
					ball++;
				}
			}
		}
		
		if(strike == 0 && ball == 0) return "OUT!!";
		else if(strike == 3 && ball ==0) return "GOAL!";
		return strike + "Strike " + ball + "Ball";
	}
	
	private static String makeHintMessage() {
		int index = (int)(Math.random()*3);	// 0~2 중 랜덤 값
		String hintMessage = "";
		for(int i=0; i<3; i++) {
			if(i==index) {
				hintMessage += Integer.toString(goal[index]);
			}
			else {
				hintMessage += "*";
			}
		}
		hintLimit--;
		return hintMessage;
	}
}



