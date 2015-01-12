package sensor;

import java.net.DatagramPacket;

import util.CSVFile;
import util.PacketHandler;
import main.RunSettings;
import network.Net;
import network.NetSettings;

public class SensorReceiveThread extends Thread {
	
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
			SensorDataAccess.setUss_lb(sensorValues[3]);
			
			CSVFile.write(CSVFile.robotDataFilename, "EV2", receiveData);
			
			if (RunSettings.debugSensor) {
				System.out.println();
				System.out.print("uss_f:  " + sensorValues[0] + "\n");
				System.out.print("uss_rf: " + sensorValues[1] + "\n");
				System.out.print("uss_rb: " + sensorValues[2] + "\n");
				System.out.print("uss_lb: " + sensorValues[3] + "\r\n");
			}

		}
	}
}
