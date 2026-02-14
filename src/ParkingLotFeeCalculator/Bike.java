package ParkingLotFeeCalculator;

public class Bike extends Vehicle{
	private static final double HOURLY_RATE = 20;
	
	public Bike(String numberPlate) {
		super(numberPlate);
	}
	
	@Override
	public double getHourlyRate() {
		return HOURLY_RATE;
	}
}
