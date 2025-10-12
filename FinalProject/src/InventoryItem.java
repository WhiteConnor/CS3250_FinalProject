/**
 * InventoryItem class represents an inventory item
 * 
 * @author white
 */
public abstract class InventoryItem {
	private int price;
	private TaxBracket taxBracket;
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
	InventoryItem(String itemName, int price, TaxBracket _taxBracket){
		setItemName(itemName);
		setPrice(price);
		setTaxes(taxBracket);
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
		return taxBracket.getRate();
	}
	
	
	/**
	 * Get tax amount
	 * 
	 * @return taxes Integer: Tax value in cents
	 */
	public int getTaxes() {
		return getTotalPrice() - getPrice();
	}
	
	/**
	 * Get total price
	 * 
	 * @return price Integer: Total price with taxes in cents
	 */
	public int getTotalPrice() {
		return Math.round(getTaxRate() * getPrice());
	}
	
	/**
	 * Set tax bracket
	 * 
	 * @param taxes TaxBracket: 
	 */
	public void setTaxes(TaxBracket _taxBracket) {
		this.taxBracket = _taxBracket;
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
