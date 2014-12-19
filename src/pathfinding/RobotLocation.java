package pathfinding;

import java.util.ArrayList;

import org.jfree.data.xy.MatrixSeries;

import mapping.Map;
import mapping.TestVector;

import org.jfree.data.xy.Vector;

import mapping.*;

public class RobotLocation {
	
	private Map myMap;
	
	private TestVector lastPassedWay;
	
	public TestVector getLastPassedWay(){ // this is the latest added vector for ziad
		return lastPassedWay;
	}
	
	private TestVector relativeToStart;
	
	private double currentTotalAngle;
	
	public double getCurrentTotalAngle(){
		return this.currentTotalAngle;
	}
	
	public TestVector getRelativeToStart(){
		return this.relativeToStart;
	}
	
	public void setRealativeToStart(int x, int y){
		this.relativeToStart = new TestVector (x,y);
	}
	
	// Constructor
	public RobotLocation(Map m){
		this.relativeToStart = new TestVector(0,0); // initialize the current position with 0/0
		this.currentTotalAngle = 0.0;
		this.lastPassedWay = new TestVector (0,0);
		this.myMap = m;
	// just a bit of testing:
		test();
		
	}
	 
	public void move(double d, double r) { // creates new geographical vector by a length and an angle
		
		this.currentTotalAngle += d ; // get orientation in room
		double deltaX = r * Math.cos(currentTotalAngle/180*Math.PI);
		double deltaY = r * Math.sin(currentTotalAngle/180*Math.PI); // use addition theorems
		this.lastPassedWay = new TestVector((int)(deltaX*100),(int)(deltaY*100)); // in cm and int
		myMap.updateMapFromVectors(lastPassedWay);
		this.relativeToStart = new TestVector(relativeToStart.x + lastPassedWay.x, relativeToStart.y + lastPassedWay.y);
		
	}
	
	private void test() {
		double degrees = 0;
		double meters = 5;
		System.out.println("Position of the robot: " + this.relativeToStart.x + " / " + this.relativeToStart.y);
		this.move(degrees, meters);
		System.out.println("Turned " + degrees + " degrees and moved " + meters + " meters");
		System.out.println("Position of the robot: " + this.relativeToStart.x + " / " + this.relativeToStart.y);
		degrees = 45;
		meters = 5;
		this.move(degrees, meters);
		System.out.println("Turned " + degrees + " degrees and moved " + meters + " meters");
		System.out.println("Position of the robot: " + this.relativeToStart.x + " / " + this.relativeToStart.y);
	}

}
