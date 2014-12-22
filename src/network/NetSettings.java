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
	public static final String configFilePath = "./res/net.properties";
	
	// init content with default values, if reading from file wont work
	// actually doesn't work on the robot, so set settings always here
	
	// COMPUTER SETTINGS
	private static String PC_IP		= "141.82.173.143";
	private static int PC_PORT		= 10000;
	
	// ROBOT SETTINGS
	private static String EV1_IP	= "141.82.48.201";
	private static String EV2_IP	= "141.82.48.201";
	// currently the brick of EV1 is on "EV2", cause EV2 doesn't connect to the wlan anymore :(
	private static String EV3_IP	= "141.82.48.203";
	private static String EV0_IP	= "127.0.0.1";
	// EV0 - pseudo robot, running on localhost
	private static int ROBOT_PORT	= 10003;
	
	// OTHER STUFF
	private static int PACKET_SIZE	= 64;
	private static int INFO_PORT	= 10002;
	// info port is used for sending information packets
	
	private NetSettings(){
		// private constructor, so you cannot create an instance of it
	}
	
	// READ CONFIG FILE
	public static void readConfigFile() {
		
		Properties properties = new Properties();
		try {
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(configFilePath));
			properties.load(stream);
			stream.close();

			// Set values
			
			// COMPUTER
			String pcip = properties.getProperty("pcip");
			NetSettings.PC_IP = pcip;
			
			String pcport = properties.getProperty("pcport");
			NetSettings.PC_PORT = Integer.parseInt(pcport);
						
			// ROBOTS
			NetSettings.EV1_IP = properties.getProperty("ev1ip");
			NetSettings.EV2_IP = properties.getProperty("ev2ip");
			NetSettings.EV3_IP = properties.getProperty("ev3ip");
			NetSettings.EV0_IP = properties.getProperty("ev0ip");
			
			String robotport = properties.getProperty("robotport");
			NetSettings.ROBOT_PORT = Integer.parseInt(robotport);
			
			// BUFFER & INFOPORT
			String packetsize = properties.getProperty("packetsize");
			NetSettings.PACKET_SIZE = Integer.parseInt(packetsize);
			
			String infoport = properties.getProperty("infoport");
			NetSettings.INFO_PORT = Integer.parseInt(infoport);
			
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't read '"+ configFilePath +"'");
			System.err.println("Using default values");
		} catch (IOException e) {
			System.err.println("IOException while reading properties file '"
					+ configFilePath + "'\r\n  Message: " + e.getMessage());
		}
	}

	// GETTER
	public static int getPacketSize()	{ return PACKET_SIZE; }
	
	public static String getPcIp()		{ return PC_IP;	 }
	public static String getEv1Ip()		{ return EV1_IP; }
	public static String getEv2Ip() 	{ return EV2_IP; }
	public static String getEv3Ip() 	{ return EV3_IP; }
	public static String getEv0Ip() 	{ return EV0_IP; }
	
	public static int getPcPort()		{ return PC_PORT;	 }
	public static int getRobotPort()	{ return ROBOT_PORT; }
	public static int getInfoPort()		{ return INFO_PORT;	 }
}