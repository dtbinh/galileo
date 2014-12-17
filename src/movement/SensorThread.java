

package movement;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class SensorThread extends Thread {
	
	/*private EV3TouchSensor touch1 = new EV3TouchSensor(SensorPort.S4);
	private boolean touchActivated1 = false;*/
	//private EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
	public SensorMode sensorSampleProvider;
	//private boolean touchActivated = false;
	
	//EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S4);
	
	EV3UltrasonicSensor sonar1 = new EV3UltrasonicSensor(SensorPort.S3);
	float[] sonarDistance1 = new float[1];
	EV3UltrasonicSensor sonar2 = new EV3UltrasonicSensor(SensorPort.S2);
	float[] sonarDistance2 = new float[1];
	EV3UltrasonicSensor sonar3 = new EV3UltrasonicSensor(SensorPort.S4);
	float[] sonarDistance3 = new float[1];
	
	int distance = 0;
	
	
	public void run() {
		// create a new client with the UDP Implementation
		int control = 0;
		//sensorSampleProvider = touch.getTouchMode();
		
		while (true) {
			
			float[] sample = new float[sensorSampleProvider.sampleSize()];
			sensorSampleProvider.fetchSample(sample, 0);
			//touchActivated = sample[0] > 0;
			
			sonar1.getDistanceMode().fetchSample(sonarDistance1, 0);
			sonar2.getDistanceMode().fetchSample(sonarDistance2, 0);
			sonar3.getDistanceMode().fetchSample(sonarDistance3, 0);
			
//			sample = new float[irSensor.sampleSize()];
//			control = irSensor.getRemoteCommand(0);
//			irSensor.fetchSample(sample, 0);
			distance = (int)sample[0];
			
			
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public float getSonarDistance1() {
		return sonarDistance1[0];
	}
	
	public float getSonarDistance2() {
		return sonarDistance2[0];
	}
	
	public float getSonarDistance3() {
		return sonarDistance3[0];
	}
	
	public int getIRDistance(){
		return distance;
	}
	public void message(){
		System.out.println("Control: Distance1: " + sonarDistance1[0]+"  Distance2: " + sonarDistance2[0]+"  Distance3: " + sonarDistance3[0]);
	}
}
