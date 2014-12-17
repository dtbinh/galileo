/*This class holds the Network Settings
 *  
 *DESCRIPTION:
 *  It reads the config file located at 'propertiesFile',
 *  writes them into its Attributes.
 *  Other classes can acces the values through Getter methods
 * 
 */

package network;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class NetworkSettings {
	public static final String propertiesFile = "./res/net.properties";
		// init content with default values, if reading from file wont work
	private static int BUFFER_SIZE	= 1024;
	private static String PC_IP				= "141.82.170.62";
	
	private static String EV1_IP	= "141.82.48.201";
	private static String EV2_IP	= "141.82.48.201";
	private static String EV3_IP	= "141.82.48.203";
	private static String EV0_IP	= "127.0.0.1";
	
	private static int PC_PORT		= 10000;
	private static int ROBOT_PORT	= 10003;

	private NetworkSettings() {
		readConfigFile();
	}
	
	// READ CONFIG FILE
	public static void readConfigFile() {
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(propertiesFile));
			properties.load(stream);
			stream.close();

			// SET VALUES

			// BUFFER
			String buffersize = properties.getProperty("buffersize");
			NetworkSettings.BUFFER_SIZE = Integer.parseInt(buffersize);
			
			// COMPUTER
			String pcip = properties.getProperty("pcip");
			NetworkSettings.PC_IP = pcip;
			
			String pcport = properties.getProperty("pcport");
			NetworkSettings.PC_PORT = Integer.parseInt(pcport);
						
			// ROBOTS
			NetworkSettings.EV1_IP = properties.getProperty("ev1ip");
			NetworkSettings.EV2_IP = properties.getProperty("ev2ip");
			NetworkSettings.EV3_IP = properties.getProperty("ev3ip");
			// EV0 - pseudo robot, running on localhost
			NetworkSettings.EV0_IP = properties.getProperty("ev0ip");
			
			String robotport = properties.getProperty("robotport");
			NetworkSettings.ROBOT_PORT = Integer.parseInt(robotport);
			
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't read '" + propertiesFile + "'");
			System.err
					.println("  Using default values instead! You can change them in the,\r\n"
						   + "  file network/NetworkSettings.. but that's not recommended");
		} catch (IOException e) {
			System.err.println("IOException while reading properties file '"
					+ propertiesFile + "'\r\n  Message: " + e.getMessage());
		}
	}

	// GETTER
	public static int getBufferSize() { return BUFFER_SIZE; }
	
	public static String getPcIP()		{ return PC_IP; }	
	public static int getPcPort()		{ return PC_PORT; }
	
	public static String getEv1Ip() { return EV1_IP; }
	public static String getEv2Ip() { return EV2_IP; }
	public static String getEv3Ip() { return EV3_IP; }
	public static String getEv0Ip() { return EV0_IP; }
	public static int getRobotPort()	{ return ROBOT_PORT; }
}