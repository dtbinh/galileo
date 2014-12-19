package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Net {
	
	private Net(){
		// private constructor, so you cannot create an instance of it
	}
	
	/**
	 * <b>Sry, currently unimplemented</b>
	 */
	public static void sendACK() {
		System.out.println("sendACK currently unimplemented");
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
	 */
	public static void sendRobotCmd(String IP, int PORT, int command) {
		byte[] data = new byte[NetSettings.getPacketSize()];
		// set type of packet
		data[0] = 1;		
		// fill packet
		for(int i=0; i < 2; ++i)
			data[i+1] = (byte) ((command >> (1 - i) * 8) & 0xff);
		send(IP, PORT, data);
	}
	
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
			System.err.println("Couldn't send packet, cause it exceeds "
					+ "the maximum of " + NetSettings.getPacketSize());
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

	public static byte[] receive(int PORT) {
		byte[] receiveData = new byte[NetSettings.getPacketSize()];

		try {
			DatagramSocket serverSocket = new DatagramSocket(PORT);
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			serverSocket.receive(receivePacket);
			receiveData = receivePacket.getData();

			serverSocket.close();
		} catch (SocketException e) {
			System.err.println("Couldn't initialize DatagramSocket. "
					+ e.getMessage());
		} catch (IOException e) {
			System.err.println("Error receiving packet on port " + PORT + ". "
					+ e.getMessage());
		}
		
		return receiveData;
	}
}
