import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
			LocalDate birth_date,
			String username,
			String salt,
			String hashed_password,
			Sex sex,
			Role role
			) throws SQLException {
		String sql = "INSERT INTO users (first_name, last_name, birth_date, username, salt, hashed_password, sex, role) "
		           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

		    pstmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("User insertion failed");
		    throw new SQLException(e);
		}

	}
}
