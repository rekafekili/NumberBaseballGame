package onlyudp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
	private static int CLIENTPORT = 2019;
	private static int SERVERPORT = 2020;
	private static String SERVERIP = "127.0.0.1";
	private static int MAX_LEN = 512;
	
	public static void main(String[] args) {
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

