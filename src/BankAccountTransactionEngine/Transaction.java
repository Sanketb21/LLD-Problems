package BankAccountTransactionEngine;

public class Transaction {
	private final String type;
	private final double amount;
	private final long timestamp;
	
	public Transaction(String type, double amount, long timestamp) {
		this.type = type;
		this.amount = amount;
		this.timestamp = timestamp;
	}
	
	public String getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public long getTimeStamp() {
		return timestamp;
	}
	
}
