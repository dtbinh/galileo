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
	private static int BUFFER_SIZE	= 256;
	private static String PCZIAD_IP			= "127.0.0.1";
	private static String PCROMAN_IP		= "127.0.0.1";
	private static String PCEUGEN_IP		= "127.0.0.1";
	private static String PCJASON_IP		= "127.0.0.1";
	private static String PCDANIEL_IP		= "127.0.0.1";
	private static String PCPATRICK_IP		= "192.168.2.107";
	private static String PCALEJANDRO_IP	= "127.0.0.1";
	
	private static String EV1_IP	= "141.82.48.201";
	private static String EV2_IP	= "141.82.48.202";
	private static String EV3_IP	= "141.82.48.203";
	private static String EV0_IP	= "127.0.0.1";
	
	private static int RECEIVE_PORT	= 10000;
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
			String buffersize = properties.getProperty("buffersize");
			NetworkSettings.BUFFER_SIZE = Integer.parseInt(buffersize);
			
			String pcziad = properties.getProperty("pcziad");
			NetworkSettings.PCZIAD_IP = pcziad;
			
			String pcroman = properties.getProperty("pcroman");
			NetworkSettings.PCROMAN_IP = pcroman;
			
			String pceugen = properties.getProperty("pceugen");
			NetworkSettings.PCEUGEN_IP = pceugen;
			
			String pcjason = properties.getProperty("pcjason");
			NetworkSettings.PCJASON_IP = pcjason;
			
			String pcdaniel = properties.getProperty("pcdaniel");
			NetworkSettings.PCDANIEL_IP = pcdaniel;
			
			String pcpatrick = properties.getProperty("pcpatrick");
			NetworkSettings.PCPATRICK_IP = pcpatrick;
			
			String pcalejandro = properties.getProperty("pcalejandro");
			NetworkSettings.PCALEJANDRO_IP = pcalejandro;
			
			String receiveport = properties.getProperty("receiveport");
			NetworkSettings.RECEIVE_PORT = Integer.parseInt(receiveport);
			
			
			// ROBOTS
			// EV1
			NetworkSettings.EV1_IP = properties.getProperty("ev1ip");
			// EV2
			NetworkSettings.EV2_IP = properties.getProperty("ev2ip");
			// EV3
			NetworkSettings.EV3_IP = properties.getProperty("ev3ip");
			// EV0 - debug robot			
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
	
	public static String getEv1Ip() { return EV1_IP; }
	public static String getEv2Ip() { return EV2_IP; }
	public static String getEv3Ip() { return EV3_IP; }
	public static String getEv0Ip() { return EV0_IP; }
	
	public static int getReceivePort()	{ return RECEIVE_PORT;	}
	public static int getRobotPort()	{ return ROBOT_PORT;	}

	public static String getPcziadIP()		{ return PCZIAD_IP;		}
	public static String getPCROMAN_IP()	{ return PCROMAN_IP;	}
	public static String getPCEUGEN_IP()	{ return PCEUGEN_IP;	}
	public static String getPCJASON_IP()	{ return PCJASON_IP;	}
	public static String getPCDANIEL_IP()	{ return PCDANIEL_IP;	}
	public static String getPCPATRICK_IP()	{ return PCPATRICK_IP;	}
	public static String getPCALEJANDRO_IP() { return PCALEJANDRO_IP; }
}