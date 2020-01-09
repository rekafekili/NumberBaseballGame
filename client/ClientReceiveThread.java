package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientReceiveThread extends Thread {
	private DatagramSocket receiveSocket;
	
	public ClientReceiveThread(int port) {
		try {
			receiveSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("!!!!! Receive Thread Failed !!!!!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			byte[] threadBuffer = new byte[512];
			DatagramPacket dp = new DatagramPacket(threadBuffer, threadBuffer.length);
			
			receiveSocket.receive(dp);
			System.out.println("SERVER >> " + new String(dp.getData()).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
