import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * DB class is used to handle database actions not related to initialization.
 */
public class DB {
	final private String url = "jdbc:mysql://localhost:3306/cs3250";
	final private String username = "default";
	final private String password = "password1234";
	private Connection connection;
	
	/**
	 * Constructs a DB and obtains connection
	 */
	public DB() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Connection Successful");
		} catch (SQLException e) {
		    throw new IllegalStateException("Connection Error", e);
		}
	}
	
	/**
	 * Insert a new warehouse receipt to database
	 * @param item String: Currently the item name, will be item_id
	 * @param receiptDate LocalDate: Date of item receipt
	 * @param lotCode String: Associated Lot Code
	 * @param stockCount String: Count of items received
	 */
	public void insertNewWarehouseReceipt(
			String item,
			LocalDate receiptDate,
			String lotCode,
			String stockCount) {
		System.out.println("Item name: " + item);
		System.out.println("Date: " + receiptDate);
		System.out.println("Lot Code: " + lotCode);
		System.out.println("Stock Count: " + stockCount);
		
	}
}
