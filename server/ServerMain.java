package server;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		ServerUDP socket = new ServerUDP(3000);
		String userName = null;
		InetAddress clientIp = null;
		int clientPort = 0;
		String sendMessage = null;
		
		ServerGame gameOperator = new ServerGame();
		
		while(true) {
			try {
				DatagramPacket dp = socket.receiveMessage();
				String receivedMessage = new String(dp.getData()).trim();
				System.out.println("Received Log : " + receivedMessage);
				clientIp = dp.getAddress();
				clientPort = dp.getPort();
				
				if(userName==null) { // ù ���� : userName ����
					userName = receivedMessage;
					sendMessage = "--- Welcome! Please Enter the number between 100 and 1000 ---";
					System.out.println("--- [" + userName + "] is Entered ---");
					gameOperator.createGoal();	// ���� ����
				}
				else if(receivedMessage.equals("STOP")) {
					sendMessage = "STOP";
				}
				else if(receivedMessage.equals("EXIT")) {
					break;		// Client�� ������ ���� ���� Ȯ���ϰ� ����
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
	

}