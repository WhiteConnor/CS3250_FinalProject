import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Transaction {
	private LocalDateTime transactionTime;
	private User initializingUser;
	private int amountInCents;
	private int taxes;
	private ArrayList<InventoryItem> itemList = new ArrayList<InventoryItem>();
	private Transaction previousReceipt = null;
	
	
	public User getInitializingUser() {
		return initializingUser;
	}
	public void setInitializingUser(User initializingUser) {
		this.initializingUser = initializingUser;
	}
	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
	public int getTaxes() {
		return taxes;
	}
	public int getAmountInCents() {
		return amountInCents;
	}
	public void calculateTotals() {
		amountInCents = 0;
		taxes = 0;
		for (InventoryItem item : itemList) {
			amountInCents += item.getPrice();
			taxes += item.getTaxes();
		}
	}
	public void setAmountInCents(int amountInCents) {
		this.amountInCents = amountInCents;
	}
	public ArrayList<InventoryItem> getItemList() {
		return itemList;
	}
	public void addItem(InventoryItem item) {
		itemList.add(item);
		this.calculateTotals();
	}
	public Transaction getPreviousReceipt() {
		return previousReceipt;
	}
	public void setPreviousReceipt(Transaction previousReceipt) {
		this.previousReceipt = previousReceipt;
	}
}
