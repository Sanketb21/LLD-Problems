package OnlineFoodOrderingSystem;

import java.util.*;
import java.util.stream.Collectors;

public class FoodOrderingSystem {
	private static final double DISCOUNT = 0.10;
	
	public double calculateBill(List<Item> items) {
		double total = items.stream().mapToDouble(i ->
		{if(i.getCategory().equalsIgnoreCase("Drink")) {return i.getPrice() + 20;} return i.getPrice();}).sum();
		
		if(total > 1000) {
			total = total - (total*DISCOUNT);
		}
		
		return total;
	}
	
	public Map<String, Double> categoryWiseTotal(List<Item> items){
		Map<String, Double> categoryTotal = items.stream().
				collect(Collectors.groupingBy(Item :: getCategory, Collectors.summingDouble(Item :: getPrice)));
		
		return categoryTotal;
	}
	
	
	public static void main(String[] args) {
		FoodOrderingSystem fs = new FoodOrderingSystem();
		
		List<Item> items = new ArrayList<>();
		
		items.add(new Item("Biryani", 70D, "Food"));
		items.add(new Item("Coke", 40D, "Drink"));
		items.add(new Item("Murg Musallam", 300D, "Food"));
		items.add(new Item("Custard", 80D, "Sweet"));
		items.add(new Item("Roti", 120D, "Food"));
		items.add(new Item("Budweiser", 2000D, "Drink"));
		
		double totalFinalBill = fs.calculateBill(items);
		System.out.println("Total Final Bill is :" + totalFinalBill);
		
		Map<String, Double> categoryTotal = fs.categoryWiseTotal(items);
		System.out.println("Category Wise Total is : " + categoryTotal);
	}
}
