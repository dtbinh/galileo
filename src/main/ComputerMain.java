package main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import util.ConfigFiles;
import network.Net;
import network.NetworkSettings;

public class ComputerMain {
	private static String EV0IP	 = NetworkSettings.getEv0Ip();
	private static String EV2IP	 = NetworkSettings.getEv2Ip();
	private static int PCPORT	 = NetworkSettings.getPcPort();
	private static int ROBOTPORT = NetworkSettings.getRobotPort();
	
	
	public static void main(String[] args) throws IOException{
		ConfigFiles.read();
		//sensorTest();
		//printRobotSettings();
		Net.sendRobotCmd(EV2IP, ROBOTPORT, 105);
	}
	
	/* Note, that you'll need to start the sensorTest on the Robot too ;) */
	private static void sensorTest() throws IOException {
		// get memory for saving packets
		byte[] bytes = new byte[4];
	    byte[] receiveData = new byte[NetworkSettings.getBufferSize()];
	    
		while(true) {
			receiveData = Net.receive(PCPORT);
			
			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i];
			Float uss_f = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i+4];
			Float uss_rf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();

			for (int i = 0;i<4;i++)
				bytes[i]=receiveData[i+8];
			Float uss_rb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
			
			System.out.print("uss_f:  " + uss_f  + "\t");
			System.out.print("uss_rf: " + uss_rf + "\t");
			System.out.print("uss_rb: " + uss_rb + "\r\n");
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
