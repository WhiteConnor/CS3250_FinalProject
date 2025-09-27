import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Transaction class to represent transactions
 * 
 * @author white
 */
public abstract class Transaction {
	private LocalDateTime transactionTime;
	private User initializingUser;
	private int amountInCents;
	private int taxes;
	private ArrayList<InventoryItem> itemList = new ArrayList<InventoryItem>();
	private Transaction previousReceipt = null;
	/**
	 * Construct Transaction Item
	 */
	public Transaction() {}
	/**
	 * Return initializing user
	 */
	public User getInitializingUser() {
		return initializingUser;
	}
	/**
	 * Update initializing user
	 * @param initializingUser User: User creating transaction
	 */
	public void setInitializingUser(User initializingUser) {
		this.initializingUser = initializingUser;
	}
	/**
	 * Time of transaction
	 * @return transactionTime LocalDateTime: Time of transaction
	 */
	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}
	
	/**
	 * Set transaction time
	 * @param transactionTime LocalDateTime: Time of transaction
	 */
	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
	/**
	 * Get transaction taxes
	 * @return taxes Integer: taxes in cents
	 */
	public int getTaxes() {
		return taxes;
	}
	/**
	 * Get transaction amount in Cents
	 * @return amountInCents Integer: Transaction amount in cents
	 */
	public int getAmountInCents() {
		return amountInCents;
	}
	/**
	 * Calculate total taxes and amount for transaction
	 */
	public void calculateTotals() {
		amountInCents = 0;
		taxes = 0;
		for (InventoryItem item : itemList) {
			amountInCents += item.getPrice();
			taxes += item.getTaxes();
		}
	}
	/*
	 * Set amount in cents for transaction
	 */
	public void setAmountInCents(int amountInCents) {
		this.amountInCents = amountInCents;
	}
	/**
	 * Get all transaction items
	 * @return itemList ArrayList<InventoryItem>: List of items in transaction
	 */
	public ArrayList<InventoryItem> getItemList() {
		return itemList;
	}
	/**
	 * Add item to transaction
	 * @param item InventoryItem: Item to add to transaction
	 */
	public void addItem(InventoryItem item) {
		itemList.add(item);
		this.calculateTotals();
	}
	/**
	 * Previous receipt (if any)
	 * @return previousReceipt Transaction: Previously associated receipt
	 */
	public Transaction getPreviousReceipt() {
		return previousReceipt;
	}
	/**
	 * Add an associated receipt
	 * @param previousReceipt Transaction: Previously associated receipt
	 */
	public void setPreviousReceipt(Transaction previousReceipt) {
		this.previousReceipt = previousReceipt;
	}
}
