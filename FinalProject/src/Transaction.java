import java.time.LocalDateTime;

/**
 * Transaction class to represent transactions
 * 
 * @author white
 */
public class Transaction {
	private int transaction_id;
    private String item_name;
    private int user_id;     
    private String SKU;
    private int quantity;
    private int total_price;
    private TaxBracket tax_bracket;
    private LocalDateTime transaction_date;
    
    Transaction(int transaction_id,
    		String item_name,
    		int user_id,
    		String SKU,
    		int quantity,
    		int price,
    		TaxBracket tax_bracket,
    		LocalDateTime transaction_date) {
    	this.transaction_id = transaction_id;
    	this.item_name = item_name;
    	this.user_id = user_id;
    	this.SKU = SKU;
    	this.quantity = quantity;
    	this.setTotal_price(price);
    	this.tax_bracket = tax_bracket;
    	this.transaction_date = transaction_date;
    }
    
	public String getTotal_price() {
		return String.format("$%.2f", (total_price / 100.0));
	}
    
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public TaxBracket getTax_bracket() {
		return tax_bracket;
	}
	public void setTax_bracket(TaxBracket tax_bracket) {
		this.tax_bracket = tax_bracket;
	}
	public LocalDateTime getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDateTime transaction_date) {
		this.transaction_date = transaction_date;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
}
