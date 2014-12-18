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

public final class NetSettings {
	public static final String configFileOnComputer = "./src/network/net.properties";
	//public static final String configFileOnRobot = "./network/net.properties";
		// init content with default values, if reading from file wont work
	private static int BUFFER_SIZE	= 1024;
	private static String PC_IP				= "141.82.173.143";
	
	private static String EV1_IP	= "141.82.48.201";
	private static String EV2_IP	= "141.82.48.201";
	// currently the brick of EV1 is on "EV2", cause EV2 doesn't connect to the wlan anymore :(
	private static String EV3_IP	= "141.82.48.203";
	private static String EV0_IP	= "127.0.0.1";
	
	private static int PC_PORT		= 10000;
	private static int ROBOT_PORT	= 10003;
	
	private NetSettings(){
		// private constructor, so you cannot create an instance of it
	}
	
	// READ CONFIG FILE
	public static void readConfigFile() {
		
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(configFileOnComputer));
			properties.load(stream);
			stream.close();

			// SET VALUES

			// BUFFER
			String buffersize = properties.getProperty("buffersize");
			NetSettings.BUFFER_SIZE = Integer.parseInt(buffersize);
			
			// COMPUTER
			String pcip = properties.getProperty("pcip");
			NetSettings.PC_IP = pcip;
			
			String pcport = properties.getProperty("pcport");
			NetSettings.PC_PORT = Integer.parseInt(pcport);
						
			// ROBOTS
			NetSettings.EV1_IP = properties.getProperty("ev1ip");
			NetSettings.EV2_IP = properties.getProperty("ev2ip");
			NetSettings.EV3_IP = properties.getProperty("ev3ip");
			// EV0 - pseudo robot, running on localhost
			NetSettings.EV0_IP = properties.getProperty("ev0ip");
			
			String robotport = properties.getProperty("robotport");
			NetSettings.ROBOT_PORT = Integer.parseInt(robotport);
			
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't read '" + configFileOnComputer + "'");
			System.err
					.println("  Using default values instead! You can change them in the,\r\n"
						   + "  file network/NetSettings.. but that's not recommended");
		} catch (IOException e) {
			System.err.println("IOException while reading properties file '"
					+ configFileOnComputer + "'\r\n  Message: " + e.getMessage());
		}
	}

	// GETTER
	public static int getBufferSize() { return BUFFER_SIZE; }
	
	public static String getPcIp()		{ return PC_IP; }	
	public static int getPcPort()		{ return PC_PORT; }
	
	public static String getEv1Ip() { return EV1_IP; }
	public static String getEv2Ip() { return EV2_IP; }
	public static String getEv3Ip() { return EV3_IP; }
	public static String getEv0Ip() { return EV0_IP; }
	public static int getRobotPort()	{ return ROBOT_PORT; }
}