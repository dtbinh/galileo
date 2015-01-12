package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import network.NetSettings;

public class PacketHandler {
	
	private PacketHandler() {
		// private constructor, so you cannot create an instance of it
	}
	
	/**
	 * Returns the values as String
	 * <br>useful for printing values
	 * <br>At first it looks at the first byte to see what kind of data it contains
	 * <br>0 -> sensordata
	 * <br>1 -> robot command
	 * <br>2 -> ACKnowledgement
	 * <br>Then it extracts the data according to the packet convention
	 * @param packetbytes	byte array containing data
	 * @return		content interpreted as specified in the packet_convention
	 * @see			galileo/doc/packet_convention.txt
	 */
	public static String getContent (byte[] packetbytes) {
		String str = "";
		
		if (packetbytes[0] == 0) {
			float[] sensordata = getSensordata(packetbytes);
			str = sensordata[0] + " , " + sensordata[1] + " , " + sensordata[2];
		}
		else if (packetbytes[0] == 1)
			str = getRobotCommand(packetbytes) +"";
		else if (packetbytes[0] == 2)
			str = getAckNumber(packetbytes) +"";
		
		return str;
	}
	
	/**
	 * Returns sensordata in an float[]
	 * <br> [0] ----> uss_f
	 * <br> [1] ----> uss_rf
	 * <br> [2] ----> uss_rb
	 * @param	packetbytes	- data part of a DatagramPacket
	 * @return	float[] sensordata
	 */
	public static float[] getSensordata (byte[] packetbytes) {
		float[] data = new float[4];
		byte[] bytes = new byte[4];
		// uss_f
		for(int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i];
		data[0] = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		// uss_rf
		for (int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i+4];
		data[1] = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		// uss_rb
		for (int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i+8];
		data[2] = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		// uss_lb
		for (int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i+12];
		data[3] = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		return data;
	}
	
	/**
	 * Extracts robot Command
	 * @param	packetbytes	- data part of a DatagramPacket
	 * @return	short robotCommand
	 */
	public static short getRobotCommand (byte[] packetbytes) {
		byte[] bytes = new byte[2];
		for(int i=1; i <= 2; ++i)
			bytes[i-1] = packetbytes[i];
		
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
	}
	
	/**
	 * Extract Acknowledgement number from a packet
	 * @param	packetbytes	- data part of a DatagramPacket
	 * @return	int acknumber
	 */
	public static int getAckNumber (byte[] packetbytes) {
		byte[] bytes = new byte[4];
		for(int i=1; i<=4; ++i)
			bytes[i-1] = (byte)packetbytes[i];
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getInt();
	}
	
	//------------------------------------------------------
	
	public static byte[] makeRobotCommandPacket(short robotCommand) {
		byte[] robotCmdPacket = new byte[NetSettings.getPacketSize()];
		// set type of packet
		robotCmdPacket[0] = 1;
		// fill packet
		for(int i=0; i < 2; ++i)
			robotCmdPacket[i+1] = (byte) ((robotCommand >> (1 - i) * 8) & 0xff);
		return robotCmdPacket;
	}
	
	public static byte[] makeSensorPacket(float uss_f, float uss_rf, float uss_rb , float uss_lb) {
		byte[] sensordataArr = new byte[NetSettings.getPacketSize()];
		sensordataArr[0] = 0;		// set type of packet
		// fill packet
		for(int i=0; i<4; ++i)
			sensordataArr[i+1] = (byte)((Float.floatToIntBits(uss_f)  >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensordataArr[i+5] = (byte)((Float.floatToIntBits(uss_rf) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensordataArr[i+9] = (byte)((Float.floatToIntBits(uss_rb) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensordataArr[i+13] = (byte)((Float.floatToIntBits(uss_lb) >> ((7 - i) * 8)) & 0xff);
		return sensordataArr;
	}
	
	public static byte[] makeACKPacket(int ackNr) {
		byte[] ackPacket = new byte[NetSettings.getPacketSize()];
		ackPacket[0] = 2;			// set type of packet
		// fill packet
		for(int i=0; i<4; ++i)
			ackPacket[i+1] = (byte) (ackNr >> ((7 - i) * 8) & 0xff);
		return ackPacket;
	}
}
