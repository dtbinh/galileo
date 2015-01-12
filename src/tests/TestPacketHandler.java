package tests;

import util.PacketHandler;

public class TestPacketHandler {
	public static void main(String[] args) {
		byte[] sensorPacket = PacketHandler.makeSensorPacket(0.35700002f, 0.35700002f, 0.35700002f,0.35700002f);
		
		short s = 12207;
		byte[] robotCmdPacket = PacketHandler.makeRobotCommandPacket(s);
		
		byte[] ackPacket = PacketHandler.makeACKPacket(111);
		
		System.out.print("made sensorPacket 0.35700002f\n  getSensorData:\t");
		System.out.println(PacketHandler.getContent(sensorPacket));
		System.out.print("made robotCmdPacket(12207)\n  getrobotcommand:\t");
		System.out.println(PacketHandler.getContent(robotCmdPacket));
		System.out.print("made ackPacket(111)\n  getack:\t");
		System.out.println(PacketHandler.getAckNumber(ackPacket));
	}

}
