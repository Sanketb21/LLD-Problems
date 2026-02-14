package ParkingLotFeeCalculator;

import java.time.LocalDateTime;

public abstract class Vehicle {
	private final String numberPlate;
	private final LocalDateTime entryTime;
	
	public Vehicle(String numberPlate) {
		this.numberPlate = numberPlate;
		this.entryTime = LocalDateTime.now();
	}
	
	public String getNumberPlate() {
		return numberPlate;
	}

	public LocalDateTime getEntryTime() {
		return entryTime;
	}
	
	public abstract double getHourlyRate();

	@Override
	public String toString() {
		return "Vehicle [numberPlate=" + numberPlate + ", entryTime=" + entryTime + "]";
	}
	
}
