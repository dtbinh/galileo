package main;

import network.Net;
import network.NetSettings;

public class testi {
public static void main(String[]args){
	Net.sendRobotCmd(NetSettings.getEv2Ip(),NetSettings.getRobotPort(), 110); // drive 1 cm forward
}
}
