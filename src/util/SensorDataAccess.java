package util;

import exceptions.NoSensorDataAvailableException;

public class SensorDataAccess {

	private static float frontSensor = -1.0f ;
	private static float rightUpperSensor = -1.0f;
	private static float rightDownerSensor = -1.0f;
	
	public static float getFrontSensor() throws NoSensorDataAvailableException{
		if (frontSensor == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("front Sensor");
		}
		return frontSensor;
	}
	public static void setFrontSensor(float frontSensor) {
		SensorDataAccess.frontSensor = frontSensor;
	}
	public static float getRightUpperSensor() throws NoSensorDataAvailableException {
		if (rightUpperSensor == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("rightUpper Sensor");
		}
		return rightUpperSensor;
	}
	public static void setRightUpperSensor(float rightUpperSensor) {
		SensorDataAccess.rightUpperSensor = rightUpperSensor;
	}
	public static float getRightDownerSensor() throws NoSensorDataAvailableException {
		if (rightDownerSensor == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("rightDowner Sensor");
		}
		return rightDownerSensor;
	}
	public static void setRightDownerSensor(float rightDownerSensor) {
		SensorDataAccess.rightDownerSensor = rightDownerSensor;
	}
	
}
