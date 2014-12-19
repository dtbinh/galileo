package util;

import network.NetSettings;

public class ConfigFiles {
	
	public static void read() {
		NetSettings.readConfigFile();
		util.RobotSettings.readConfigFile();
	}

}
