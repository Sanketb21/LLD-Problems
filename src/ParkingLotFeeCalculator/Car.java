package ParkingLotFeeCalculator;

public class Car extends Vehicle{
	private static final double HOURLY_RATE = 50;
	
	public Car(String numberPlate) {
		super(numberPlate);
	}
	
	@Override
	public double getHourlyRate() {
		return HOURLY_RATE;
	}
}
