package onlyudp;

import java.io.IOException;
import java.net.*;

/**
 * UDP ����� Ȯ���ϱ� ���� EchoServer
 * @author SeongYun
 */
public class EchoServer {
	
	// ���� �� ��Ʈ ��ȣ�� ������ �ִ� ũ�� ����
	private static int port = 2020;		// Server Port
	private static int MAX_LEN = 512;	// Maximum Length of Buffer
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// DatagramSocket �ʱ�ȭ�� �α� �޽���
			socket = new DatagramSocket(port);
			System.out.println("--- EchoServer Socket Created! ---");
			
			// ���۷� ����ϱ� ���� ����Ʈ �迭 ����
			byte buffer[] = new byte[MAX_LEN];
			
			// ��� ������ ����Ʈ �迭�� ���ڰ����� �־� DatagramPacket ����, UDP ��Ŷ�� �������� �� ���� �� ��Ŷ
			DatagramPacket clientPacket = new DatagramPacket(buffer, buffer.length);
			System.out.println("--- Waiting for Receiving ---");
			
			// UDP ��Ŷ�� ���ŵ� ������ ���, ���� �Ǹ� ���� �ڵ�� �Ѿ
			socket.receive(clientPacket);
			
			// ���� ���� ��Ŷ�� ��� �ִ� �۽����� IP, Port��ȣ, Data �� �����Ͽ� ����
			InetAddress clientIP = clientPacket.getAddress();
			int clientPort = clientPacket.getPort();
			String clientMessage = new String(clientPacket.getData()).trim();	// trim() : String�� ó���� ���� �ִ� ������ ����
			System.out.println(clientIP + ":" + port + " >> " + clientMessage);
			
			buffer = clientMessage.getBytes(); // getBytes() : String �� Byte Array�� ��ȯ
			clientPacket = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
			
			// ��Ŷ ����
			socket.send(clientPacket);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("!!! EchoServer Socket Failed! !!!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! DatagramPacket IOException !!!");
		}
		
		socket.close();
		System.out.println("--- EchoServer Socket Closed! ---");
		System.exit(0);
	}
}