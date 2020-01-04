package onlyudp;

import java.io.IOException;
import java.net.*;

public class EchoServer {
	private static int port = 2020;		// Server Port
	private static int MAX_LEN = 512;	// Maximum Length of Buffer
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(port);
			System.out.println("--- EchoServer Socket Created! ---");
			
			byte buffer[] = new byte[MAX_LEN];
			DatagramPacket clientPacket = new DatagramPacket(buffer, buffer.length);
			System.out.println("--- Waiting for Receiving ---");
			socket.receive(clientPacket);
			
			InetAddress clientIP = clientPacket.getAddress();
			int clientPort = clientPacket.getPort();
			String clientMessage = new String(clientPacket.getData()).trim();
			System.out.println(clientIP + ":" + port + " >> " + clientMessage);
			
			buffer = clientMessage.getBytes();
			clientPacket = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
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

