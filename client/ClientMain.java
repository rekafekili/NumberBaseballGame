package client;

import java.io.IOException;
import java.util.Scanner;

/**
 * UDP ��� + ���ھ߱����� + ��Ÿ ����� ������ Client
 * @author SeongYun
 *
 */
public class ClientMain {
	public static void main(String[] args) {
		// Ű����κ��� �Է¹ޱ� ���� Scanner �ν��Ͻ�
		Scanner sc = new Scanner(System.in);
		String receivedMessage;	// ���� ���� �����͸� ������ ���ڿ� ����

		// UDP ��� ����, ���� ����
		ClientUDP client = new ClientUDP("127.0.0.1", 2019);

		// ù �۽��� UserName�� �����Ѵ�.
		System.out.print("Enter your name : ");
		String username = sc.next();
		try {
			client.sendMessage(username); // �޽��� ����
			while (true) {
				receivedMessage = client.receiveMessage().trim(); // ���� ���� �޽��� ����
				
				System.out.println("SERVER >> " + receivedMessage); // ���� ���� �޽��� ���
				
				if(receivedMessage.equals("GOAL!")){	// ���� ���� �޽����� GOAL�� ���
					break;	// ����
				}
				else if(receivedMessage.equals("STOP")) {	// ���� ���� �޽����� STOP�� ���
					client.sendMessage("EXIT");	// EXIT ����
					break;	// ����
				}
				else {
					// ����ڷκ��� �Է� ����
					System.out.print(username + " : ");
					String message = sc.next() + "";
					
					// �޽��� ����
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