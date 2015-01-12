package tests;

import util.CSVFile;
import util.PacketHandler;

public class TestCSVFile {
	public static void main(String[] args) {
		byte[] packetdata = PacketHandler.makeSensorPacket(10.22f, 20.22f, 20.22f, 22.22f);
		CSVFile.write(CSVFile.robotDataFilename, "EV0", packetdata);
		CSVFile.print(CSVFile.robotDataFilename);
	}
}
