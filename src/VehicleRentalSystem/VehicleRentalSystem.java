package VehicleRentalSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleRentalSystem {
	private Map<String, Vehicle> vMap = new HashMap<>();
	private List<RentalRecord> history = new ArrayList<>();
	
	public void addVehicle(Vehicle v) {
		vMap.put(v.getVehicleId(), v);
	}
	
	public double calculateRentalCost(String vehicleId, int days) {
		try {
			Vehicle v = vMap.get(vehicleId);
			
			if(v == null || v.getIsReserved()) {
				throw new IllegalArgumentException("Cannot calculate rental cost for this vehicle");
			}
			
			double rentalCost = v.getPricePerDay() * days;
			
			history.add(new RentalRecord(vehicleId, days, rentalCost));
			
			return rentalCost;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return 0.0;
	}
	
	public boolean reserveVehicle(String vehicleId) {
		try {
			Vehicle v = vMap.get(vehicleId);
			
			if(v == null || v.getIsReserved()) {
				throw new IllegalArgumentException("This vehicle is not available or is already reserved");
			}
			
			if(!v.getIsReserved()) {
				v.setIsReserved(true);
				return true;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public List<RentalRecord> getRentalHistory(String vehicleId){
		List<RentalRecord> rentalHistory = history.stream().
				filter(record -> record.vehicleId.equals(vehicleId)).collect(Collectors.toList());
		return rentalHistory;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VehicleRentalSystem vrs = new VehicleRentalSystem();
		
		vrs.addVehicle(new Vehicle("V001", "Car", 500));
		vrs.addVehicle(new Vehicle("V002", "Bike", 200));
		vrs.addVehicle(new Vehicle("V003", "Bike", 200));
		vrs.addVehicle(new Vehicle("V004", "Car", 200));
		vrs.addVehicle(new Vehicle("V005", "Bike", 200));
		
		double rentalCost1 = vrs.calculateRentalCost("V001", 4);
		System.out.println("Rental Cost of Vehicle V001 is " + rentalCost1 );
		
		double rentalCost2 = vrs.calculateRentalCost("V003", 15);
		System.out.println("Rental Cost of Vehicle V003 is " + rentalCost2 );

		vrs.reserveVehicle("V003");
		System.out.println("Vehicle Reserved V003");
		double rentalCostReservedVehicle = vrs.calculateRentalCost("V003", 5);
		System.out.println(rentalCostReservedVehicle);
		
		System.out.println("History count for V001:");
		System.out.println(vrs.getRentalHistory("V001").size());
		
		
	}

}
