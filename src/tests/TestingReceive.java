package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import util.PacketHandler;
import network.NetSettings;

public class TestingReceive {
	public static void main(String[] args) throws IOException {
		boolean run = true;
		byte[] receiveData = new byte[NetSettings.getPacketSize()];
		DatagramSocket serverSocket = new DatagramSocket(
				NetSettings.getPcPort());
		
		while (run) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println("From: " + receivePacket.getAddress());
			
			receiveData = receivePacket.getData();
			
			System.out.println(PacketHandler.getContent(receiveData));
		}
		serverSocket.close();
	}
}
