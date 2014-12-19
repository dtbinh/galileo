package util;

public class SensorDataAccess {

	private static float frontSensor;
	private static float rightUpperSensor;
	private static float rightDownerSensor;
	
	public static float getFrontSensor() {
		return frontSensor;
	}
	public static void setFrontSensor(float frontSensor) {
		SensorDataAccess.frontSensor = frontSensor;
	}
	public static float getRightUpperSensor() {
		return rightUpperSensor;
	}
	public static void setRightUpperSensor(float rightUpperSensor) {
		SensorDataAccess.rightUpperSensor = rightUpperSensor;
	}
	public static float getRightDownerSensor() {
		return rightDownerSensor;
	}
	public static void setRightDownerSensor(float rightDownerSensor) {
		SensorDataAccess.rightDownerSensor = rightDownerSensor;
	}
	
}
