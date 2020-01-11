package server;


import java.io.IOException;
import java.net.*;

public class ServerUDP {
	private DatagramSocket socket;
	private InetAddress clientIp;
	private int clientPort;
	
	/**
	 * ServerUDP �����ڷν�, DatagramSocket�� �����Ѵ�.
	 * @param port DatagramSocket ���ڰ�
	 */
	public ServerUDP(int port){
		try {
			socket = new DatagramSocket(port);
			System.out.println("--- Server Socket Created ---");
		} catch (Exception e) {
			System.out.println("!!! Server Socket Failed !!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * IP�� Port ��ȣ�� �����ϴ� �޼ҵ�
	 * @param ip IP�ּ�
	 * @param port Port��ȣ
	 */
	public void setIpPort(InetAddress ip, int port) {
		this.clientIp = ip;
		this.clientPort = port;
	}
	
	/**
	 * ���ڰ����� ���� ���ڿ��� ��Ŷ���� ����� �����ϴ� �޼ҵ�
	 * @param message ��Ŷ���� ���� ���ڿ�
	 * @throws IOException ����¿� ������ ���� �� �߻�
	 */
	public void sendMessage(String message) throws IOException{
		byte buffer[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, clientIp, clientPort);
		socket.send(dp);
	}
	
	/**
	 * ������ ��Ŷ�� ��ȯ�ϴ� �޼ҵ�
	 * @return ������ DatagramPacket
	 * @throws IOException
	 */
	public DatagramPacket receiveMessage() throws IOException {
		byte buffer[] = new byte[512];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		socket.receive(dp);
		
		return dp;
	}
	
	/**
	 * UDP ������ �����ϰ�, �α� �޽��� ���
	 */
	public void closeUDP() {
		socket.close();
		System.out.println("--- Server Socket Closed ---");
	}
}


