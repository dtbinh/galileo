package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Packetdata {
	
	public static void main(String[] args){
		
		byte[] sensorPacket = new byte[1024];
		sensorPacket[0] = 0;
		for(int i=0; i<4; ++i)
			sensorPacket[i+1] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensorPacket[i+5] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		for(int i=0; i<4; ++i)
			sensorPacket[i+9] = (byte)((Float.floatToIntBits((float)0.35700002) >> ((7 - i) * 8)) & 0xff);
		
		byte[] robotCmdPacket = new byte[1024];
		robotCmdPacket[0] = 1;
		short s = 32767;
		short s2 = 127;
		
//		for(int i=0; i<2; ++i)
//			robotCmdPacket[i+1] = (byte) ((s >> ((3 - i) * 4)) & 0xff);
		robotCmdPacket[1] = (byte) (s & 0xff);
		robotCmdPacket[2] = (byte) ((s & 0xff00)>>8);
//		for(int i=0; i<2; ++i)
//			robotCmdPacket[i+5] = (byte) ((s >> ((7 - i) * 8)) & 0xff);
		
		System.out.println(getContent(sensorPacket));
		System.out.println(getContent(robotCmdPacket));
		
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
		System.out.println("getsensordata");
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
		System.out.println("getrobotcmd");
		for(int i=0; i< packetbytes.length; ++i)
			System.out.print(packetbytes[i]);
		System.out.println();
		String str = "";
		byte[] bytes = new byte[2];
		for(int i=0; i < 2; ++i)
			bytes[i] = packetbytes[i+1];
		int robotCmd = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort();
		str += robotCmd;
		return str;
	}
	
	private static String getACK (byte[] packetbytes) {
		System.out.println("getack");
		String str = "";
		return str;
	}
	
	/**
	 * Returns the values Byte by Byte ( interpreted as bytes )
	 * @param bytes	byte array containing data
	 * @return		content interpreted as bytes
	 */
	public static String getByteByByte (byte[] bytes) {
		String str = "";
		for(int i=0; i<bytes.length; ++i) {
			str += bytes[i];
		}
		return str;
	}

}
