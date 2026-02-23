package BankAccountTransactionEngine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankEngine {
	
	private final Map<Long, BankAccount> accounts = new ConcurrentHashMap<>();
	
	public void createAccount(long id, double initialBalance) {
		accounts.put(id, new BankAccount(id, initialBalance));
	}
	
	public boolean withdraw(long id, double amount) {
		BankAccount acc = accounts.get(id);
		return accounts.get(id) != null && acc.withdraw(amount);
	}
	
	public void deposit(long id, double amount) {
		BankAccount acc = accounts.get(id);
		if(acc != null) acc.deposit(amount);
	}
	
	public double getTotalDebits(long id) {
		BankAccount acc = accounts.get(id);
		if(acc == null) return 0.0;
		
		return acc.getTransactions(id).stream().filter(t -> t.getType().equalsIgnoreCase("Withdrawal"))
											.mapToDouble(Transaction::getAmount).sum();
	}
	

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		BankEngine engine = new BankEngine();
		
		engine.createAccount(111, 1000);
		engine.createAccount(112, 100);
		engine.createAccount(113, 2000);
		engine.createAccount(114, 10000);
		engine.createAccount(115, 100000);
		
		int threads = 100;
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		CountDownLatch latch = new CountDownLatch(1);
		
		for(int i = 0; i < threads; i++) {
			executor.execute(() -> {
				try {
					latch.await();
					engine.withdraw(115, 10);
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		
		System.out.println("Launching 100 simultaneous withdrawals...");
		latch.countDown();
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		
		double debits = engine.getTotalDebits(115);
		System.out.println("Total Successful Withdrawals : Rs" + debits);
		
		if(debits > 10000) {
			System.out.println("CRITICAL ERROR: Overdraft occurred!");
		}
		else {
			System.out.println("Logic Correct: No overdraft possible.");
		}
		
	}

}
