
public abstract class InventoryItem {
	private int price;
	private float taxRate;
	private int taxes;
	private String itemName;
	
	InventoryItem(){}
	InventoryItem(String itemName, int price, float taxRate, int taxes){
		this.itemName = itemName;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	public int getTaxes() {
		return taxes;
	}
	public void setTaxes(int taxes) {
		this.taxes = taxes;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void calculateTaxes() {
		
	}
	
}
