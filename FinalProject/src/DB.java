import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DB class is used to handle database actions not related to initialization.
 */
public class DB {
	final private String url = "jdbc:mysql://localhost:3306/cs3250";
	final private String username = "default";
	final private String password = "password1234";
	private Connection connection;
	static Statement stmt;
	
	/**
	 * Constructs a DB and obtains connection
	 */
	public DB() {
		try {
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
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
	
	/**
	 * Login user, return getHashedPass
	 * @param user String: Username
	 * @return String: getHashedPass
	 * @throws SQLException 
	 */
	public String getHashedPass(String user) {
		String sql = "SELECT hashed_password FROM users "
				+ 	 "WHERE username = ?;";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, user);
		    
		    ResultSet results = pstmt.executeQuery();
		    if (results.next()) {
		    	return results.getString("hashed_password");
		    } else 
		    	return null;
				
		} catch (SQLException e) {
		    System.out.println("Query Failed = username hashed_password from users login");
		    e.printStackTrace();
		    return null;
		}
	}
	
	/**
	 * Get the salt for user verification
	 * @param user String: Username
	 * @return String: salt
	 * @throws SQLException
	 */
	public String getSalt(String user) throws SQLException {
		String sql = "SELECT salt FROM users "
				+	 "WHERE username = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  user);
			
			ResultSet results = pstmt.executeQuery();
			if (results.next())
				return results.getString("salt");
			else
				return null;
		} catch (SQLException e) {
			System.out.println("Query Failed - getSalt");
			throw e;
		}
	}
	
	/**
	 * insert user into users
	 * Copilot used to learn PreparedStatement
	 * @param first_name String: first name (100)
	 * @param last_name String: last name (100)
	 * @param birth_date LocalDate: birth date
	 * @param username String: username (50)
	 * @param salt String: salt (16)
	 * @param hashed_password String: hashed password (128)
	 * @param sex Enum: sex
	 * @param role Enum: role
	 * @throws SQLException
	 */
	public void addUser(
			String first_name,
			String last_name,
			String email,
			LocalDate birth_date,
			String username,
			String salt,
			String hashed_password,
			Sex sex,
			Role role
			) throws SQLException {
		String sql = "INSERT INTO users (first_name, last_name, birth_date, username, salt, hashed_password, sex, role, email) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, first_name);
		    pstmt.setString(2, last_name);
		    pstmt.setDate(3, java.sql.Date.valueOf(birth_date)); // Copilot used to insert date
		    pstmt.setString(4, username);
		    pstmt.setString(5, salt);
		    pstmt.setString(6, hashed_password);
		    pstmt.setString(7, sex.name()); // Copilot used to get string of enums
		    pstmt.setString(8, role.name());
		    pstmt.setString(9, email);

		    pstmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("User insertion failed");
		    throw new SQLException(e);
		}
	}
	
	
	/**
	 * insert item into items
	 * @param itemName String (100)
	 * @param userID int: current user
	 * @param description String (255)
	 * @param weightKG float
	 * @param price int: in cents
	 * @param taxBracket TaxBracket enum
	 * @param expirationTime int: days after receipt until expire
	 * @param SKU String (64)
	 * @param category Category enum
	 * @param unitsPerBin int: number of units that fit in a single bin
	 * @param minTemp int: min temp
	 * @param maxTemp int: max temp
	 * @throws SQLException
	 */
	public void addItem(
			String itemName,
			int userID,
			String description,
			float weightKG,
			int price,
			TaxBracket taxBracket,
			int expirationTime,
			String SKU,
			Category category,
			int unitsPerBin,
			int minTemp,
			int maxTemp
			) throws SQLException {
		String sql = "INSERT INTO items("
				+ "item_name, user_id, description, weight_kg, price, tax_bracket, expiration_time, SKU, "
				+ "category, units_per_bin, min_temp, max_temp) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, itemName);
		    pstmt.setInt(2, userID);
		    pstmt.setString(3, description);
		    pstmt.setFloat(4, weightKG);
		    pstmt.setInt(5, price);
		    pstmt.setString(6, taxBracket.name());
		    pstmt.setInt(7, expirationTime);
		    pstmt.setString(8, SKU);
		    pstmt.setString(9, category.name());
		    pstmt.setInt(10, unitsPerBin);
		    pstmt.setInt(11, minTemp);
		    pstmt.setInt(12, maxTemp);

		    pstmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("User insertion failed");
		    throw new SQLException(e);
		}
	}
	
	/**
	 * insert item into items, no temperature requirements
	 * @param itemName String (100)
	 * @param userID int: current user
	 * @param description String (255)
	 * @param weightKG float
	 * @param price int: in cents
	 * @param taxBracket TaxBracket enum
	 * @param expirationTime int: days after receipt until expire
	 * @param SKU String (64)
	 * @param category Category enum
	 * @param unitsPerBin int: number of units that fit in a single bin
	 * @throws SQLException
	 */
	public void addItem(
			String itemName,
			int userID,
			String description,
			float weightKG,
			int price,
			TaxBracket taxBracket,
			int expirationTime,
			String SKU,
			Category category,
			int unitsPerBin
			) throws SQLException {
		String sql = "INSERT INTO items("
				+ "item_name, user_id, description, weight_kg, price, tax_bracket, expiration_time, SKU, "
				+ "category, units_per_bin) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, itemName);
		    pstmt.setInt(2, userID);
		    pstmt.setString(3, description);
		    pstmt.setFloat(4, weightKG);
		    pstmt.setInt(5, price);
		    pstmt.setString(6, taxBracket.name());
		    pstmt.setInt(7, expirationTime);
		    pstmt.setString(8, SKU);
		    pstmt.setString(9, category.name());
		    pstmt.setInt(10, unitsPerBin);

		    pstmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("User insertion failed");
		    throw new SQLException(e);
		}
	}
	
	/**
	 * Gets user item
	 * @param username String: unique username
	 * @return User user
	 * @throws SQLException
	 */
	public User getUser(String username) throws SQLException{
		String sql = "SELECT user_id FROM users "
				+	 "WHERE username = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  username);
			
			ResultSet results = pstmt.executeQuery();
			if (results.next())
				return new User(results.getInt("user_id"));
			else
				return null;
		} catch (SQLException e) {
			System.out.println("Query Failed - get user");
			throw e;
		}
	}
	
	public ArrayList<InventoryItem> getItems() throws SQLException {
		return getItems(50);
	}
	
	public ArrayList<InventoryItem> getItems(int limit) throws SQLException {
		ArrayList<InventoryItem> allItems = new ArrayList<InventoryItem>();
		String sql = "SELECT item_name,\r\n"
				+ "             user_id,\r\n"
				+ "				description,\r\n"
				+ "				weight_kg,\r\n"
				+ "				price,\r\n"
				+ "				tax_bracket,\r\n"
				+ "				expiration_time,\r\n"
				+ "				SKU,\r\n"
				+ "				category,\r\n"
				+ "				units_per_bin,\r\n"
				+ "				date_added,\r\n"
				+ "				last_updated,\r\n"
				+ "				min_temp,\r\n"
				+ "				max_temp FROM items LIMIT ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,  limit);
			
			ResultSet results = pstmt.executeQuery();
			while (results.next())
				allItems.add(new InventoryItem(
					    results.getString("item_name"),                 
					    results.getInt("user_id"),                      
					    results.getString("description"),               
					    results.getFloat("weight_kg"),                  
					    results.getInt("price"),                        
					    TaxBracket.valueOf(results.getString("tax_bracket")),
					    results.getInt("expiration_time"),              
					    results.getString("SKU"),                       
					    Category.valueOf(results.getString("category")),
					    results.getInt("units_per_bin"),
					    results.getTimestamp("date_added"),
					    results.getTimestamp("last_updated"),
					    results.getInt("min_temp"),						 
					    results.getInt("max_temp")
					));
			return allItems;
		} catch (SQLException e) {
			System.out.println("Query Failed - get Item");
			throw e;
		}
	}
	
	public ArrayList<InventoryItem> getItems(String searchTerm) throws SQLException {
		ArrayList<InventoryItem> allItems = new ArrayList<InventoryItem>();
		String sql = "SELECT item_name,\r\n"
		        + "             user_id,\r\n"
		        + "				description,\r\n"
		        + "				weight_kg,\r\n"
		        + "				price,\r\n"
		        + "				tax_bracket,\r\n"
		        + "				expiration_time,\r\n"
		        + "				SKU,\r\n"
		        + "				category,\r\n"
		        + "				units_per_bin,\r\n"
		        + "				date_added,\r\n"
		        + "				last_updated,\r\n"
		        + "				min_temp,\r\n"
		        + "				max_temp\r\n"
		        + "FROM items\r\n"
		        + "WHERE item_name LIKE ?\r\n"
		        + "LIMIT 50;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + searchTerm + "%");
			
			ResultSet results = pstmt.executeQuery();
			while (results.next())
				allItems.add(new InventoryItem(
					    results.getString("item_name"),                 
					    results.getInt("user_id"),                      
					    results.getString("description"),               
					    results.getFloat("weight_kg"),                  
					    results.getInt("price"),                        
					    TaxBracket.valueOf(results.getString("tax_bracket")),
					    results.getInt("expiration_time"),              
					    results.getString("SKU"),                       
					    Category.valueOf(results.getString("category")),
					    results.getInt("units_per_bin"),
					    results.getTimestamp("date_added"),
					    results.getTimestamp("last_updated"),
					    results.getInt("min_temp"),						 
					    results.getInt("max_temp")
					));
			return allItems;
		} catch (SQLException e) {
			System.out.println("Query Failed - get Item");
			throw e;
		}

		
	}
	/**
	 * Ensures that the email doesn't already exist
	 * @param email String: email to check
	 * @return Boolean: false if email already exists, true if email is available
	 */
	public Boolean verifyUserEmail(String email) {
		String sql = "SELECT email FROM users WHERE email = ?;";
				
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			
			ResultSet results = pstmt.executeQuery();
			if (results.next())
				return false;
			else
				return true;
		} catch (SQLException e) {
			System.out.println("Query Failed - verify user email");
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * Ensures that the username doesn't already exist
	 * @param username String: username to check
	 * @return Boolean: false if user already exists, true if username is available
	 */
	public Boolean verifyUsername(String username) {
		String sql = "SELECT username FROM users WHERE username = ?;";
				
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			
			ResultSet results = pstmt.executeQuery();
			if (results.next())
				return false;
			else
				return true;
		} catch (SQLException e) {
			System.out.println("Query Failed - verify username");
			e.printStackTrace();
			return true;
		}
	}
}
