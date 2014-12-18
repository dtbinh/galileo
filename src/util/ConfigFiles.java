package util;

import network.NetSettings;

public class ConfigFiles {
	//public static final String robotConfigFileName	= "./res/robot.properties";
	//public static final String netConfigFileName	= "./res/net.properties";
	
	public static void read() {
		NetSettings.readConfigFile();
		util.RobotSettings.readConfigFile();
	}

}
