package util;

import exceptions.NoSensorDataAvailableException;

public class SensorDataAccess {

	private static float uss_f  = -1.0f ;
	private static float uss_rf = -1.0f;
	private static float uss_rb = -1.0f;
	
	// GETTER
	public static float getUss_f() throws NoSensorDataAvailableException{
		if (uss_f == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("front Sensor");
		}
		return uss_f;
	}

	public static float getUss_rf() throws NoSensorDataAvailableException {
		if (uss_rf == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("rightUpper Sensor");
		}
		return uss_rf;
	}
	
	public static float getUss_rb() throws NoSensorDataAvailableException {
		if (uss_rb == -1.0f) {
			throw new exceptions.NoSensorDataAvailableException("rightDowner Sensor");
		}
		return uss_rb;
	}
	
	// SETTER
	public static void setUss_f(float uss_f) {
		SensorDataAccess.uss_f = uss_f;
	}
	public static void setUss_rf(float uss_rf) {
		SensorDataAccess.uss_rf = uss_rf;
	}
	public static void setUss_rb(float uss_rb) {
		SensorDataAccess.uss_rb = uss_rb;
	}
	
}
