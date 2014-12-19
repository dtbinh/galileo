package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import util.ConfigFiles;
import util.PacketExtractor;
import network.Net;
import network.NetSettings;

public class ComputerMain {
	private static String EV0IP	 = NetSettings.getEv0Ip();
	private static String EV2IP	 = NetSettings.getEv2Ip();
	private static int PCPORT	 = NetSettings.getPcPort();
	private static int ROBOTPORT = NetSettings.getRobotPort();
	
	
	public static void main(String[] args) {
		ConfigFiles.read();
		//sensorTest();
		//printRobotSettings();
		
		Net.sendRobotCmd(EV2IP, ROBOTPORT, 127);
	}
	
	/* Note, that you'll need to start the sensorTest on the Robot too ;) */
	private static void sensorTest() throws IOException {
		// get memory for saving packets
		byte[] bytes = new byte[4];
	    byte[] receiveData = new byte[NetSettings.getPacketSize()];
	    
		while(true) {
			DatagramPacket received = Net.receive(PCPORT);
			receiveData = received.getData();
			
//			float[] sensorValues = PacketExtractor.getSensordata(receiveData);
			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i];
			Float uss_f = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i+4];
			Float uss_rf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();

			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i+8];
			Float uss_rb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
			
			System.out.print("uss_f:  " + uss_f + "\t");
			System.out.print("uss_rf: " + uss_rf + "\t");
			System.out.print("uss_rb: " + uss_rb + "\r\n");
//			System.out.print("uss_f:  " + sensorValues[0] + "\t");
//			System.out.print("uss_rf: " + sensorValues[1] + "\t");
//			System.out.print("uss_rb: " + sensorValues[2] + "\r\n");
		}
	}
	
	private static void printRobotSettings() {
		System.out.println("MotorA: \t" + util.RobotSettings.getMotorA());
		System.out.println("MotorB: \t" + util.RobotSettings.getMotorB());
		System.out.println("MotorC: \t" + util.RobotSettings.getMotorC());
		System.out.println("MotorD: \t" + util.RobotSettings.getMotorD());
		System.out.println("Sensor1:\t" + util.RobotSettings.getS1());
		System.out.println("Sensor2:\t" + util.RobotSettings.getS2());
		System.out.println("Sensor3:\t" + util.RobotSettings.getS3());
		System.out.println("Sensor4:\t" + util.RobotSettings.getS4());
	}

}
