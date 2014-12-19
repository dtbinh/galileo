package main;

import pathfinding.RobotPath;
import util.ConfigFiles;
import mapping.Map;

public class ComputerMain {
	
	public static void main(String[] args) {
		ConfigFiles.read();
		//printRobotSettings();
		
		new util.SensorReceiveThread().start();
		
		RobotPath r = new RobotPath();
		r.run(new Map());
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
