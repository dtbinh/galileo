package movement;

import util.SensorThread;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class Movement{
	private SensorThread sensorThread;
	//private ClientThread clThread;
	private RegulatedMotor leftMotor = Motor.B;
	private RegulatedMotor rightMotor = Motor.C;
	private Tachometer tacho = new Tachometer(leftMotor, rightMotor);
	
	public Movement() {
//		clThread = new ClientThread();
//		clThread.run();
	}
	
	//different bytes of control ( first byte of the array):
	//0 -> while driving forward information about lateral sensors(ultrasonic), and front sensor (infrared) 
	//1 -> while driving backward information about lateral sensors(ultrasonic), and front sensor (infrared)
	//2 -> while turning left information about lateral sensors, and front sensor (infrared)
	//3 -> while turning right information about lateral sensors, and front sensor (infrared)
	//4 -> distance at the end of the function end of function anyway 
	//5 -> touch sensor (DANGEROUS!!!!) forced end of the function.
	
	public void driveForward(int distance) {//in cm
		// moves 36 degree = 0.01 meters aprox.
		
		//TODO check that the thread is working right and the pack are right
		//TODO for the Array of byte concatenation used commons-lang3-3.3.2.jar
		// send pack type Byte[]
		// while control if is possible continue forward
		// send info about of the sensors each 0.5cms
		tacho.resetTacho();
		
		for(int i=0; i< distance*2; i++){
			// Do not touch and not give order to stop 
			//if(!sensorThread.isTouched() ){//&& clThread.getReceive() != 0){
				// movement
				this.leftMotor.rotate(18, true);
				this.rightMotor.rotate(18, true);
				
			// Is touching	
//			}else if (sensorThread.isTouched()){
//				// pack 5
////				byte[] pack5 = {5};
////				cl.sendArray(pack5);
//				break;
				
//			}
		}
	}
	
	public void driveBackward(int distance){
		// moves 36 degree = 0.01 meters aprox
		
		// send pack type Byte[]
		// while control if is possible continue forward
		// send info about of the sensors each 0.5cms
		tacho.resetTacho();
		
		for(int i=0; i< distance*2; i++){
			//Do not touch and not give order to stop 
			//if(!sensorThread.isTouched() ){//&& clThread.getReceive() != 0){
	
				//movement
				this.leftMotor.rotate(-18, true);
				this.rightMotor.rotate(-18, true);
				
			//Is touching	
//			}else if (sensorThread.isTouched()){
//				//pack 5
////				byte[] pack5 = {5};
////				cl.sendArray(pack5);
//				break;
//				
//			}
		}
	}
	
	public void turnLeft(int degrees) {
		tacho.resetTacho();
		
		for(int i=0; i< degrees*2; i=i+3){
			//Do not touch and not give order to stop 
			//if(!sensorThread.isTouched() ) { //&& clThread.getReceive() != 0){
				
				//movement
				leftMotor.rotate(-3, true);
				rightMotor.rotate(3);
				
			//Is touching	
//			}else if (sensorThread.isTouched()){
//				//pack 5
////				byte[] pack5 = {5};
////				cl.sendArray(pack5);
//				break;
//				
//			}
		}
	}
	
	public void turnRight(int degrees) {
		
		for(int i=0; i< degrees*2; i=i+3){
			//Do not touch and not give order to stop 
			//if(!sensorThread.isTouched() ) { //&& clThread.getReceive() != 0){
				
				//movement
				rightMotor.rotate(-3, true);
				leftMotor.rotate(3);
				
			//Is touching	
//			}else if (sensorThread.isTouched()){
//				//pack 5
////				byte[] pack5 = {5};
////				cl.sendArray(pack5);
//				break;
//				
//			}
		}
	}

	
	
	public String ParallelWall() {
		String msg= null;
		float error =(float) 0.02;
		tacho.resetTacho();
		
		msg = sensorThread.getSonarDistance1() + "*" + sensorThread.getSonarDistance2();
		
		if(sensorThread.getSonarDistance1() < sensorThread.getSonarDistance2()){
			do{
				rightMotor.rotate(5, true);
				leftMotor.rotate(-5);
			}
			while(sensorThread.getSonarDistance2()-sensorThread.getSonarDistance1() > error);
		}else if(sensorThread.getSonarDistance2() < sensorThread.getSonarDistance1()){
			do{
				leftMotor.rotate(5, true);
				rightMotor.rotate(-5);
			}
			while(sensorThread.getSonarDistance2()-sensorThread.getSonarDistance1() > error);
		}
		
		msg = msg + "/" + sensorThread.getSonarDistance1() + "*" + sensorThread.getSonarDistance2();
		msg = msg + "?" + sensorThread.getIRDistance();
		msg = msg + "#" + tacho.getTachoMedia();
		System.out.println(msg);
	
		return msg;
	}
	
	
	
	
}
