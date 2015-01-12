package sensor;

import util.PacketHandler;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import network.Net;
import network.NetSettings;

public class SensorSendingThread extends Thread {
	
	public void run() {
		/* Steps to initialize the sensors */
		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1");
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
		
		EV3UltrasonicSensor ev3_ultra_lb = new EV3UltrasonicSensor(s1);
		SampleProvider sample_ev3_ultra_lb = ev3_ultra_lb.getMode("Distance");
		
		float ultra_sample_f[] = new float[1];
		float ultra_sample_rf[] = new float[1];
		float ultra_sample_rb[] = new float[1];
		float ultra_sample_lb[] = new float[1];
		
		// save storage for sending data
		byte[] sendData = new byte[NetSettings.getPacketSize()];

		Key escape = brick.getKey("Escape");
		
		LCD.clear();
		
		// TODO: change to Thread.currentThread().isAlive()
		while (!escape.isDown()) {
			sample_ev3_ultra_f.fetchSample(ultra_sample_f, 0);
			sample_ev3_ultra_rf.fetchSample(ultra_sample_rf, 0);
			sample_ev3_ultra_rb.fetchSample(ultra_sample_rb, 0);
			sample_ev3_ultra_lb.fetchSample(ultra_sample_lb, 0);
			
			sendData = PacketHandler.makeSensorPacket(ultra_sample_f[0], ultra_sample_rf[0], ultra_sample_rb[0],ultra_sample_lb[0]);
			
			// send packet to pc
			Net.send(NetSettings.getPcIp(), NetSettings.getPcPort(), sendData);
			
			//if (RunSettings.debugSensor) {
				LCD.clear(1);
				LCD.drawString("uss_f:  " + ultra_sample_f[0], 0, 1);
				
				LCD.clear(2);
				LCD.drawString("uss_rf: " + ultra_sample_rf[0], 0, 2);
				
				LCD.clear(3);
				LCD.drawString("uss_rb: " + ultra_sample_rb[0], 0, 3);
				
				LCD.clear(3);
				LCD.drawString("uss_lb: " + ultra_sample_lb[0], 0, 4);
			//}
			Delay.msDelay(200);		// 5 packets per second
		}

		// Close sensors
		ev3_ultra_lb.close();
		ev3_ultra_rf.close();
		ev3_ultra_rb.close();
		ev3_ultra_f.close();
		
		// clear screen
		LCD.clear();
	}

}
