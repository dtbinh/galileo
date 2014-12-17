package util;

import network.NetworkSettings;

public class ConfigFiles {
	//public static final String robotConfigFileName	= "./res/robot.properties";
	//public static final String netConfigFileName	= "./res/net.properties";
	
	public static void read() {
		NetworkSettings.readConfigFile();
		util.RobotSettings.readConfigFile();
	}

}
