package pathfinding;

import java.util.ArrayList;

import mapping.Map;
import mapping.TestVector;
import network.Net;
import network.NetSettings;
import sensor.SensorDataAccess;
import exceptions.NoSensorDataAvailableException;

public class Puffi {
	private Map map;
	private TestVector vector;

	public Puffi(Map map) {
		this.map = map;
	}

	private static int TIMEFORONECENTIMETER = 90;// robot needs about 90ms for 1
													// cms

	public void run() throws InterruptedException {
		int drivenDistance = 0;
		int round = 0;
		findInitPoint();
		while (round <=3) {
			
			drivenDistance = driveTillContact();
			if (round == 0) {
//				System.out.println("driven distance " + drivenDistance);
				 map.updateMapFromVectors(new TestVector((drivenDistance+4),0));
//				map.updateMapFromVectors(new TestVector(5, 0));
			} else if (round == 1) {
//				System.out.println("driven distance " + drivenDistance);
				 map.updateMapFromVectors(new TestVector(0,(drivenDistance+2)));
//				map.updateMapFromVectors(new TestVector(0, 5));
			} else if (round == 2) {
//				System.out.println("driven distance " + drivenDistance);
				 map.updateMapFromVectors(new TestVector((-drivenDistance-4),0));
//				map.updateMapFromVectors(new TestVector(-5, 0));
			} else if (round == 3) {
//				System.out.println("driven distance " + drivenDistance);
				 map.updateMapFromVectors(new TestVector(0,(-drivenDistance-2)));
//				map.updateMapFromVectors(new TestVector(0, -5));
//				System.out.println(""+map.toString());
			}
			turnLeftNinety();
			turnTillSheer();
			round += 1;
		}
//		map.updateMapFromVectors(new TestVector((28+4),0));
//		map.updateMapFromVectors(new TestVector(0,(28+2)));
//		map.updateMapFromVectors(new TestVector((-28-4),0));
//		map.updateMapFromVectors(new TestVector(0,(-28-2)));
		round = 0;
		int mapSize=map.size()+1;
		ArrayList<TestVector> obstacles = new ArrayList();
		System.out.println("mapsize "+mapSize);
		while (round <=3) {
			
			obstacles=obstacleDrive(1.2f);
			if (round == 0) {
//				System.out.println("Vector x: "+vector.x);
//				System.out.println("Vector y: "+vector.y);
				for(TestVector obstacle :  obstacles){
					map.insertObstacle((obstacle.x+6),obstacle.y-1);
				}
//				map.insertObstacle((vector.x+3),vector.y);
			} else if (round == 1) {
//				System.out.println("Vector x: "+vector.x);
//				System.out.println("Vector y: "+vector.y);
				for(TestVector obstacle :  obstacles){
					map.insertObstacle((obstacle.y),(map.size()-(obstacle.x+5)));
				}
				
			
			} else if (round == 2) {
				for(TestVector obstacle :  obstacles){
					map.insertObstacle(map.size()-(obstacle.x+3),map.size()-(obstacle.y));
				}
			
			} else if (round == 3) {
				for(TestVector obstacle :  obstacles){
					map.insertObstacle((map.size()-(obstacle.y-2)),(obstacle.x+4));
				}
			
			}
			turnLeftNinety();
			turnTillSheer();
			round += 1;
		}
		// drive();
		// turnNinety();
		// turnTillSheer();
		// int distance;
		// distance=driveTillContact();

		// while(true){
		// distance=driveTillContact();
		// System.out.println("distance"+distance);
		// Thread.sleep(1000);
		// turnNinety();
		// Thread.sleep(1000);
		// turnTillSheer();
		// }

	}

	// public void run() throws InterruptedException {
	// int drivenDistance = 0;
	// int round = 0;
	// drive();
	// turnLeftNinety();
	// turnTillSheer();
	// for (int i = 0; i < 4; i++) { // we made the frame of the ground with
	// // the sensor looking outside
	// drivenDistance = drive();
	// if (round == 0) {
	// // map.updateMapFromVectors(new TestVector(drivenDistance, 0));
	// } else if (round == 1) {
	// // map.updateMapFromVectors(new TestVector(0, drivenDistance));
	// } else if (round == 2) {
	// // map.updateMapFromVectors(new TestVector(-drivenDistance, 0));
	// } else if (round == 3) {
	// // map.updateMapFromVectors(new TestVector(0, -drivenDistance));
	// }
	// turnLeftNinety();
	// turnTillSheer();
	// round += 1;
	// }
	// // turn 180 degrees to the right
	// // first 90º right and parallel wall
	// round = 0;
	// turnLeftNinety();
	// turnLeftNinety();
	// for (int i = 0; i < 4; i++) { // we made the frame of the ground with
	// // the sensor looking inside
	//
	// if (Math.abs(getDistanceRFSensor() - getDistanceRBSensor()) < 0.02) {
	// turnTillSheer();
	// }
	// // float backRightSensorDistance = getDistanceRBSensor();
	// while (getDistanceFrontSensor() > 0.05) {
	// driveXcm(10);
	// Thread.sleep(10*TIMEFORONECENTIMETER);
	// if (Math.abs(getDistanceRFSensor() - getDistanceRBSensor()) < 0.02) {
	// turnTillSheer();
	// }
	//
	// }
	// // drive backward 5 cm
	// Net.sendRobotCmd(NetSettings.getEv2Ip(),
	// NetSettings.getRobotPort(), 205);
	// turnRightNinety();
	// }
	//
	// }

	public void findInitPoint(){
		driveTillContact();
		try {
			turnLeftNinety();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnTillSheer();
		driveTillContact();
		try {
			turnLeftNinety();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnTillSheer();
	}
	public float getDistanceFrontSensor() {
		float frontSensor = -1.0f;
		while (frontSensor == -1.0f) {
			try {
				frontSensor = SensorDataAccess.getUss_f();
			} catch (NoSensorDataAvailableException e) {
				// TODO Auto-generated catch block

			}
		}
		return frontSensor;
	}

	public float getDistanceRFSensor() {
		float rfSensor = -1.0f;
		while (rfSensor == -1.0f) {
			try {
				rfSensor = SensorDataAccess.getUss_rf();
			} catch (NoSensorDataAvailableException e) {
				// TODO Auto-generated catch block

			}
		}
		return rfSensor;
	}

	public float getDistanceRBSensor() {
		float rbSensor = -1.0f;
		while (rbSensor == -1.0f) {
			try {
				rbSensor = SensorDataAccess.getUss_rb();
			} catch (NoSensorDataAvailableException e) {
				// TODO Auto-generated catch block

			}
		}
		return rbSensor;
	}

	public void driveXcm(int cm) {
		int code = 100 + cm;
		Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(),
				code);
	}

	public void turnRightNinety() throws InterruptedException {
		Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 78);
		Thread.sleep(500);

	}

	public float getDistance() {
		float frontSensor = -1.0f;
		while (frontSensor == -1.0f) {
			try {
				frontSensor = SensorDataAccess.getUss_f();
			} catch (NoSensorDataAvailableException e) {
				// TODO Auto-generated catch block

			}
		}
		return frontSensor;
	}

	public int drive() {
		float firstDistance = getDistance();
		int drivenDistance = 0;
		while (firstDistance > 0.1) {
			firstDistance = firstDistance * 100;

			if (firstDistance >= 100) {

				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 200); // drive 1 cm forward
				drivenDistance += 100;
				try {
					Thread.sleep(100 * TIMEFORONECENTIMETER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (firstDistance <= 100) {
				firstDistance += 90;
				if ((int) firstDistance == 100) {
					Net.sendRobotCmd(NetSettings.getEv2Ip(),
							NetSettings.getRobotPort(), 101); // drive 1 cm
																// forward
					try {
						Thread.sleep(2 * TIMEFORONECENTIMETER);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Net.sendRobotCmd(NetSettings.getEv2Ip(),
							NetSettings.getRobotPort(), (int) firstDistance); // drive
																				// 1
																				// cm
																				// forward
				}
				firstDistance -= 90;
				drivenDistance += (int) firstDistance;
				try {
					Thread.sleep((int) firstDistance * TIMEFORONECENTIMETER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			firstDistance = getDistance();
		}
		return drivenDistance;

	}
	
	public ArrayList <TestVector> obstacleDrive(float mapSize){
		float sensorDataFront = 10;
		float sensorDataLeft=10;
		int distance = 0;
		ArrayList <TestVector> obstacles= new ArrayList();
		while (sensorDataFront > 0.1 || sensorDataFront == 0.0) {
			
			if(sensorDataFront<0.2){
				int distanceToWall=(int)(getDistance()*100);
				distanceToWall=95+(distanceToWall);
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), distanceToWall);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
			
			Net.sendRobotCmd(NetSettings.getEv2Ip(),
					NetSettings.getRobotPort(), 105); // drive 1 cm forward
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
			turnTillSheer();
			distance += 1;
			sensorDataLeft=getLeftDistance();
			System.out.println("sensorDataLeft: "+sensorDataLeft);
			System.out.println("mapSize: "+mapSize);
			if (sensorDataLeft < mapSize) {
				int obstacleDetectionCount=0;
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sensorDataLeft=getLeftDistance();
//				System.out.println("left"+sensorDataLeft);
				if(sensorDataLeft<mapSize){
					obstacleDetectionCount+=1;

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sensorDataLeft=getLeftDistance();
//				System.out.println("left"+sensorDataLeft);
				if(sensorDataLeft<mapSize){
					obstacleDetectionCount+=1;
		
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sensorDataLeft=getLeftDistance();
				if(sensorDataLeft<mapSize){
					obstacleDetectionCount+=1;
				}
				
				if(obstacleDetectionCount==3){
//					Net.sendRobotCmd(NetSettings.getEv2Ip(),
//							NetSettings.getRobotPort(), 99);
					System.out.println("sensorDataLeftBeforeVector: "+ sensorDataLeft);
					int x = (int)((sensorDataLeft+0.025)*20); //+0.05 to round up if its like 0.09
//					this.map.insertObstacle(y,distance);
					obstacles.add(new TestVector(x,distance));
//					System.out.println("from wall: "+(sensorDataLeft*10));
//					System.out.println("from other wall: "+distance);
//					System.out.println("OBJEEECT");
				}
			
			}
			
			try {
				sensorDataLeft = SensorDataAccess.getUss_lb();
				sensorDataFront = SensorDataAccess.getUss_f();
			} catch (NoSensorDataAvailableException e) {

				e.printStackTrace();
			}
		}
		return obstacles;
		
		
	}

	public int driveTillContact() {
		float sensorDataFront = 10;
		float sensorDataSides = 10;
		float sensorDataLeft=10;
		int distance = 0;
		while (sensorDataFront > 0.1 || sensorDataFront == 0.0) {
//			System.out.println("sensorDataLeft"+sensorDataLeft);
			
			if(sensorDataFront<0.2){
				int distanceToWall=(int)(getDistance()*100);
				distanceToWall=95+(distanceToWall);
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), distanceToWall);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
			
			Net.sendRobotCmd(NetSettings.getEv2Ip(),
					NetSettings.getRobotPort(), 105); // drive 1 cm forward
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
			turnTillSheer();
			distance += 1;
//			if (sensorDataSides > 0.01) {
//				Net.sendRobotCmd(NetSettings.getEv2Ip(),
//						NetSettings.getRobotPort(), 303);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (sensorDataSides < -0.01) {
//				Net.sendRobotCmd(NetSettings.getEv2Ip(),
//						NetSettings.getRobotPort(), 503);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (sensorDataLeft < 1.0) {
//				int obstacleDetectionCount=0;
//			
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				sensorDataLeft=getLeftDistance();
//				System.out.println("left"+sensorDataLeft);
//				if(sensorDataLeft<1.0){
//					obstacleDetectionCount+=1;
//
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				sensorDataLeft=getLeftDistance();
//				System.out.println("left"+sensorDataLeft);
//				if(sensorDataLeft<1.0){
//					obstacleDetectionCount+=1;
//		
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				sensorDataLeft=getLeftDistance();
//				System.out.println("left"+sensorDataLeft);
//				if(sensorDataLeft<1.0){
//					obstacleDetectionCount+=1;
//				}
//				
//				if(obstacleDetectionCount==3){
////					Net.sendRobotCmd(NetSettings.getEv2Ip(),
////							NetSettings.getRobotPort(), 99);
////					int y = (int)(sensorDataLeft*10);
////					this.map.insertObstacle(y,distance);
//					System.out.println("from wall: "+(sensorDataLeft*10));
//					System.out.println("from other wall: "+distance);
//					System.out.println("OBJEEECT");
//				}
//			
//			}
			
			try {
				sensorDataLeft = SensorDataAccess.getUss_lb();
				sensorDataFront = SensorDataAccess.getUss_f();
				sensorDataSides = SensorDataAccess.getUss_rb()
						- SensorDataAccess.getUss_rf();
			} catch (NoSensorDataAvailableException e) {

				e.printStackTrace();
			}
		}
		return distance;
	}


	public void turnLeftNinety() throws InterruptedException {
		Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 77);
		Thread.sleep(500);

	}
	
	public float getLeftDistance(){
		float leftSensor = -1.0f;
		while (leftSensor == -1.0f) {
			try {
				leftSensor = SensorDataAccess.getUss_lb();
			} catch (NoSensorDataAvailableException e) {
				// TODO Auto-generated catch block

			}
		}
		return leftSensor;
	}

	public void turnTillSheer() {
		float sensorDataSides = 10;
		while (sensorDataSides > 0.002 || sensorDataSides < -0.002) {
			if (sensorDataSides > 0.002) {
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 301);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			if (sensorDataSides < -0.002) {
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 501);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			try {
				sensorDataSides = SensorDataAccess.getUss_rb()
						- SensorDataAccess.getUss_rf();
			} catch (NoSensorDataAvailableException e) {
			}
		}

	}

}
