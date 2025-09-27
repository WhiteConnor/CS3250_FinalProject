import java.time.LocalDateTime;

/**
 * WarehouseItem class to handle warehouse inventory
 */
public class WarehouseItem {
	private InventoryItem type;
	private LocalDateTime dateReceived;
	private String lotCode;
	private int stock;
	/**
	 * Default constructor
	 */
	WarehouseItem () {}
	/**
	 * Construct a WarehouseItem instance
	 * @param type InventoryItem: Item type
	 * @param dateReceived LocalDateTime: timestamp of receipt
	 * @param lotCode String: Lot code
	 * @param stock Integer: Stock count
	 */
	WarehouseItem(InventoryItem type, LocalDateTime dateReceived, String lotCode, int stock) {
		this.type = type;
		this.dateReceived = dateReceived;
		this.lotCode = lotCode;
		this.stock = stock;
	}
	/**
	 * Get item type
	 * @return type InventoryItem: associated item
	 */
	public InventoryItem getType() {
		return type;
	}
	/**
	 * Set item type
	 * @param type InventoryItem: associated item
	 */
	public void setType(InventoryItem type) {
		this.type = type;
	}

	/**
	 * Get date received
	 * @return dateReceived LocalDateTime: Date received
	 */
	public LocalDateTime getDateReceived() {
		return dateReceived;
	}

	/**
	 * Set received date
	 * @param dateReceived LocalDateTime: Date received
	 */
	public void setDateReceived(LocalDateTime dateReceived) {
		this.dateReceived = dateReceived;
	}

	/**
	 * Get lot code
	 * @return lotCode String: Lot code
	 */
	public String getLotCode() {
		return lotCode;
	}

	/**
	 * Set lot code
	 * @param lotCode String: Lot code
	 */
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	/**
	 * Get stock count
	 * @return stock Integer: Stock count
	 */
	public int getStock() {
		return stock;
	}
	/**
	 * Set stock count
	 * @param stock Integer: Stock count
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
