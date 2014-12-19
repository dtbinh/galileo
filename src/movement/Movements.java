package movement;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class Movements{
	private SensorThread sensorThread;
	//private ClientThread clThread;
	private RegulatedMotor leftMotor = Motor.B;
	private RegulatedMotor rightMotor = Motor.C;
	private Tachometer tacho = new Tachometer(leftMotor, rightMotor);
	
	public Movements() {
//		clThread = new ClientThread();
//		clThread.run();
	}
	
	public void rotate(int angle) {
		
		this.leftMotor.rotate(angle, true);
		this.rightMotor.rotate(angle, true);
	
	}
	
	public void driveForward(int distance) {//in cm
		//one full rotation (360 degrees) is about 18 centimeters,
		//thats how the rotating angle is computed
		int rotateAngle=(distance*360)/18;
		this.leftMotor.rotate(rotateAngle, true);
		this.rightMotor.rotate(rotateAngle, true);

	}
	// distance in cm
	public void driveBackward(int distance){
		//one full rotation (360 degrees) is about 18 centimeters,
		//thats how the rotating angle is computed
		int rotateAngle=(distance*360)/18;
		this.leftMotor.rotate(-rotateAngle, true);
		this.rightMotor.rotate(-rotateAngle, true);
		
	}
	
	public void turnLeft(int degrees) {
		for(int i=0; i< degrees*2; i=i+3){
				//movement
				leftMotor.rotate(-3, true);
				rightMotor.rotate(3);
		}
	}
	
	public void turnRight(int degrees) {
		for(int i=0; i< degrees*2; i=i+3){

				rightMotor.rotate(-3, true);
				leftMotor.rotate(3);
		}
	}
	
}
