package tests;

import network.Net;
import network.NetSettings;

public class TestingSend {
	
	public static void main(String[] args) throws InterruptedException {
		if ( TestSettings.networkTestInfoPacket) {
			Net.sendInfo("127.0.0.1", "1234567890hallohallo1234567890hallohallo1234567890hallohallo1234!!!");
		} else {
			Net.sendRobotCmd("127.0.0.1", NetSettings.getPcPort(), 220);
			Thread.sleep(300);
			Net.sendSensordata("127.0.0.1", NetSettings.getPcPort(), 0.35700002f, 0.35700002f, 0.35700002f);
			Thread.sleep(300);
			Net.sendACK();
			Thread.sleep(300);
		}
	}

}
