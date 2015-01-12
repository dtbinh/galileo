package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RobotSettings {
	public static final String configFilePath = "./res/robot.properties";
	
	private static String motorA = "nothing";
	private static String motorB = "wheel_left";
	private static String motorC = "wheel_right";
	private static String motorD = "nothing";
	
	private static String S1 = "uss_lb";
	private static String S2 = "uss_rb";
	private static String S3 = "uss_f";
	private static String S4 = "uss_rf";
	
	
	public static void readConfigFile() {
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(configFilePath));
			properties.load(stream);
			stream.close();

			// SET VALUES
			// MOTORS
			RobotSettings.motorA = properties.getProperty("motorA");
			RobotSettings.motorB = properties.getProperty("motorB");
			RobotSettings.motorC = properties.getProperty("motorC");
			RobotSettings.motorD = properties.getProperty("motorD");
			
			// SENSORS
			
			RobotSettings.S1 = properties.getProperty("sensor1");
			RobotSettings.S2 = properties.getProperty("sensor2");
			RobotSettings.S3 = properties.getProperty("sensor3");
			RobotSettings.S4 = properties.getProperty("sensor4");
			
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't read '"+ configFilePath +"'");
			System.err.println("Using default values");
		} catch (IOException e) {
			System.err.println("IOException while reading properties file '"
					+ configFilePath + "'\r\n  Message: " + e.getMessage());
		}
	}
	
	// GETTER
	public static String getMotorA() { return motorA; }
	public static String getMotorB() { return motorB; }
	public static String getMotorC() { return motorC; }
	public static String getMotorD() { return motorD; }
	
	public static String getS1() { return S1; }
	public static String getS2() { return S2; }
	public static String getS3() { return S3; }
	public static String getS4() { return S4; }
	
}
