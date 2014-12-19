package tests;

import java.io.IOException;
import java.net.DatagramPacket;

import util.PacketHandler;
import network.Net;
import network.NetSettings;

public class TestingReceive {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Listening on port: " + NetSettings.getPcPort());
		
		if ( TestSettings.networkTestInfoPacket ) {
			while( true ) {
				System.out.println(Net.receiveInfo());
			}			
		} else {
			while ( true ) {
				DatagramPacket receivePacket = Net.receive(NetSettings.getPcPort());
				System.out.println("From: " + receivePacket.getAddress());
				System.out.println("  getContent(): " + PacketHandler.getContent(receivePacket.getData()));
				System.out.println("  getRoboCmd(): " + PacketHandler.getRobotCommand(receivePacket.getData()));
				System.out.print("  getSenspac(): ");
				for(int i=0 ; i<3; ++i) {
					System.out.print(PacketHandler.getSensordata(receivePacket.getData())[0] + " ");
				}
				System.out.println();
			}
		}
	}
}
