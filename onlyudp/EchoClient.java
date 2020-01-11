package onlyudp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * UDP ����� Ȯ���ϱ� ���� EchoClient
 * @author SeongYun
 *
 */
public class EchoClient {
	// ��Ʈ ��ȣ, ���� IP, ���� �ִ� ũ�⸦ ���� ������ �ʱ�ȭ
	private static int CLIENTPORT = 2019;	// �۽� ���� ������ �� ����� ��Ʈ ��ȣ
	private static int SERVERPORT = 2020;	// ��Ŷ�� ���� �� ����� ������ ��Ʈ ��ȣ
	private static String SERVERIP = "127.0.0.1";	// ���� IP �ּ� ���� (localhost : �׽�Ʈ��)
	private static int MAX_LEN = 512;	// Buffer�� �ִ� ũ�� ����
	
	public static void main(String[] args) {
		// EchoServer ����
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

