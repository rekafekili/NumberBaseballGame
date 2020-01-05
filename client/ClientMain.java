package client;

import java.io.IOException;
import java.util.Scanner;

/*
 *  UDP ����� �̿��� ���ھ߱�����
 *  Ŭ���̾�Ʈ ���� : UDP �ۼ����� ����ϴ� ��ü�� ��� �ν��Ͻ��Ͽ� ������ ������ ����
 *  
 */
public class ClientMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String receivedMessage;

		ClientUDP client = new ClientUDP("127.0.0.1", 2019);

		System.out.print("Enter your name : ");
		String username = sc.next();

		try {
			client.sendMessage("!" + username);
			while (true) {
				receivedMessage = client.receiveMessage().trim();
				System.out.println("SERVER >> " + receivedMessage);

				System.out.print(username + " : ");
				String message = sc.next() + "";
				client.sendMessage(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		client.closeUDP();
		System.exit(0);
	}
}

//if(receivedMessage.equals("STOP")) {
//	client.sendMessage("EXIT");
//	break;
//}