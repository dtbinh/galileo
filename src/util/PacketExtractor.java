package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import network.NetSettings;

public class PacketExtractor {
	
	public static void main(String[] args){
		// teststuff... will be deleted when everything is implemented!
		byte[] sensorPacket = new byte[NetSettings.getPacketSize()];
		sensorPacket[0] = 0;
		for(int i=0; i<4; ++i)
			sensorPacket[i+1] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensorPacket[i+5] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensorPacket[i+9] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		
		byte[] robotCmdPacket = new byte[NetSettings.getPacketSize()];
		robotCmdPacket[0] = 1;
		short s = 12207;//(short) 0b0010_1111_1010_1111;
		System.out.println(s);
		for(int i=0; i < 2; ++i)
			robotCmdPacket[i+1] = (byte) ((s >> (1 - i) * 8) & 0xff);
		
		byte[] ackPacket = new byte[NetSettings.getPacketSize()];
		
		System.out.println("getsensordata");
		System.out.println(getContent(sensorPacket));
		System.out.println("getrobotcommand");
		System.out.println(getContent(robotCmdPacket));
		System.out.println("getack");
		System.out.println(getACK(ackPacket));
	}
	
	private PacketExtractor() {
		// private constructor, so you cannot create an instance of it
	}
	
	/**
	 * Returns the values as String
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
			str = getACK(packetbytes);
		
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
		float[] data = new float[3];
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
	 * <br>Sry, currently unimplemented!</br>
	 * @param	packetbytes	- data part of a DatagramPacket
	 * @return
	 */
	private static String getACK (byte[] packetbytes) {
		String str = "";
		return str;
	}
}
