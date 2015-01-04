package pathfinding;

import java.util.ArrayList;
import java.util.Vector;

import exceptions.NoSensorDataAvailableException;
import sensor.SensorDataAccess;
import util.CSVFile;
import mapping.Map;
import network.Net;
import network.NetSettings;

public class RobotPath { // This class implements the general behaviour
							// (pathfinding) of our robots
	
	private RobotLocation myLocation;

	private final float PARALLELTOWALLERROR = 0.02f;
	
	private final float CONTACTERROR = 0.1f;

	private boolean hasMapSize;

	private boolean waitingForACK;

	private boolean parallelToWall;

	public boolean isParallelToWall() {
		return parallelToWall;
	}

	public void setParallelToWall(boolean parallelToWall) {
		this.parallelToWall = parallelToWall;
	}

	public boolean updateParallelToWall() {
		try {
			if (SensorDataAccess.getUss_rf() - SensorDataAccess.getUss_rb() < PARALLELTOWALLERROR) {
				this.parallelToWall = true;
				return parallelToWall;
			}
		} catch (NoSensorDataAvailableException e) {
			System.out.println("Currently no reasonable data on " + e.sensorString);
		}
		this.parallelToWall = false;
		return parallelToWall;
	}

	public boolean isWaitingForACK() {
		return waitingForACK;
	}

	public void setWaitingForACK(boolean waitingForACKs) {
		this.waitingForACK = waitingForACKs;
	}

	public RobotPath() { // Constructor

		
		this.hasMapSize = false;
	}

	public void run(Map m) {
		
		System.out.println("[Robot]: Let's have a look at this room...");

		driveTillContact(); // now i should be at an obstacle
		
		System. out.println("[Robot]: Hey, this could be the wall!");
		
		turnToParallel(); // now i should be parallel to an obstacle
		
		driveTillContact(); // now i should be at a corner
		// now i am at a corner and ready to relocate myself
		
		// initialize robot location
		
		myLocation = new RobotLocation(m);
		this.hasMapSize = false;
		
		System.out.println("[Robot]: OK, I think this is an edge of the room.");
		System.out.println("[Computer]: OK, I start calculating the map here.");
		
		while(this.hasMapSize==false) {
			double angle = this.turnToParallel();
			double length = this.driveAndCountTacho();
			System.out.println("[Robot]: Got " + length + " centimeters in " + angle + " direction.");
			myLocation.move(angle, length);
			System.out.println("[Laptop]: Perfect, got a new Vector for the map.");
			//if(this.myLocation.getRelativeToStart().x == 0 && this.myLocation.getRelativeToStart().y == 0) {//TODO: Insert value error here
				//break;
			//}
			
		}
		


		while (this.hasMapSize = false) {
			
		}

		// now we are at the first edge. now get the shape of the room

		while (this.hasMapSize == false) {

			// this is the part where the robots drive around the wall and
			// get the size
			// of the room

		}

		// here the robots examine the inner part of the room, also
		// obstacles
	}

	public double turnToParallel() { // repeatately sends turn to the robot till ti is parallel to an obstacle
		
		double angleThatIHaveTurned = 0.0d;
		String ip = NetSettings.getEv2Ip();
		int port = NetSettings.getRobotPort();
		
		Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 315);  // turn 15 degrees at first
		//Net.receive(NetSettings.getPcPort());
		angleThatIHaveTurned += 15;
			
		while (true) {
			Net.sendRobotCmd(ip, port, 302);; // turn 2 degrees again and again till i am parallel
			//Net.receive(NetSettings.getPcPort());
			angleThatIHaveTurned += 2;
			
			if (this.updateParallelToWall()==true) {
				break;
			}
		}
		return angleThatIHaveTurned;
	}

	public void driveTillContact() { // this function lets the robot drive 1 cm
										// forward until it's less than 3 cm
										// from the wall
		
		while (true) {
			Net.sendRobotCmd(NetSettings.getEv2Ip(),NetSettings.getRobotPort(), 101); // drive 1 cm forward
			//Net.receive(NetSettings.getPcPort());
			try {
				if (SensorDataAccess.getUss_f() < this.CONTACTERROR) { // am i near to an
																// obstacle?
					break;
				}
			} catch (NoSensorDataAvailableException e) {
				System.out.println("Currently no reasonable Data on " + e.sensorString);
			}
		}
	}
	
	public void driveForward() {
		
	}
	
	public double driveAndCountTacho() {
		double drivenDistance = 0.0d;
		while(true) {
			Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 101);
			drivenDistance += 0.01;
			try {
				if(sensor.SensorDataAccess.getUss_f() < 0.03) {
					break;
				}
			} catch (NoSensorDataAvailableException e) {
				System.out.println("Currently no reasonable data on " + e.sensorString);
			}
		}
		return drivenDistance;
		
		
		
	}

	// Methods for access to sensor data - dummy implementation
	
/*
	public ArrayList<String[]> getHistory() {
		return CSVFile.readComplete();

	}

	public ArrayList<Float> currentSensorData() {
		

		// gets the sensor data out of the history file
		
		

		int historySize = getHistory().size();
		//System.out.println("History size: " + historySize);
		String[] packageData = getHistory().get(historySize - 1);
		int packageDataSize = packageData.length;
		String sensorData = packageData[packageDataSize - 1];

		// now we have to parse and cast from string to float
		//System.out.println(packageData[packageDataSize - 1]);
		String[] data = sensorData.split(" ");
		ArrayList<Float> convertedData = new ArrayList<Float>();
		for (int i = 1; i < data.length; i += 2) {
			//System.out.println(data[i]);
			convertedData.add(Float.parseFloat(data[i])); // works not
		}

		return convertedData;

	}
	
	*/

	// Getters and setters

	public boolean getHasMapSize() {
		return hasMapSize;
	}

	public void setHasMapSize(boolean hasMapSize) {
		this.hasMapSize = hasMapSize;
	}

}
