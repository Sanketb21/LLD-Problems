package VehicleRentalSystem;

public class RentalRecord {
	String vehicleId;
	int daysRented;
	double rentingCost;
	
	public RentalRecord(String vehicleId, int daysRented, double rentingCost) {
		this.vehicleId = vehicleId;
		this.daysRented = daysRented;
		this.rentingCost = rentingCost;
	}
	
}
