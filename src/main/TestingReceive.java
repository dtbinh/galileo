package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import network.NetSettings;

public class TestingReceive {
	public static void main(String[] args) throws IOException {
		// CSVFile.write("./res/robotdata.csv", );
		
		boolean run = true;
		byte[] receiveData = new byte[NetSettings.getBufferSize()];
		DatagramSocket serverSocket = new DatagramSocket(
				NetSettings.getPcPort());
		
		while (run) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			serverSocket.receive(receivePacket);
			System.out.println(receivePacket.getAddress());
			System.out.println(receivePacket.getPort());
			
			receiveData = receivePacket.getData();
			for(int i=0; i<receivePacket.getLength(); ++i) {
				System.out.print(receiveData[i]);
			}
			
			
		}
		serverSocket.close();
	}
}
