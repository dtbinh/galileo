package main;

import java.io.IOException;

import movement.Movement;
import network.Net;
import network.NetworkSettings;

public class ControlClient {
	
	public void run() throws IOException {
		Movement movements = new Movement();
		
		int input = 8;	// any value between 1 & 100 will just wait for another input
		
		while ( input != 0){
			//0 to stop the client the value should be
			//0 to stop the current process, while it's running
			System.out.println("waiting for cmd");
			int received = Net.receive(NetworkSettings.getRobotPort())[0];
			input = received;
			System.out.println("received: " + received);
			
			// to drive forward the value of the entry should be between 101 and 200
			// 101: drive 1 cm forward;.......;200: drive 100 cm forward
			if ( (101<=input)&&(input<=200) ) {
				movements.driveForward(input-100);
				Net.sendACK();
				
			//to drive backward the value of the entry should be between 201 and 300
			// 201: drive 1 cm backward;.......;300: drive 100 cm backward
			} else if ( (201<=input)&&(input<=300) ) {
				movements.driveBackward(input-200);
				Net.sendACK();
				
			// to turn left the value of the entry should be between 301 and 500 
	        // 301 turn 1 degree left;.......;500: turn 200 degrees left 
			}else if ( (301<=input)&&(input<=500) ) {
				movements.turnLeft(input-300);
				Net.sendACK();
				
			// to turn right the value of the entry should be between 501 and 700
			// 501 turn 1 degree right;.......;700: turn 200 degrees right
			}else if ( (501<=input)&&(input<=700) ) {
				movements.turnRight(input-500);
				Net.sendACK();
            }
		} // time to get another input! :)
	}
}
