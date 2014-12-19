package util;

import java.net.DatagramPacket;

import network.Net;
import network.NetSettings;

public class SensorReceiveThread extends Thread {
	private static boolean print = false;
	
	public void run() {
		// get memory for saving packets
	    byte[] receiveData = new byte[NetSettings.getPacketSize()];
	    
		while(true) {
			DatagramPacket received = Net.receive(NetSettings.getPcPort());
			
			receiveData = received.getData();
			float[] sensorValues = PacketHandler.getSensordata(receiveData);
			
			SensorDataAccess.setUss_f(sensorValues[0]);
			SensorDataAccess.setUss_rf(sensorValues[1]);
			SensorDataAccess.setUss_rb(sensorValues[2]);
			
			if (print) {
				System.out.print("uss_f:  " + sensorValues[0] + "\t");
				System.out.print("uss_rf: " + sensorValues[1] + "\t");
				System.out.print("uss_rb: " + sensorValues[2] + "\r\n");
			}

		}
	}
	
	public static void setPrint(boolean bool) {
		print = bool;
	}
}
