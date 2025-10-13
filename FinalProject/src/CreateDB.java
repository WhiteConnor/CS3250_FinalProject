import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CreateDB is a static class used to re-initialize the database
 * Assumes that the database has already been initialized and default user added
 * 
 * @author white
 */

public class CreateDB {
	final private static String url = "jdbc:mysql://localhost:3306/cs3250";
	final private static String username = "default";
	final private static String password = "password1234";
	private static Connection connection;
	static Statement stmt;
	
	/**
	 * Constructor not used as everything is static
	 */
	public CreateDB() {}
	
	/**
	 * Handles initialization of database including resetting, creating tables, inserting seed data
	 */
	public static void initialize() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Connection Successful");
		    stmt = connection.createStatement();
		    reset();
		    createTables();
		    insertSeedData();
		    
		} catch (SQLException e) {
		    throw new IllegalStateException("Connection Error", e);
		}
	}
	
	/**
	 * Drops database and creates a new one
	 * 
	 * @throws SQLException
	 */
	private static void reset() throws SQLException {
		System.out.println("Resetting Database cs3250...");
		try {
			String sql = "DROP DATABASE cs3250;";
		    stmt.execute(sql);
		    sql = "CREATE DATABASE cs3250;";
		    stmt.execute(sql);
		    sql = "USE cs3250;";
		    stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Reset Failed");
			throw new SQLException(e);
		}
	    
	}
	
	/**
	 * Calls all table creation methods
	 * 
	 * @throws SQLException
	 */
	private static void createTables() throws SQLException {
		System.out.println("Creating tables...");
		try {
			createUsersTable();
			createItemsTable();
		} catch (SQLException e) {
			System.out.println("Tables creation failed");
			throw new SQLException(e);
		}
	}
	
	/**
	 * Table creation method to create users table
	 * @throws SQLException
	 */
	private static void createUsersTable() throws SQLException {
		String sql = "CREATE TABLE `users` (\r\n"
				+ "  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `first_name` VARCHAR(100) NOT NULL,\r\n"
				+ "  `last_name` VARCHAR(100) NOT NULL,\r\n"
				+ "  `birth_date` DATE NOT NULL,\r\n"
				+ "  `username` VARCHAR(50) NOT NULL UNIQUE,\r\n"
				+ "  `salt` VARCHAR(24) NOT NULL,\r\n"
				+ "  `hashed_password` VARCHAR(128) NOT NULL,\r\n"
				+ "  `sex` ENUM('MALE','FEMALE') NOT NULL,\r\n"
				+ "  `role` ENUM('ADMIN','MANAGER','EMPLOYEE','CUSTOMER') NOT NULL,\r\n"
				+ "  PRIMARY KEY (`user_id`)\r\n"
				+ ");";
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Table creation failed - users");
			throw new SQLException(e);
		}
	}
	
	/**
	 * Table creation method to create items table
	 * @throws SQLException
	 */
	private static void createItemsTable() throws SQLException {
		// Copilot used on 10/12/2025, wrote query based on requirements, eg price (int in cents), weight_kg...
		// Query modified after generation to better fit requirements
		String sql = "CREATE TABLE `items` (\r\n"
				+ "  `item_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `item_name` VARCHAR(100) NOT NULL,\r\n"
				+ "  `user_id` INT UNSIGNED,"
				+ "  `description` VARCHAR(255) NOT NULL,\r\n"
				+ "  `weight_kg` FLOAT NOT NULL,\r\n"
				+ "  `price` INT UNSIGNED NOT NULL,\r\n"
				+ "  `tax_bracket` ENUM(\r\n"
				+ "    'FOOD',\r\n"
				+ "    'MEDICAL',\r\n"
				+ "    'GENERAL'\r\n"
				+ "  ) NOT NULL,\r\n"
				+ "  `expiration_time` INT UNSIGNED NOT NULL,\r\n"
				+ "  `SKU` VARCHAR(64) NOT NULL UNIQUE,\r\n"
				+ "  `category` ENUM(\r\n"
				+ "    'GROCERY',\r\n"
				+ "    'BEVERAGE',\r\n"
				+ "    'PHARMACEUTICAL',\r\n"
				+ "    'APPAREL',\r\n"
				+ "    'FOOTWEAR',\r\n"
				+ "    'TOOLS',\r\n"
				+ "    'ELECTRONICS',\r\n"
				+ "    'FIREARMS',\r\n"
				+ "    'AMMUNITION',\r\n"
				+ "    'FURNITURE',\r\n"
				+ "    'TOYS',\r\n"
				+ "    'AUTOMOTIVE',\r\n"
				+ "    'SPORTING_GOODS',\r\n"
				+ "    'JEWELRY',\r\n"
				+ "    'COSMETICS',\r\n"
				+ "    'PET_SUPPLIES',\r\n"
				+ "    'CLEANING',\r\n"
				+ "    'STATIONERY',\r\n"
				+ "    'GARDENING',\r\n"
				+ "    'INDUSTRIAL',\r\n"
				+ "    'OFFICE_EQUIPMENT',\r\n"
				+ "    'BABY_PRODUCTS',\r\n"
				+ "    'BOOKS',\r\n"
				+ "    'MUSIC',\r\n"
				+ "    'VIDEO_GAMES',\r\n"
				+ "    'LUXURY'\r\n"
				+ "  ) NOT NULL,\r\n"
				+ "  `units_per_bin` INT UNSIGNED NOT NULL,\r\n"
				+ "  `date_added` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ "  `last_updated` DATETIME DEFAULT NULL,\r\n"
				+ "  `min_temp` INT DEFAULT NULL,\r\n"
				+ "  `max_temp` INT DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`item_id`),\r\n"
				+ "  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)"
				+ "  ON UPDATE CASCADE"
				+ "  ON DELETE SET NULL"
				+ ");\r\n"
				+ "";
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Table creation failed - items");
			throw new SQLException(e);
		}
	}
	
	/**
	 * Calls all seed insertion methods
	 * 
	 * @throws SQLException
	 */
	private static void insertSeedData() throws SQLException {
		System.out.println("Inserting seed data...");
		try {
			addUsers();
		} catch (SQLException e) {
			System.out.println("Failed to insert seed data");
			throw new SQLException(e);
		}
	}
	
	/**
	 * Inserts seed users into users table
	 * 
	 * @throws SQLException
	 */
	private static void addUsers() throws SQLException {
		// Seed data created with Copilot on 9/26, prompt: 
		/* create 5 rows of seed data (don't worry about salts and hashed passwords,
		 * I will implement those later: CREATE TABLE users ( user_id INT
		 * UNSIGNED NOT NULL AUTO_INCREMENT, first_name VARCHAR(100) 
		 * NOT NULL, last_name VARCHAR(100) NOT NULL, birth_date DATE NOT
		 * NULL, username VARCHAR(50) NOT NULL, salt VARCHAR(16) NOT NULL,
		 * hashed_password VARCHAR(128) NOT NULL, sex ENUM('MALE','FEMALE')
		 * NOT NULL, role ENUM('ADMIN','MANAGER','EMPLOYEE','CUSTOMER'),
		 * PRIMARY KEY (user_id) );
		 * user - pass: generated using program on 10/05/2025 and added for testing
		 */

		String sql = "INSERT INTO `users` (\r\n"
				+ "  `first_name`, `last_name`, `birth_date`, `username`, `salt`, `hashed_password`, `sex`, `role`\r\n"
				+ ") VALUES\r\n"
				+ "('Alice', 'Johnson', '1990-04-15', 'alicej', 'salt123456789012', 'hashed_pw_1', 'FEMALE', 'ADMIN'),\r\n"
				+ "('Bob', 'Smith', '1985-09-22', 'bobsmith', 'salt123456789013', 'hashed_pw_2', 'MALE', 'MANAGER'),\r\n"
				+ "('Carol', 'Nguyen', '1992-12-03', 'caroln', 'salt123456789014', 'hashed_pw_3', 'FEMALE', 'EMPLOYEE'),\r\n"
				+ "('David', 'Lee', '1998-07-30', 'dlee98', 'salt123456789015', 'hashed_pw_4', 'MALE', 'CUSTOMER'),\r\n"
				+ "('Eve', 'Martinez', '1995-01-10', 'evem', 'salt123456789016', 'hashed_pw_5', 'FEMALE', 'EMPLOYEE'),\r\n"
				+ "('Test', 'Test', '2025-10-01', 'user', 'tz19WOXoaPqUUdAJYxxtGg==', '10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=', 'MALE', 'ADMIN');";
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Table creation failed - users");
			throw new SQLException(e);
		}
	}
	
}
