package VehicleRentalSystem;

public class Vehicle {
	private String vehicleId;
	private String type;
	private boolean isReserved;
	private double pricePerDay;
	
	public Vehicle(String vehicleId, String type, double pricePerDay) {
		this.vehicleId = vehicleId;
		this.type = type;
		this.pricePerDay = pricePerDay;
		this.isReserved = false;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	
	public String getType() {
		return type;
	}
	
	public double getPricePerDay() {
		return pricePerDay;
	}
	
	public boolean getIsReserved() {
		return isReserved;
	}
	
	public void setIsReserved(boolean flag) {
		isReserved = flag;
	}
	
}
