package BankAccountTransactionEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
	private final long id;
	private double balance;
	private final List<Transaction> history = new CopyOnWriteArrayList<>();
	private final ReentrantLock lock = new ReentrantLock();
	
	public BankAccount(long id, double initialBalance) {
		this.id = id;
		this.balance = initialBalance;
	}
	
	public boolean withdraw(double amount) {
		lock.lock(); // Ensure only one thread changes balance at a time
		try {
			if(balance >= amount) {
				balance -= amount;
				history.add(new Transaction("Withdrawal", amount, System.currentTimeMillis()));
				return true;
			}
			return false;
		}
		finally {
			lock.unlock();
		}
	}
	
	public void deposit(double amount) {
		lock.lock();
		try {
			balance += amount;
			history.add(new Transaction("Deposit", amount, System.currentTimeMillis()));
		}
		finally{
			lock.unlock();
		}
	}
	
	public List<Transaction> getTransactions(long accountId){
		return new ArrayList<>(history);
	}

}
