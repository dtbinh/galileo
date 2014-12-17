package main;

import java.io.IOException;

import util.ConfigFiles;
import network.Net;
import network.NetworkSettings;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class RobotMain {
	static String pcIP = NetworkSettings.getPcIP();
	static int pcPORT  = NetworkSettings.getPcPort();
	static int portEV3 = NetworkSettings.getPcPort();

	public static void main(String[] args) {
		ConfigFiles.read();
		//sensorTest();
		
		ControlClient cc = new ControlClient();
		
		try {
			cc.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* if you want to receive the data on the computer to
	 * you have to start sensorTest() in ComputerMain too! */
	private static void sensorTest() {
		/* Steps to initialize the sensors */
		Brick brick = BrickFinder.getDefault();
		//Port s1 = brick.getPort("S1");
		Port s2 = brick.getPort("S2");
		Port s3 = brick.getPort("S3");
		Port s4 = brick.getPort("S4");

		// Ultrasonic Sensors
		EV3UltrasonicSensor ev3_ultra_f = new EV3UltrasonicSensor(s3);
		SampleProvider sample_ev3_ultra_f = ev3_ultra_f.getMode("Distance");
		
		EV3UltrasonicSensor ev3_ultra_rf = new EV3UltrasonicSensor(s4);
		SampleProvider sample_ev3_ultra_rf = ev3_ultra_rf.getMode("Distance");
		
		EV3UltrasonicSensor ev3_ultra_rb = new EV3UltrasonicSensor(s2);
		SampleProvider sample_ev3_ultra_rb = ev3_ultra_rb.getMode("Distance");

		float ultra_sample_rf[] = new float[1];
		float ultra_sample_rb[] = new float[1];
		float ultra_sample_f[] = new float[1];
		
		// save storage for sending data
		byte[] sendData = new byte[1024];

		Key escape = brick.getKey("Escape");

		while (!escape.isDown()) {
			// Display ultra f
			LCD.clear(1);
			sample_ev3_ultra_f.fetchSample(ultra_sample_f, 0);
			LCD.drawString("uss_f:  " + ultra_sample_f[0], 0, 1);
			
			// Display ultra rf
			LCD.clear(2);
			sample_ev3_ultra_rf.fetchSample(ultra_sample_rf, 0);
			LCD.drawString("uss_rf: " + ultra_sample_rf[0], 0, 2);
			
			// Display ultra rb
			LCD.clear(3);
			sample_ev3_ultra_rb.fetchSample(ultra_sample_rb, 0);
			LCD.drawString("uss_rb: " + ultra_sample_rb[0], 0, 3);

			Delay.msDelay(500);
			
			int ultra_sample_intbits_rf = Float.floatToIntBits(ultra_sample_rf[0]);
			int ultra_sample_intbits_rb = Float.floatToIntBits(ultra_sample_rb[0]);
			int ultra_sample_intbits_f = Float.floatToIntBits(ultra_sample_f[0]);

			for (int i = 0; i < 4; i++)
				sendData[i] = (byte) ((ultra_sample_intbits_f >> ((7 - i) * 8)) & 0xff);
			for (int i = 0; i < 4; i++)
				sendData[i + 4] = (byte) ((ultra_sample_intbits_rf >> ((7 - i) * 8)) & 0xff);
			for (int i = 0; i < 4; i++)
				sendData[i + 8] = (byte) ((ultra_sample_intbits_rb >> ((7 - i) * 8)) & 0xff);
			
			// send packet to pc
			Net.send(pcIP, pcPORT, sendData);
		}

		// Close sensors
		ev3_ultra_rf.close();
		ev3_ultra_rb.close();
		ev3_ultra_f.close();
		
		// clear screen
		LCD.clear();
	}

}
