package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import util.PacketHandler;

public class Net {
	
	private Net(){
		// private constructor, so you cannot create an instance of it
	}
	
	//-------------R A W   S E N D / R E C E I V E   M E T H O D S -----------
	
	/**
	 * Raw sending implementation
	 * <br>May you want to use the convenience functions?
	 * <br>
	 * <br>If not, remember to consider the packet convention.
	 * <br>First byte:
	 * <br>  0 -> sensordata
	 * <br>  1 -> robot command
	 * <br>  2 -> ACKnowledgement
	 * @see doc/packet_convention.txt
	 */
	public static void send(String IP, int PORT, byte[] sendData) {
		// check if data part is too big
		if (sendData.length > NetSettings.getPacketSize()) {
			int lost = sendData.length - NetSettings.getPacketSize();
			System.err.println("Lost " + lost + " bytes, cause data exceeds "
					+ "the maximum packetsize of " + NetSettings.getPacketSize());
		}

		try {
			// Prepare network
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(IP);

			// create and send packet
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, PORT);
			clientSocket.send(sendPacket);

			clientSocket.close();
		} catch (SocketException e) {
			System.err.println("Couldn't initialize DatagramSocket. "
					+ e.getMessage());
		} catch (UnknownHostException e) {
			System.err.println("Couldn't send packet to " + IP + ":" + PORT
					+ " command: " + sendData + " " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Couldn't send packet to " + IP + ":" + PORT
					+ " command: " + sendData + " " + e.getMessage());
		}
	}

	public static DatagramPacket receive(int PORT) {
		byte[] receiveData = new byte[NetSettings.getPacketSize()];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);

		try {
			DatagramSocket serverSocket = new DatagramSocket(PORT);
			
			serverSocket.receive(receivePacket);
			
			serverSocket.close();
		} catch (SocketException e) {
			System.err.println("Couldn't initialize DatagramSocket. "
					+ e.getMessage());
		} catch (IOException e) {
			System.err.println("Error receiving packet on port " + PORT + ". "
					+ e.getMessage());
		}
		
		return receivePacket;
	}
	
	//------------- C O N V E N I E N C E   M E T H O D S --------------------
	
	/**
	 * Convenience way of sending sensor data
	 * @param IP		receiver's IP address,		use NetSettings.get...Ip() if you can
	 * @param PORT		on which receiver listens,	use NetSettings.get...Port() if you can
	 * @param uss_f		ultrasonic sensor front
	 * @param uss_rf	ultrasonic sensor right front
	 * @param uss_rb	ultrasonic sensor right back
	 */
	public static void sendSensordata(String IP, int PORT, float uss_f, float uss_rf, float uss_rb) {
		byte[] sensorPacket = PacketHandler.makeSensorPacket(uss_f, uss_rf, uss_rb);
		send(IP, PORT, sensorPacket);
	}
	
	
	/**
	 * Convenience way of sending an Robot Command
	 * <br>Robot commands:
	 * <br>0:	stop the client or current process
	 * <br><b>101-200 to DRIVE FORWARD</b>
	 * <br>101----> drive 1 cm forward
	 * <br>200----> drive 100 cm forward
	 * <br><b>201-300 to DRIVE BACKWARD</b>
	 * <br>201----> drive 1 cm backward
	 * <br>300----> drive 100 cm backward
	 * <br><b>301-500 to TURN LEFT</b>
	 * <br>301----> turn 1 degree left
	 * <br>500----> turn 200 degrees left
	 * <br><b>501-700 to TURN RIGHT</b>
	 * <br>501----> turn 1 degree right
	 * <br>700----> turn 200 degrees right 
	 * @param IP		receiver's IP address,		use NetSettings.get...Ip() if you can
	 * @param PORT		on which receiver listens,	use NetSettings.get...Port() if you can
	 * @param command	is actually a short value	range(-32768 ... 32767)
	 * 					have a look at the table above
	 */
	public static void sendRobotCmd(String IP, int PORT, int command) {
		byte[] data = PacketHandler.makeRobotCommandPacket((short)command);
		send(IP, PORT, data);
	}
	
	
	/**
	 * <b>Sry, currently unimplemented</b>
	 * Convenience way of sending an ACKnowledgement packet
	 */
	public static void sendACK( ) {//String IP, int PORT, int ackNr) {
		//System.out.println("sendACK currently unimplemented");
		byte[] data = new byte[NetSettings.getPacketSize()];
		// set type of packet
		data[0] = 2;
		// TODO: fill packet 
		send(NetSettings.getPcIp(), NetSettings.getPcPort(), data);
	}
	
	
	/**
	 * Convenience way of receiving a robot command
	 * @param PORT	on which receiver listens,	use NetSettings.get...Port() if you can
	 * @return	short robotCommand
	 */
	public static short receiveRobotCmd(int PORT) {
		DatagramPacket received = receive(PORT);
		byte[] receivedData = received.getData();
		String s = PacketHandler.getContent(receivedData);
		return Short.parseShort(s);
	}
	
	/**
	 * 
	 * @param timeout	
	 */
	public static void waitForACK(int timeout) {
		
	}
	
	//------------- I N F O   P A C K E T   M E T H O D S --------------------
	
	/**
	 * <b>Do not use this for controlling anything.</b>
	 * <br>Just use it for information you want to have being printed on the other device
	 * <br>Port is automatically set to NetSettings.getInfoPort()
	 * @param IP	receiver's ip address, use NetSettings.get...Ip() if you can
	 * @param info	String containing info, <b>maximum of 64 bytes</b>
	 */
	public static void sendInfo(String IP, String info) {
		send(IP, NetSettings.getInfoPort(), info.getBytes());
	}
	
	/**
	 * <b>Do not use this for controlling anything.</b>
	 * <br>Just use it for information you want to have being printed on the other device
	 * <br>Port is automatically set to NetSettings.getInfoPort()
	 */
	public static String receiveInfo() {
		String info = "";
		DatagramPacket receivedPacket = receive(NetSettings.getInfoPort());
		byte[] data = receivedPacket.getData();
		for(int i=0; i<receivedPacket.getLength(); ++i)
			info += (char)data[i];
		return info;
	}
}
