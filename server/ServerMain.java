package server;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * UDP ��� + ���ھ߱����� + ��Ÿ ����� ������ Server
 * @author SeongYun
 *
 */
public class ServerMain {
	// Warning Ƚ���� ���������� ����
	private static int warningCount = 0;
	public static void main(String[] args) throws IOException {
		ServerUDP socket = new ServerUDP(3000);	// ServerUDP ����
		String userName = null;	// ���� �̸��� ���� �� ����
		String sendMessage = null;	// Client���� ���� �޽���
		
		ServerGame gameOperator = new ServerGame();	// ���ھ߱����� ����� ���õ� ��ü �ν��Ͻ�

		Timer alertTimer = null;	// ��� �ð� Timer
		
		while(true) {
			try {
				// ���� ���, ���� ���� ��� dp �� ����
				DatagramPacket dp = socket.receiveMessage();
				
				// ������ ��Ŷ�� �����Ϳ� ip, port �� �и��Ͽ� �����Ѵ�.
				String receivedMessage = new String(dp.getData()).trim();
				System.out.println("Received Log : " + receivedMessage);
				socket.setIpPort(dp.getAddress(), dp.getPort());
				
				// userName�� �ʱ�ȭ ���� ���� �Ȱ��ٸ� ó������ ���ŵ� �����̹Ƿ� ù �޽����� userName���� ����
				if(userName==null) { 
					userName = receivedMessage;
					sendMessage = "--- Welcome! Please Enter the number between 100 and 1000 ---";	// ȯ�� ����
					System.out.println("--- [" + userName + "] is Entered ---"); // �α� �޽���
					gameOperator.createGoal();	// ���� ����
				}
				else {
					alertTimer.cancel();	// 10�� ������ WarningCount �� 1�� �����ϴ� Timer ����
					
					if(receivedMessage.equals("STOP")) {	// STOP ��ɾ ���ŵ��� ���
						sendMessage = "STOP";	// STOP �� �����ϰ� Client�κ��� EXIT�� ���ŵǸ� ����
					}
					else if(receivedMessage.equals("EXIT")) {
						break;		// Client�� ������ ���� ���� Ȯ���ϰ� ����
					}
					else if(receivedMessage.equals("HINT")) {	// HINT ��ɾ ���ŵ��� ���
						sendMessage = gameOperator.makeHintMessage();
					}
					else if(100 < Integer.parseInt(receivedMessage) && Integer.parseInt(receivedMessage) < 1000) {	// ������ ���� ���� ���
						System.out.println("Received Number : " + receivedMessage);
						sendMessage = gameOperator.calcNumberBaseball(receivedMessage);
					}
					else {	// �� �� �޽����� ���ŵ� ��� �ȳ� �޽���
						sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
					}
					
					// Message�� �۽��ϱ� ���� WarningCount�� Ȯ��
					if(warningCount > 0 && warningCount < 3) {	// Warning Count�� 0 �ʰ� 3 �̸��� ���
						sendMessage += (" [Warning" + warningCount + "]"); // Client���� �����ϴ� �޽��� ���� WarningCount�� �ٿ��� ���� 
					}
					else if(warningCount >= 3) {	// WarningCount�� 3�̻��� ��� STOP ����
						sendMessage = "STOP";
					}
				}
				socket.sendMessage(sendMessage);	// �޽��� ����
				
				// alertTimer�� cancel �Ǹ� Timer�� TimerTask�� ����Ǳ� ������ ���� �ν��Ͻ� ���־�� �Ѵ�.
				alertTimer = new Timer();	// Timer �ʱ�ȭ
				TimerTask alertTask = new TimerTask() {
					@Override
					public void run() {
						warningCount++;
					}
				};
				
				alertTimer.scheduleAtFixedRate(alertTask, 10000, 10000);	// 10�ʰ� ������ WarningCount�� �����ϰ�, �� ���Ŀ� 10�ʰ� ���������� WarningCount�� �����Ѵ�.
				
				if(sendMessage.equals("GOAL!")) break;	// ������ �޽����� GOAL�� ��� ���� ����(���� ����)
			} catch (IOException e) {
				break;
			} catch (NumberFormatException nfe) {
				// Integer.parseInt() �޼ҵ忡�� ���ڿ��� Integer�� �ؼ��� �� ������ �ƴ� ���ڿ��̸� �߻��Ѵ�.
				sendMessage = "Please, Retry.\n Goal is between 100 and 1000";
				socket.sendMessage(sendMessage);
			}
		}
		socket.closeUDP();
		System.exit(0);
	}
}