package client;

import java.io.IOException;
import java.net.*;

public class ClientUDP {
	private InetAddress serverIp;	// ��� ���� IP
	private DatagramSocket socket;
	private static int SERVERPORT = 3000;
	
	/**
	 * ClientUDP ������ : serverIp�� �����ϰ� ���� ����
	 * @param ip ���� IP
	 * @param port Ŭ���̾�Ʈ�� ���� �� Port
	 */
	public ClientUDP(String ip, int port){
		try {
			serverIp = InetAddress.getByName(ip);
			socket = new DatagramSocket(port);
			System.out.println("--- Client Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Client Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * �޽����� ��Ŷȭ�Ͽ� �����ϴ� �޼ҵ�
	 * @param message	������ �޽���
	 * @throws IOException ��Ŷ ���� �� �߻��� �� �ִ� ����
	 */
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, serverIp, SERVERPORT);
		socket.send(dp);
	}
	
	/**
	 * ��Ŷ�� �����Ͽ� �� �ȿ� �޽����� ��ȯ�ϴ� �޼ҵ�
	 * @return	��Ŷ �������� ���ڿ��� ��ȯ
	 * @throws IOException
	 */
	public String receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return new String(dp.getData());
	}
	
	/**
	 * UDP ��� ���� �޼ҵ�
	 */
	public void closeUDP() {
		socket.close();
		System.out.println("--- Client Socket Closed ---");
	}
}