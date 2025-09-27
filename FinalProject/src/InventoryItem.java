/**
 * InventoryItem class represents an inventory item
 * 
 * @author white
 */
public abstract class InventoryItem {
	private int price;
	private float taxRate;
	private int taxes;
	private String itemName;
	
	/**
	 * Default constructor
	 */
	InventoryItem(){}
	
	/**
	 * Create an item with values to initialize
	 * @param itemName String: Name of item
	 * @param price Integer: Price per item in cents
	 * @param taxRate Float: Tax rate for item
	 * @param taxes Taxes: Calculated taxes
	 */
	InventoryItem(String itemName, int price, float taxRate, int taxes){
		setItemName(itemName);
		setPrice(price);
		setTaxRate(taxRate);
		setTaxes(taxes);
	}
	
	/**
	 * Get price of item in cents
	 * @return price Integer: Price of item in cents
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Set price of item in cents
	 * @param price Integer: Price of item in cents
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * Get tax rate
	 * 
	 * @return taxRate Float: tax rate
	 */
	public float getTaxRate() {
		return taxRate;
	}
	
	/**
	 * Set tax rate
	 * 
	 * @param taxRate Float: Item tax rate
	 */
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	
	/**
	 * Get tax amount
	 * 
	 * @return taxes Integer: Tax value in cents
	 */
	public int getTaxes() {
		return taxes;
	}
	
	/**
	 * Set tax amount
	 * 
	 * @param taxes Integer: Tax value in cents
	 */
	public void setTaxes(int taxes) {
		this.taxes = taxes;
	}
	
	/**
	 * Get item name
	 * 
	 * @return itemName String: Item name
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * Set item name
	 * 
	 * @param itemName String: item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
