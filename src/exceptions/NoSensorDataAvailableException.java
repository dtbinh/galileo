package exceptions;

public class NoSensorDataAvailableException extends Exception{
	
	public String sensorString;
	
	public NoSensorDataAvailableException(String sensorString) {
		this.sensorString = sensorString;
	}

}
