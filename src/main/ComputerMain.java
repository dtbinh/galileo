package main;

import gui.GuiControl;
import pathfinding.Puffi;
import pathfinding.RobotPath;
import sensor.SensorReceiveThread;
import util.ConfigFiles;
import mapping.Map;

public class ComputerMain {
	private static Map map = new Map();
	
	public static void main(String[] args) throws InterruptedException {
//		ConfigFiles.read();		// don't use, since it doesn't work on the robot yet
								// change settings in network/NetSettings.java
		
		if (RunSettings.gui) {
			new GuiControl().start();
		}
		
		new SensorReceiveThread().start();
		
		if (RunSettings.map_2D) {
			new mapping.history.Map2D().start();
		} else {
			new mapping.history.MainMap3D().start();
		}
//		RobotPath r = new RobotPath();
		Puffi p = new Puffi(map);
		p.run();
	}
	
	public static Map getMap() {
		return ComputerMain.map;
	}
}
