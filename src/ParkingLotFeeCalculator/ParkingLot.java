package ParkingLotFeeCalculator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParkingLot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ParkingLotSystem parkingLot = new ParkingLotSystem();
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		executor.submit(() -> {
			Vehicle car1 = new Car("MH 03 AB 1234");
			parkingLot.parkVehicle(car1);
			System.out.println("Parked Car: " + car1.getNumberPlate() + " at " + car1.getEntryTime());
		});
		
		executor.submit(() -> {
			Vehicle bike1 = new Bike("MH 02 HJ 1112");
			parkingLot.parkVehicle(bike1);
			System.out.println("Parked Bike: " + bike1.getNumberPlate() + " at " + bike1.getEntryTime());
		});
		
		executor.submit(() -> {
			Vehicle car2 = new Car("MH 01 BG 1268");
			parkingLot.parkVehicle(car2);
			System.out.println("Parked Car: " + car2.getNumberPlate() + " at " + car2.getEntryTime());
		});
		
		executor.submit(() -> {
			Vehicle bike2 = new Bike("MH 43 VS 8897");
			parkingLot.parkVehicle(bike2);
			System.out.println("Parked Bike: " + bike2.getNumberPlate() + " at " + bike2.getEntryTime());
		});
		
		executor.submit(() -> {
			Vehicle car3 = new Car("MH 04 VS 2127");
			parkingLot.parkVehicle(car3);
			System.out.println("Parked Car: " + car3.getNumberPlate() + " at " + car3.getEntryTime());
		});
		
		executor.submit(() -> {
			Vehicle bike3 = new Bike("MH 12 IU 7070");
			parkingLot.parkVehicle(bike3);
			System.out.println("Parked Bike: " + bike3.getNumberPlate() + " at " + bike3.getEntryTime());
		});
		
		executor.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				parkingLot.unparkVehicle("MH 02 HJ 1112");
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		executor.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				parkingLot.unparkVehicle("MH 04 VS 2127");
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		executor.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				parkingLot.unparkVehicle("MH 14 AA 6969");
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Currently Parked Cars");
		List<Vehicle> cars = parkingLot.getParkedVehiclesByType(Car.class);
		cars.forEach(v -> System.out.println("Car " + v.getNumberPlate() + " parked at " + v.getEntryTime()));
		
		System.out.println("Currently Parked Bikes");
		List<Vehicle> bikes = parkingLot.getParkedVehiclesByType(Bike.class);
		bikes.forEach(v -> System.out.println("Bikes " + v.getNumberPlate() + " parked at " + v.getEntryTime()));
	}

}
