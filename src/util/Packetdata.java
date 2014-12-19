package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import network.NetSettings;

public class Packetdata {
	
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
	
	private Packetdata() {
		// private constructor, so you cannot create an instance of it
	}
	
	/**
	 * Returns the values interpreted according to the packet convention
	 * At first it looks at the first byte to see what kind of data it contains
	 * 0 -> sensordata
	 * 1 -> robot command
	 * 2 -> ACKnowledgement
	 * @param packetbytes	byte array containing data
	 * @return		content interpreted as specified in the packet_convention
	 * @see			galileo/doc/packet_convention.txt
	 */
	public static String getContent (byte[] packetbytes) {
		String str = "";
		
		if (packetbytes[0] == 0)
			str = getSensordata(packetbytes);
		else if (packetbytes[0] == 1)
			str = getRobotCommand(packetbytes);
		else if (packetbytes[0] == 2)
			str = getACK(packetbytes);
		
		return str;
	}
	
	private static String getSensordata (byte[] packetbytes) {
		String str = "";
		byte[] bytes = new byte[4];
		
		for(int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i];
		float uss_f = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		for (int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i+4];
		float uss_rf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		
		for (int i=1; i <= 4; ++i)
			bytes[i-1] = packetbytes[i+8];
		float uss_rb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
		str += uss_f;
		str += " , ";
		str += uss_rf;
		str += " , ";
		str += uss_rb;
		return str;
	}
	
	private static String getRobotCommand (byte[] packetbytes) {
		String str = "";
		byte[] bytes = new byte[2];
		for(int i=1; i <= 2; ++i)
			bytes[i-1] = packetbytes[i];
		//System.out.println(bytes[1]);
		int robotCmd = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
		str += robotCmd;
		return str;
	}
	
	/**
	 * <br>Sry, currently unimplemented!</br>
	 * @param packetbytes
	 * @return
	 */
	private static String getACK (byte[] packetbytes) {
		String str = "";
		return str;
	}
}
