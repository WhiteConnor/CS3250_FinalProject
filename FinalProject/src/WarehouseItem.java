import java.time.LocalDateTime;

public class WarehouseItem {
	private InventoryItem type;
	private LocalDateTime dateReceived;
	private int lotCode;
	private int stock;
	
	WarehouseItem () {}
	WarehouseItem(InventoryItem type, LocalDateTime dateReceived, int lotCode, int stock) {
		this.type = type;
		this.dateReceived = dateReceived;
		this.lotCode = lotCode;
		this.stock = stock;
	}
	public InventoryItem getType() {
		return type;
	}

	public void setType(InventoryItem type) {
		this.type = type;
	}

	public LocalDateTime getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(LocalDateTime dateReceived) {
		this.dateReceived = dateReceived;
	}

	public int getLotCode() {
		return lotCode;
	}

	public void setLotCode(int lotCode) {
		this.lotCode = lotCode;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
