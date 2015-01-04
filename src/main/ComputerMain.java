package main;

import gui.GuiControl;
import pathfinding.RobotPath;
import sensor.SensorReceiveThread;
import util.ConfigFiles;
import mapping.Map;

public class ComputerMain {
	private static Map map = new Map();
	
	public static void main(String[] args) {
		ConfigFiles.read();
		
		if (RunSettings.gui) {
			new GuiControl().start();
		}
		
		new SensorReceiveThread().start();
		
		if (RunSettings.map_2D) {
			new mapping.history.Map2D().start();
		} else {
			new mapping.history.Map3D().start();
		}
		
		RobotPath r = new RobotPath();
		r.run(map);
	}
	
	public static Map getMap() {
		return ComputerMain.map;
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
