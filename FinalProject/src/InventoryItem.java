import java.sql.Timestamp;

/**
 * InventoryItem class represents an inventory item
 * 
 * @author white
 */
public class InventoryItem {
	private String itemName;
	private int userID;
	private String description;
	private float weightKG;
	private int price;
	private int inventory;
	private TaxBracket taxBracket;
	private int expirationTime;
	private String SKU;
	private Category category;
	private int unitsPerBin;
	private Timestamp dateAdded;
	private Timestamp lastUpdated;
	private int minTemp;
	private int maxTemp;
	
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
	InventoryItem(String itemName, int userID, String description, float weightKG, int price, int inventory,
            TaxBracket taxBracket, int expirationTime, String SKU, Category category,
            int unitsPerBin, Timestamp dateAdded, Timestamp lastUpdated, int minTemp, int maxTemp) {
		  this.itemName = itemName;
		  this.userID = userID;
		  this.description = description;
		  this.weightKG = weightKG;
		  this.price = price;
		  this.inventory = inventory;
		  this.taxBracket = taxBracket;
		  this.expirationTime = expirationTime;
		  this.SKU = SKU;
		  this.category = category;
		  this.unitsPerBin = unitsPerBin;
		  this.setDateAdded(dateAdded);
		  this.setLastUpdated(lastUpdated);
		  this.setMinTemp(minTemp);
		  this.setMaxTemp(maxTemp);
  // Optionally set dateAdded and lastUpdated if you have fields for them
}
	
	public TaxBracket getTaxBracket() {
		return this.taxBracket;
	}
	
	public void setTaxBracket(TaxBracket tb) {
		this.taxBracket = tb;
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

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getWeightKG() {
		return weightKG;
	}

	public void setWeightKG(float weightKG) {
		this.weightKG = weightKG;
	}

	public int getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(int expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getUnitsPerBin() {
		return unitsPerBin;
	}

	public void setUnitsPerBin(int unitsPerBin) {
		this.unitsPerBin = unitsPerBin;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
}
