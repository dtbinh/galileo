package pathfinding;

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
		while (true) {
			drivenDistance = drive();
			if(round==0){
				map.updateMapFromVectors(new TestVector(drivenDistance,0));
			}else if(round==1){
				map.updateMapFromVectors(new TestVector(0,drivenDistance));
			}else if (round==2){
				map.updateMapFromVectors(new TestVector(-drivenDistance,0));
			}else if(round==3){
				map.updateMapFromVectors(new TestVector(0,-drivenDistance));
			}
			turnNinety();
			turnTillSheer();
			round+=1;
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

	public int driveTillContact() {
		float sensorDataFront = 10;
		float sensorDataSides = 10;
		int distance = 0;
		while (sensorDataFront > 0.2 || sensorDataFront == 0.0) {

			Net.sendRobotCmd(NetSettings.getEv2Ip(),
					NetSettings.getRobotPort(), 110); // drive 1 cm forward
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			distance += 1;
			if (sensorDataSides > 0.01) {
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 303);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sensorDataSides < -0.01) {
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 503);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				sensorDataFront = SensorDataAccess.getUss_f();
				sensorDataSides = SensorDataAccess.getUss_rb()
						- SensorDataAccess.getUss_rf();
			} catch (NoSensorDataAvailableException e) {

				e.printStackTrace();
			}
		}
		return distance;
	}

	public void turnNinety() throws InterruptedException {
		Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 77);
		Thread.sleep(500);

	}

	public void turnTillSheer() {
		float sensorDataSides = 10;
		while (sensorDataSides > 0.001 || sensorDataSides < -0.001) {
			if (sensorDataSides > 0.001) {
				Net.sendRobotCmd(NetSettings.getEv2Ip(),
						NetSettings.getRobotPort(), 301);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			if (sensorDataSides < -0.001) {
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
