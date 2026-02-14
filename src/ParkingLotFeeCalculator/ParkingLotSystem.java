package ParkingLotFeeCalculator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingLotSystem {
	private final Map<String, Vehicle> vehicleMap = new ConcurrentHashMap<>();
	
	public void parkVehicle(Vehicle v) {
		vehicleMap.put(v.getNumberPlate(), v);
	}
	
	public double calculateFee(String numberPlate) {
		if(!vehicleMap.containsKey(numberPlate)) {
			throw new NoSuchElementException("No such vehicle parked in Parking Lot.");
		}
		
		Vehicle v = vehicleMap.get(numberPlate);
		
		Duration duration = Duration.between(v.getEntryTime(), LocalDateTime.now());
		long hours = duration.toHours();
		
		double fees = v.getHourlyRate() * (hours > 1 ? hours : 1);
		if(hours > 10) {
			fees = fees - 0.15*fees;
		}
		return fees;
	}
	
	public void unparkVehicle(String numberPlate) {
		try {
			if(!vehicleMap.containsKey(numberPlate)) {
				throw new NoSuchElementException("No such vehicle parked in Parking Lot.");
			}
			else {
				double fees = calculateFee(numberPlate);
				vehicleMap.remove(numberPlate);
				System.out.println("Your vehicle has been unparked. Your fees is : " + fees);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<Vehicle> getParkedVehiclesByType(Class<? extends Vehicle> type) {
	    return vehicleMap.values().stream()
	            .filter(v -> type.isInstance(v)) // filter by type (Car, Bike, etc.)
	            .sorted(Comparator.comparing(Vehicle::getEntryTime)) // sort by entry time
	            .collect(Collectors.toList());
	}
	
	
}
