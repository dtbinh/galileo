package main;

import network.Net;
import network.NetSettings;

public class TestingSend {
	public static void main(String[] args) {
		byte[] sendData = new byte[NetSettings.getBufferSize()];
		sendData[0] = 1;
		sendData[1] = 127;
		System.out.println(sendData.length);
		Net.send("127.0.0.1", NetSettings.getPcPort(), sendData);
	}

}
