package main;

import util.SensorSendingThread;

public class RobotMain {

	public static void main(String[] args) {
		//ConfigFiles.read();	// doesn't work on the robot :(
								// so, better change the settings directly
								// in network/NetSettings.java
		
		new SensorSendingThread().start();
		
		ClientControl cc = new ClientControl();
		
		cc.run();
	}
	
	/* if you want to receive the data on the computer to
	 * you have to start sensorTest() in ComputerMain too! */

	
	private static String getNetConfigPathOnRobot() {
		String dir = System.getProperty("user.dir");
		String classname = RobotMain.class.getSimpleName();
		return dir + "/" + classname + ".jar" + "/" + "network/net.properties";
	}

}
