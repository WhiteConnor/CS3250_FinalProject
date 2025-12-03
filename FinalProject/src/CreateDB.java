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
			createWarehouseReceiptTable();
			createTransactionsTable();
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
				+ "  `username` VARCHAR(50) NOT NULL UNIQUE,\r\n"
				+ "  `first_name` VARCHAR(50) NOT NULL,\r\n"
				+ "  `last_name` VARCHAR(50) NOT NULL,\r\n"
				+ "  `birth_date` DATE NOT NULL,\r\n"
				+ "  `email` VARCHAR(128) NOT NULL UNIQUE,\r\n"
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
	
	
	private static void createWarehouseReceiptTable() throws SQLException {
	    String sql = "CREATE TABLE `warehouse_receipt` (\r\n"
	            + "  `receipt_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
	            + "  `SKU` VARCHAR(64) NOT NULL,\r\n"
	            + "  `receiptDate` DATE NOT NULL,\r\n"
	            + "  `lotCode` VARCHAR(64) NOT NULL,\r\n"
	            + "  `stockCount` INT UNSIGNED NOT NULL,\r\n"
	            + "  PRIMARY KEY (`receipt_id`),\r\n"
	            + "  FOREIGN KEY (`SKU`) REFERENCES `items`(`SKU`)\r\n"
	            + "    ON UPDATE CASCADE\r\n"
	            + "    ON DELETE RESTRICT\r\n"
	            + ");";

	    try {
	        stmt.execute(sql);
	    } catch (SQLException e) {
	        System.out.println("Table creation failed - warehouse_receipt");
	        throw new SQLException(e);
	    }
	}
	
	private static void createTransactionsTable() throws SQLException {
		// Used Copilot to generate sql statement based on my stated requirements, and modified after
		String sql = "	CREATE TABLE `transactions` (\r\n"
				+ "			  `transaction_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
				+ "           `description` VARCHAR(256) DEFAULT NULL,\r\n"
				+ "			  `user_id` INT UNSIGNED NOT NULL,\r\n"
				+ "			  `SKU` VARCHAR(64) NOT NULL,\r\n"
				+ "			  `quantity` INT UNSIGNED NOT NULL,\r\n"
				+ "			  `transaction_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ "			  PRIMARY KEY (`transaction_id`),\r\n"
				+ "			  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)\r\n"
				+ "			    ON UPDATE CASCADE\r\n"
				+ "			    ON DELETE RESTRICT,\r\n"
				+ "			  FOREIGN KEY (`SKU`) REFERENCES `items`(`SKU`)\r\n"
				+ "			    ON UPDATE CASCADE\r\n"
				+ "			    ON DELETE RESTRICT\r\n"
				+ "			);";
		try {
	        stmt.execute(sql);
	    } catch (SQLException e) {
	        System.out.println("Table creation failed - transactions");
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
			addItems();
			addReceipts();
			addTransactions();
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
				+ "  `first_name`, `last_name`, `birth_date`, `username`, `email`, `salt`, `hashed_password`, `sex`, `role`\r\n"
				+ ") VALUES\r\n"
				+ "('Alice', 'Johnson', '1990-04-15', 'alicej', 'alicej@gmail.com', 'salt123456789012', 'hashed_pw_1', 'FEMALE', 'ADMIN'),\r\n"
				+ "('Bob', 'Smith', '1985-09-22', 'bobsmith', 'bobsmith@gmail.com', 'salt123456789013', 'hashed_pw_2', 'MALE', 'MANAGER'),\r\n"
				+ "('Carol', 'Nguyen', '1992-12-03', 'caroln', 'caroln@gmail.com',  'salt123456789014', 'hashed_pw_3', 'FEMALE', 'EMPLOYEE'),\r\n"
				+ "('David', 'Lee', '1998-07-30', 'dlee98', 'dlee98@gmail.com', 'salt123456789015', 'hashed_pw_4', 'MALE', 'CUSTOMER'),\r\n"
				+ "('Eve', 'Martinez', '1995-01-10', 'evem', 'evem@gmail.com', 'salt123456789016', 'hashed_pw_5', 'FEMALE', 'EMPLOYEE'),\r\n"
				+ "('Test', 'Test', '2025-10-01', 'user', 'user@gmail.com', 'tz19WOXoaPqUUdAJYxxtGg==', '10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=', 'MALE', 'ADMIN');";
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("Table creation failed - users");
			throw new SQLException(e);
		}
	}
	
	private static void addItems() throws SQLException {
	    String sql = "INSERT INTO `items` (`item_name`, `user_id`, `description`, `weight_kg`, `price`, `tax_bracket`, `expiration_time`, `SKU`, `category`, `units_per_bin`, `min_temp`, `max_temp`) VALUES "
	        + "('Organic Apple', 1, 'Fresh organic apple from Utah orchards', 0.2, 150, 'FOOD', 30, 'SKU-GROC-0001', 'GROCERY', 50, 2, 8),"
	        + "('Canned Beans', 2, 'Low-sodium black beans, 15oz', 0.4, 120, 'FOOD', 1095, 'SKU-GROC-0002', 'GROCERY', 40, NULL, NULL),"
	        + "('Bottled Water', 3, '24-pack spring water bottles', 9.0, 499, 'FOOD', 365, 'SKU-BEV-0003', 'BEVERAGE', 20, NULL, NULL),"
	        + "('Energy Drink', 4, 'Caffeinated energy drink, 16oz', 0.5, 299, 'FOOD', 180, 'SKU-BEV-0004', 'BEVERAGE', 30, NULL, NULL),"
	        + "('Ibuprofen 200mg', 3, 'Pain relief tablets, 100 count', 0.1, 899, 'MEDICAL', 730, 'SKU-PHAR-0005', 'PHARMACEUTICAL', 20, 15, 25),"
	        + "('Allergy Relief', 5, 'Non-drowsy antihistamine tablets', 0.1, 1099, 'MEDICAL', 1095, 'SKU-PHAR-0006', 'PHARMACEUTICAL', 25, NULL, NULL),"
	        + "('Men''s T-Shirt', 2, 'Cotton crew neck t-shirt', 0.3, 899, 'GENERAL', 1825, 'SKU-APP-0007', 'APPAREL', 60, NULL, NULL),"
	        + "('Women''s Jeans', 1, 'Stretch denim jeans', 0.8, 2999, 'GENERAL', 1825, 'SKU-APP-0008', 'APPAREL', 40, NULL, NULL),"
	        + "('Running Shoes', 4, 'Lightweight athletic sneakers', 1.2, 4999, 'GENERAL', 1825, 'SKU-FTW-0009', 'FOOTWEAR', 30, NULL, NULL),"
	        + "('Work Boots', 5, 'Steel-toe waterproof boots', 2.5, 7999, 'GENERAL', 1825, 'SKU-FTW-0010', 'FOOTWEAR', 20, NULL, NULL),"
	        + "('Cordless Drill', 1, '18V lithium-ion drill', 2.5, 4999, 'GENERAL', 1825, 'SKU-TLS-0011', 'TOOLS', 10, NULL, NULL),"
	        + "('Hammer', 2, '16oz claw hammer', 1.0, 1299, 'GENERAL', 1825, 'SKU-TLS-0012', 'TOOLS', 50, NULL, NULL),"
	        + "('Bluetooth Speaker', 3, 'Portable waterproof speaker', 0.6, 3999, 'GENERAL', 1825, 'SKU-ELEC-0013', 'ELECTRONICS', 25, NULL, NULL),"
	        + "('USB Flash Drive', 4, '128GB USB 3.0 drive', 0.1, 1499, 'GENERAL', 1825, 'SKU-ELEC-0014', 'ELECTRONICS', 100, NULL, NULL),"
	        + "('9mm Pistol', 5, 'Semi-automatic handgun', 1.1, 39999, 'GENERAL', 3650, 'SKU-FIRE-0015', 'FIREARMS', 5, NULL, NULL),"
	        + "('Hunting Rifle', 2, 'Bolt-action rifle with scope', 4.5, 79999, 'GENERAL', 3650, 'SKU-FIRE-0016', 'FIREARMS', 3, NULL, NULL),"
	        + "('9mm Ammo', 1, 'Box of 50 rounds', 0.8, 2499, 'GENERAL', 3650, 'SKU-AMMO-0017', 'AMMUNITION', 20, NULL, NULL),"
	        + "('Shotgun Shells', 3, '12-gauge shells, 25-pack', 1.2, 2999, 'GENERAL', 3650, 'SKU-AMMO-0018', 'AMMUNITION', 15, NULL, NULL),"
	        + "('Office Chair', 4, 'Ergonomic mesh chair', 12.0, 8999, 'GENERAL', 1825, 'SKU-FURN-0019', 'FURNITURE', 5, NULL, NULL),"
		    + "('Folding Table', 5, '6-foot plastic table', 15.0, 5999, 'GENERAL', 1825, 'SKU-FURN-0020', 'FURNITURE', 4, NULL, NULL),"
	        + "('Action Figure', 2, 'Superhero collectible toy', 0.3, 1499, 'GENERAL', 1825, 'SKU-TOY-0021', 'TOYS', 60, NULL, NULL),"
	        + "('Puzzle Set', 1, '1000-piece jigsaw puzzle', 0.9, 999, 'GENERAL', 1825, 'SKU-TOY-0022', 'TOYS', 40, NULL, NULL),"
	        + "('Motor Oil', 3, '5-quart synthetic oil', 4.5, 2999, 'GENERAL', 1825, 'SKU-AUTO-0023', 'AUTOMOTIVE', 20, NULL, NULL),"
	        + "('Car Battery', 4, '12V lead-acid battery', 10.0, 8999, 'GENERAL', 1825, 'SKU-AUTO-0024', 'AUTOMOTIVE', 10, NULL, NULL),"
	        + "('Basketball', 5, 'Official size outdoor ball', 0.7, 1999, 'GENERAL', 1825, 'SKU-SPORT-0025', 'SPORTING_GOODS', 30, NULL, NULL),"
	        + "('Fishing Rod', 2, 'Telescopic spinning rod', 1.5, 3499, 'GENERAL', 1825, 'SKU-SPORT-0026', 'SPORTING_GOODS', 15, NULL, NULL),"
	        + "('Gold Necklace', 1, '14K gold chain necklace', 0.2, 129999, 'GENERAL', 1825, 'SKU-JEWEL-0027', 'JEWELRY', 10, NULL, NULL),"
	        + "('Silver Ring', 3, 'Sterling silver band', 0.1, 4999, 'GENERAL', 1825, 'SKU-JEWEL-0028', 'JEWELRY', 20, NULL, NULL),"
	        + "('Lipstick', 4, 'Matte finish lipstick', 0.05, 899, 'GENERAL', 730, 'SKU-COS-0029', 'COSMETICS', 100, NULL, NULL),"
	        + "('Face Cream', 5, 'Moisturizing night cream', 0.2, 1999, 'GENERAL', 365, 'SKU-COS-0030', 'COSMETICS', 50, NULL, NULL),"
	        + "('Dog Food', 2, 'Dry kibble, 20lb bag', 9.0, 3499, 'FOOD', 365, 'SKU-PET-0031', 'PET_SUPPLIES', 10, NULL, NULL),"
	        + "('Cat Litter', 1, 'Clumping clay litter, 40lb', 18.0, 1599, 'GENERAL', 1825, 'SKU-PET-0032', 'PET_SUPPLIES', 8, NULL, NULL),"
	        + "('Bleach', 3, 'Disinfecting bleach, 1 gallon', 4.0, 599, 'GENERAL', 1825, 'SKU-CLEAN-0033', 'CLEANING', 30, NULL, NULL),"
	        + "('Wipes', 4, 'Antibacterial wipes, 80 count', 0.6, 799, 'GENERAL', 365, 'SKU-CLEAN-0034', 'CLEANING', 40, NULL, NULL),"
	        + "('Notebook', 5, 'College-ruled spiral notebook', 0.3, 299, 'GENERAL', 1825, 'SKU-STAT-0035', 'STATIONERY', 100, NULL, NULL),"
	        + "('Ballpoint Pens', 1, 'Pack of 10 blue pens', 0.2, 199, 'GENERAL', 1825, 'SKU-STAT-0036', 'STATIONERY', 80, NULL, NULL),"
	        + "('Garden Shovel', 2, 'Steel digging shovel with wood handle', 2.5, 2499, 'GENERAL', 1825, 'SKU-GARD-0037', 'GARDENING', 15, NULL, NULL),"
	        + "('Fertilizer', 3, 'All-purpose plant food, 5lb', 2.0, 1599, 'GENERAL', 730, 'SKU-GARD-0038', 'GARDENING', 20, NULL, NULL),"
	        + "('Safety Gloves', 4, 'Cut-resistant industrial gloves', 0.4, 899, 'GENERAL', 1825, 'SKU-IND-0039', 'INDUSTRIAL', 50, NULL, NULL),"
	        + "('Welding Mask', 5, 'Auto-darkening helmet', 1.8, 4999, 'GENERAL', 1825, 'SKU-IND-0040', 'INDUSTRIAL', 10, NULL, NULL),"
	        + "('Desk Lamp', 1, 'LED adjustable desk lamp', 0.9, 1999, 'GENERAL', 1825, 'SKU-OFF-0041', 'OFFICE_EQUIPMENT', 25, NULL, NULL),"
	        + "('Laser Printer', 2, 'Monochrome wireless printer', 6.0, 8999, 'GENERAL', 1825, 'SKU-OFF-0042', 'OFFICE_EQUIPMENT', 5, NULL, NULL),"
	        + "('Diapers', 3, 'Size 3 baby diapers, 100 count', 2.5, 2499, 'GENERAL', 365, 'SKU-BABY-0043', 'BABY_PRODUCTS', 30, NULL, NULL),"
	        + "('Baby Wipes', 4, 'Sensitive skin wipes, 80 count', 0.6, 799, 'GENERAL', 365, 'SKU-BABY-0044', 'BABY_PRODUCTS', 40, NULL, NULL),"
	        + "('Cookbook', 5, 'Family recipes from the Southwest', 0.7, 1899, 'GENERAL', 1825, 'SKU-BOOK-0045', 'BOOKS', 20, NULL, NULL),"
	        + "('Sci-Fi Novel', 1, 'Award-winning space opera', 0.5, 1499, 'GENERAL', 1825, 'SKU-BOOK-0046', 'BOOKS', 25, NULL, NULL),"
	        + "('Guitar Strings', 2, 'Steel acoustic strings, medium gauge', 0.1, 999, 'GENERAL', 1825, 'SKU-MUSIC-0047', 'MUSIC', 50, NULL, NULL),"
	        + "('Headphones', 3, 'Over-ear noise-canceling headphones', 0.6, 5999, 'GENERAL', 1825, 'SKU-MUSIC-0048', 'MUSIC', 15, NULL, NULL),"
	        + "('Game Controller', 4, 'Wireless controller for console', 0.4, 3999, 'GENERAL', 1825, 'SKU-GAME-0049', 'VIDEO_GAMES', 30, NULL, NULL),"
	        + "('RPG Game Disc', 5, 'Fantasy role-playing game for console', 0.2, 5999, 'GENERAL', 1825, 'SKU-GAME-0050', 'VIDEO_GAMES', 20, NULL, NULL),"
	        + "('Designer Handbag', 1, 'Leather luxury handbag', 1.0, 149999, 'GENERAL', 1825, 'SKU-LUX-0051', 'LUXURY', 5, NULL, NULL),"
	        + "('Premium Watch', 2, 'Swiss automatic wristwatch', 0.3, 199999, 'GENERAL', 1825, 'SKU-LUX-0052', 'LUXURY', 3, NULL, NULL),"
	        + "('Shotgun Shells', 3, 'Box of 25 12-gauge shells', 1.2, 2999, 'GENERAL', 3650, 'SKU-AMMO-0053', 'AMMUNITION', 15, NULL, NULL),"
	        + "('.30-06 Rifle Ammo', 4, 'Box of 20 .30-06 Springfield rounds', 1.0, 3499, 'GENERAL', 3650, 'SKU-AMMO-0054', 'AMMUNITION', 10, NULL, NULL);";

        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Seed insert failed - items");
            throw new SQLException(e);
        }
    }
	
	private static void addReceipts() throws SQLException {
	    // Created with help from copilot to generate realistic warehouse receipts.
	    String sql = "INSERT INTO `warehouse_receipt` (\r\n"
	            + "  `SKU`, `receiptDate`, `lotCode`, `stockCount`\r\n"
	            + ") VALUES\r\n"
	            + "('SKU-AMMO-0017','2025-10-01','LOT-AMMO17-1',140),\r\n"
	            + "('SKU-AMMO-0017','2025-10-15','LOT-AMMO17-2',100),\r\n"
	            + "('SKU-AMMO-0017','2025-11-01','LOT-AMMO17-3',115),\r\n"
	            + "('SKU-AMMO-0018','2025-10-02','LOT-AMMO18-1',170),\r\n"
	            + "('SKU-AMMO-0018','2025-10-20','LOT-AMMO18-2',110),\r\n"
	            + "('SKU-AMMO-0018','2025-11-05','LOT-AMMO18-3',130),\r\n"
	            + "('SKU-AMMO-0053','2025-10-05','LOT-AMMO53-1',220),\r\n"
	            + "('SKU-AMMO-0053','2025-10-25','LOT-AMMO53-2',130),\r\n"
	            + "('SKU-AMMO-0053','2025-11-10','LOT-AMMO53-3',150),\r\n"
	            + "('SKU-AMMO-0054','2025-10-06','LOT-AMMO54-1',195),\r\n"
	            + "('SKU-AMMO-0054','2025-10-28','LOT-AMMO54-2',115),\r\n"
	            + "('SKU-AMMO-0054','2025-11-12','LOT-AMMO54-3',125),\r\n"
	            + "('SKU-APP-0007','2025-10-03','LOT-APP7-1',80),\r\n"
	            + "('SKU-APP-0007','2025-10-18','LOT-APP7-2',60),\r\n"
	            + "('SKU-APP-0007','2025-11-02','LOT-APP7-3',75),\r\n"
	            + "('SKU-APP-0008','2025-10-04','LOT-APP8-1',95),\r\n"
	            + "('SKU-APP-0008','2025-10-19','LOT-APP8-2',75),\r\n"
	            + "('SKU-APP-0008','2025-11-03','LOT-APP8-3',85),\r\n"
	            + "('SKU-AUTO-0023','2025-10-07','LOT-AUTO23-1',50),\r\n"
	            + "('SKU-AUTO-0023','2025-10-21','LOT-AUTO23-2',45),\r\n"
	            + "('SKU-AUTO-0023','2025-11-04','LOT-AUTO23-3',48),\r\n"
	            + "('SKU-AUTO-0024','2025-10-08','LOT-AUTO24-1',70),\r\n"
	            + "('SKU-AUTO-0024','2025-10-22','LOT-AUTO24-2',55),\r\n"
	            + "('SKU-AUTO-0024','2025-11-06','LOT-AUTO24-3',58),\r\n"
	            + "('SKU-BABY-0043','2025-10-09','LOT-BABY43-1',70),\r\n"
	            + "('SKU-BABY-0043','2025-10-23','LOT-BABY43-2',65),\r\n"
	            + "('SKU-BABY-0043','2025-11-07','LOT-BABY43-3',68),\r\n"
	            + "('SKU-BABY-0044','2025-10-10','LOT-BABY44-1',75),\r\n"
	            + "('SKU-BABY-0044','2025-10-24','LOT-BABY44-2',70),\r\n"
	            + "('SKU-BABY-0044','2025-11-08','LOT-BABY44-3',72),\r\n"
	            + "('SKU-BEV-0003','2025-10-11','LOT-BEV3-1',120),\r\n"
	            + "('SKU-BEV-0003','2025-10-26','LOT-BEV3-2',110),\r\n"
	            + "('SKU-BEV-0003','2025-11-09','LOT-BEV3-3',115),\r\n"
	            + "('SKU-BEV-0004','2025-10-12','LOT-BEV4-1',130),\r\n"
	            + "('SKU-BEV-0004','2025-10-27','LOT-BEV4-2',115),\r\n"
	            + "('SKU-BEV-0004','2025-11-10','LOT-BEV4-3',125),\r\n"
	            + "('SKU-BOOK-0045','2025-10-13','LOT-BOOK45-1',90),\r\n"
	            + "('SKU-BOOK-0045','2025-10-28','LOT-BOOK45-2',85),\r\n"
	            + "('SKU-BOOK-0045','2025-11-11','LOT-BOOK45-3',78),\r\n"
	            + "('SKU-BOOK-0046','2025-10-14','LOT-BOOK46-1',90),\r\n"
	            + "('SKU-BOOK-0046','2025-10-29','LOT-BOOK46-2',85),\r\n"
	            + "('SKU-BOOK-0046','2025-11-12','LOT-BOOK46-3',88),\r\n"
	            + "('SKU-CLEAN-0033','2025-10-15','LOT-CLEAN33-1',95),\r\n"
	            + "('SKU-CLEAN-0033','2025-10-30','LOT-CLEAN33-2',80),\r\n"
	            + "('SKU-CLEAN-0033','2025-11-13','LOT-CLEAN33-3',85),\r\n"
	            + "('SKU-CLEAN-0034','2025-10-16','LOT-CLEAN34-1',105),\r\n"
	            + "('SKU-CLEAN-0034','2025-10-31','LOT-CLEAN34-2',90),\r\n"
	            + "('SKU-CLEAN-0034','2025-11-14','LOT-CLEAN34-3',95),\r\n"
	            + "('SKU-COS-0029','2025-10-17','LOT-COS29-1',70),\r\n"
	            + "('SKU-COS-0029','2025-10-25','LOT-COS29-2',65),\r\n"
	            + "('SKU-COS-0029','2025-11-15','LOT-COS29-3',68),\r\n"
	            + "('SKU-COS-0030','2025-10-18','LOT-COS30-1',75),\r\n"
	            + "('SKU-COS-0030','2025-10-26','LOT-COS30-2',70),\r\n"
	            + "('SKU-COS-0030','2025-11-16','LOT-COS30-3',73),\r\n"
	            + "('SKU-ELEC-0013','2025-10-19','LOT-ELEC13-1',50),\r\n"
	            + "('SKU-ELEC-0013','2025-10-27','LOT-ELEC13-2',45),\r\n"
	            + "('SKU-ELEC-0013','2025-11-05','LOT-ELEC13-3',48),\r\n"
	            + "('SKU-ELEC-0014','2025-10-20','LOT-ELEC14-1',60),\r\n"
	            + "('SKU-ELEC-0014','2025-10-28','LOT-ELEC14-2',55),\r\n"
	            + "('SKU-ELEC-0014','2025-11-06','LOT-ELEC14-3',58),\r\n"
	            + "('SKU-FIRE-0015','2025-10-21','LOT-FIRE15-1',45),\r\n"
	            + "('SKU-FIRE-0015','2025-10-29','LOT-FIRE15-2',30),\r\n"
	            + "('SKU-FIRE-0015','2025-11-07','LOT-FIRE15-3',32),\r\n"
	            + "('SKU-FIRE-0016','2025-10-22','LOT-FIRE16-1',40),\r\n"
	            + "('SKU-FIRE-0016','2025-10-30','LOT-FIRE16-2',45),\r\n"
	            + "('SKU-FIRE-0016','2025-11-08','LOT-FIRE16-3',38),\r\n"
	            + "('SKU-FTW-0009','2025-10-23','LOT-FTW9-1',80),\r\n"
	            + "('SKU-FTW-0009','2025-10-31','LOT-FTW9-2',75),\r\n"
	            + "('SKU-FTW-0009','2025-11-09','LOT-FTW9-3',78),\r\n"
	            + "('SKU-FTW-0010','2025-10-24','LOT-FTW10-1',90),\r\n"
	            + "('SKU-FTW-0010','2025-11-01','LOT-FTW10-2',85),\r\n"
	            + "('SKU-FTW-0010','2025-11-10','LOT-FTW10-3',88),\r\n"
	            + "('SKU-FURN-0019','2025-10-25','LOT-FURN19-1',55),\r\n"
	            + "('SKU-FURN-0019','2025-11-02','LOT-FURN19-2',60),\r\n"
	            + "('SKU-FURN-0019','2025-11-11','LOT-FURN19-3',52),\r\n"
	            + "('SKU-FURN-0020','2025-10-26','LOT-FURN20-1',65),\r\n"
	            + "('SKU-FURN-0020','2025-11-03','LOT-FURN20-2',70),\r\n"
	            + "('SKU-FURN-0020','2025-11-12','LOT-FURN20-3',63),\r\n"
	            + "('SKU-GAME-0049','2025-10-27','LOT-GAME49-1',100),\r\n"
	            + "('SKU-GAME-0049','2025-11-04','LOT-GAME49-2',95),\r\n"
	            + "('SKU-GAME-0049','2025-11-13','LOT-GAME49-3',98),\r\n"
	            + "('SKU-GAME-0050','2025-10-28','LOT-GAME50-1',110),\r\n"
	            + "('SKU-GAME-0050','2025-11-05','LOT-GAME50-2',105),\r\n"
	            + "('SKU-GAME-0050','2025-11-14','LOT-GAME50-3',108),\r\n"
	            + "('SKU-GARD-0037','2025-10-29','LOT-GARD37-1',70),\r\n"
	            + "('SKU-GARD-0037','2025-11-06','LOT-GARD37-2',65),\r\n"
	            + "('SKU-GARD-0037','2025-11-15','LOT-GARD37-3',68),\r\n"
	            + "('SKU-GARD-0038','2025-10-30','LOT-GARD38-1',80),\r\n"
	            + "('SKU-GARD-0038','2025-11-07','LOT-GARD38-2',75),\r\n"
	            + "('SKU-GARD-0038','2025-11-16','LOT-GARD38-3',78),\r\n"
	            + "('SKU-GROC-0001','2025-10-31','LOT-GROC1-1',210),\r\n"
	            + "('SKU-GROC-0001','2025-11-08','LOT-GROC1-2',190),\r\n"
	            + "('SKU-GROC-0001','2025-11-17','LOT-GROC1-3',200),\r\n"
	            + "('SKU-GROC-0002','2025-11-01','LOT-GROC2-1',230),\r\n"
	            + "('SKU-GROC-0002','2025-11-09','LOT-GROC2-2',200),\r\n"
	            + "('SKU-GROC-0002','2025-11-18','LOT-GROC2-3',210),\r\n"
	            + "('SKU-IND-0039','2025-11-02','LOT-IND39-1',45),\r\n"
	            + "('SKU-IND-0039','2025-11-10','LOT-IND39-2',40),\r\n"
	            + "('SKU-IND-0039','2025-11-19','LOT-IND39-3',42),\r\n"
	            + "('SKU-IND-0040','2025-11-03','LOT-IND40-1',55),\r\n"
	            + "('SKU-IND-0040','2025-11-11','LOT-IND40-2',50),\r\n"
	            + "('SKU-IND-0040','2025-11-20','LOT-IND40-3',52),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-04','LOT-JEWEL27-1',25),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-12','LOT-JEWEL27-2',22),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-21','LOT-JEWEL27-3',24),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-05','LOT-JEWEL28-1',30),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-13','LOT-JEWEL28-2',28),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-22','LOT-JEWEL28-3',29),\r\n"
	            + "('SKU-LUX-0051','2025-11-06','LOT-LUX51-1',20),\r\n"
	            + "('SKU-LUX-0051','2025-11-14','LOT-LUX51-2',18),\r\n"
	            + "('SKU-LUX-0051','2025-11-23','LOT-LUX51-3',19),\r\n"
	            + "('SKU-LUX-0052','2025-11-07','LOT-LUX52-1',32),\r\n"
	            + "('SKU-LUX-0052','2025-11-15','LOT-LUX52-2',19),\r\n"
	            + "('SKU-LUX-0052','2025-11-24','LOT-LUX52-3',21),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-08','LOT-MUSIC47-1',60),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-16','LOT-MUSIC47-2',55),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-25','LOT-MUSIC47-3',58),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-09','LOT-MUSIC48-1',65),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-17','LOT-MUSIC48-2',60),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-26','LOT-MUSIC48-3',63),\r\n"
	            + "('SKU-OFF-0041','2025-11-10','LOT-OFF41-1',80),\r\n"
	            + "('SKU-OFF-0041','2025-11-18','LOT-OFF41-2',75),\r\n"
	            + "('SKU-OFF-0041','2025-11-27','LOT-OFF41-3',78),\r\n"
	            + "('SKU-OFF-0042','2025-11-11','LOT-OFF42-1',90),\r\n"
	            + "('SKU-OFF-0042','2025-11-19','LOT-OFF42-2',95),\r\n"
	            + "('SKU-OFF-0042','2025-11-28','LOT-OFF42-3',88),\r\n"
	            + "('SKU-PET-0031','2025-11-12','LOT-PET31-1',100),\r\n"
	            + "('SKU-PET-0031','2025-11-20','LOT-PET31-2',95),\r\n"
	            + "('SKU-PET-0031','2025-11-29','LOT-PET31-3',98),\r\n"
	            + "('SKU-PET-0032','2025-11-13','LOT-PET32-1',110),\r\n"
	            + "('SKU-PET-0032','2025-11-21','LOT-PET32-2',115),\r\n"
	            + "('SKU-PET-0032','2025-11-30','LOT-PET32-3',108),\r\n"
	            + "('SKU-PHAR-0005','2025-11-14','LOT-PHAR5-1',130),\r\n"
	            + "('SKU-PHAR-0005','2025-11-22','LOT-PHAR5-2',120),\r\n"
	            + "('SKU-PHAR-0005','2025-12-01','LOT-PHAR5-3',125),\r\n"
	            + "('SKU-PHAR-0006','2025-11-15','LOT-PHAR6-1',140),\r\n"
	            + "('SKU-PHAR-0006','2025-11-23','LOT-PHAR6-2',125),\r\n"
	            + "('SKU-PHAR-0006','2025-12-02','LOT-PHAR6-3',130),\r\n"
	            + "('SKU-SPORT-0025','2025-11-16','LOT-SPORT25-1',85),\r\n"
	            + "('SKU-SPORT-0025','2025-11-24','LOT-SPORT25-2',80),\r\n"
	            + "('SKU-SPORT-0025','2025-12-03','LOT-SPORT25-3',82),\r\n"
	            + "('SKU-SPORT-0026','2025-11-17','LOT-SPORT26-1',95),\r\n"
	            + "('SKU-SPORT-0026','2025-11-25','LOT-SPORT26-2',90),\r\n"
	            + "('SKU-SPORT-0026','2025-12-04','LOT-SPORT26-3',93),\r\n"
	            + "('SKU-STAT-0035','2025-11-18','LOT-STAT35-1',70),\r\n"
	            + "('SKU-STAT-0035','2025-11-26','LOT-STAT35-2',65),\r\n"
	            + "('SKU-STAT-0035','2025-12-05','LOT-STAT35-3',68),\r\n"
	            + "('SKU-STAT-0036','2025-11-19','LOT-STAT36-1',75),\r\n"
	            + "('SKU-STAT-0036','2025-11-27','LOT-STAT36-2',70),\r\n"
	            + "('SKU-STAT-0036','2025-12-06','LOT-STAT36-3',73),\r\n"
	            + "('SKU-TLS-0011','2025-11-20','LOT-TLS11-1',50),\r\n"
	            + "('SKU-TLS-0011','2025-11-28','LOT-TLS11-2',45),\r\n"
	            + "('SKU-TLS-0011','2025-12-07','LOT-TLS11-3',48),\r\n"
	            + "('SKU-TLS-0012','2025-11-21','LOT-TLS12-1',60),\r\n"
	            + "('SKU-TLS-0012','2025-11-29','LOT-TLS12-2',55),\r\n"
	            + "('SKU-TLS-0012','2025-12-08','LOT-TLS12-3',58),\r\n"
	            + "('SKU-TOY-0021','2025-11-22','LOT-TOY21-1',100),\r\n"
	            + "('SKU-TOY-0021','2025-11-30','LOT-TOY21-2',95),\r\n"
	            + "('SKU-TOY-0021','2025-12-09','LOT-TOY21-3',98),\r\n"
	            + "('SKU-TOY-0022','2025-11-23','LOT-TOY22-1',110),\r\n"
	            + "('SKU-TOY-0022','2025-12-01','LOT-TOY22-2',105),\r\n"
	            + "('SKU-TOY-0022','2025-12-10','LOT-TOY22-3',108)\r\n"
	            + ";";
	    try {
	        stmt.execute(sql);
	    } catch (SQLException e) {
	        System.out.println("Seed insertion failed - warehouse_receipt");
	        throw new SQLException(e);
	    }
	}
	
	/**
	 * Adds Transaction seed data in the database
	 * statement was generated via python written by Copilot.
	 * Python was modified to fit requirements
	 * @throws SQLException
	 */
	private static void addTransactions() throws SQLException {
		String sql = "INSERT INTO transactions (user_id, SKU, quantity, transaction_date) VALUES \r\n"
				+ "(4, 'SKU-COS-0030', 3, '2025-11-24 20:56:26'),\r\n"
				+ "(5, 'SKU-MUSIC-0048', 1, '2025-11-15 07:24:00'),\r\n"
				+ "(1, 'SKU-LUX-0052', 2, '2025-11-18 14:40:18'),\r\n"
				+ "(1, 'SKU-APP-0008', 2, '2025-11-11 06:32:53'),\r\n"
				+ "(6, 'SKU-GROC-0002', 3, '2025-11-06 11:15:13'),\r\n"
				+ "(5, 'SKU-AUTO-0023', 3, '2025-11-10 05:40:16'),\r\n"
				+ "(1, 'SKU-GARD-0038', 2, '2025-11-02 21:53:42'),\r\n"
				+ "(3, 'SKU-PET-0032', 3, '2025-11-27 23:19:58'),\r\n"
				+ "(2, 'SKU-PET-0031', 2, '2025-11-29 00:45:25'),\r\n"
				+ "(1, 'SKU-FURN-0019', 3, '2025-11-25 16:44:13'),\r\n"
				+ "(6, 'SKU-BEV-0004', 3, '2025-11-29 04:26:58'),\r\n"
				+ "(1, 'SKU-FIRE-0015', 2, '2025-11-27 10:54:48'),\r\n"
				+ "(4, 'SKU-BABY-0044', 3, '2025-11-29 06:11:36'),\r\n"
				+ "(6, 'SKU-COS-0030', 1, '2025-11-29 00:17:10'),\r\n"
				+ "(1, 'SKU-GAME-0049', 2, '2025-11-26 00:48:09'),\r\n"
				+ "(1, 'SKU-PHAR-0006', 1, '2025-11-07 12:42:27'),\r\n"
				+ "(6, 'SKU-BOOK-0046', 2, '2025-11-10 12:49:16'),\r\n"
				+ "(2, 'SKU-MUSIC-0047', 3, '2025-11-01 15:32:58'),\r\n"
				+ "(1, 'SKU-BABY-0043', 3, '2025-11-27 15:27:11'),\r\n"
				+ "(1, 'SKU-OFF-0042', 2, '2025-11-26 01:15:48'),\r\n"
				+ "(1, 'SKU-OFF-0041', 2, '2025-11-21 17:24:52'),\r\n"
				+ "(2, 'SKU-TOY-0021', 1, '2025-11-04 21:59:44'),\r\n"
				+ "(2, 'SKU-TLS-0011', 3, '2025-12-01 05:52:03'),\r\n"
				+ "(5, 'SKU-FURN-0019', 2, '2025-11-21 20:38:38'),\r\n"
				+ "(3, 'SKU-FTW-0010', 2, '2025-11-24 23:25:15'),\r\n"
				+ "(3, 'SKU-MUSIC-0048', 3, '2025-11-03 17:21:41'),\r\n"
				+ "(1, 'SKU-JEWEL-0028', 2, '2025-11-06 05:04:50'),\r\n"
				+ "(5, 'SKU-MUSIC-0047', 1, '2025-11-29 01:45:32'),\r\n"
				+ "(3, 'SKU-OFF-0042', 1, '2025-10-31 18:13:53'),\r\n"
				+ "(3, 'SKU-TOY-0022', 3, '2025-11-02 13:57:16'),\r\n"
				+ "(4, 'SKU-MUSIC-0047', 2, '2025-11-24 12:55:53'),\r\n"
				+ "(5, 'SKU-TLS-0012', 1, '2025-11-18 15:23:24'),\r\n"
				+ "(6, 'SKU-GAME-0049', 3, '2025-11-26 06:43:20'),\r\n"
				+ "(3, 'SKU-GARD-0038', 2, '2025-11-13 08:46:05'),\r\n"
				+ "(4, 'SKU-FIRE-0015', 3, '2025-11-25 12:31:50'),\r\n"
				+ "(3, 'SKU-CLEAN-0034', 3, '2025-11-06 02:00:57'),\r\n"
				+ "(1, 'SKU-BOOK-0045', 3, '2025-11-19 22:40:50'),\r\n"
				+ "(2, 'SKU-TLS-0012', 1, '2025-11-26 09:32:39'),\r\n"
				+ "(3, 'SKU-APP-0007', 2, '2025-11-01 13:05:22'),\r\n"
				+ "(3, 'SKU-FURN-0019', 1, '2025-11-06 21:46:09'),\r\n"
				+ "(3, 'SKU-SPORT-0025', 1, '2025-11-07 13:45:42'),\r\n"
				+ "(3, 'SKU-IND-0039', 1, '2025-11-21 18:52:12'),\r\n"
				+ "(3, 'SKU-FIRE-0016', 1, '2025-11-24 12:51:21'),\r\n"
				+ "(3, 'SKU-FURN-0020', 1, '2025-11-07 08:37:47'),\r\n"
				+ "(1, 'SKU-IND-0039', 1, '2025-11-20 12:13:33'),\r\n"
				+ "(6, 'SKU-GARD-0037', 3, '2025-11-24 19:22:41'),\r\n"
				+ "(4, 'SKU-FIRE-0016', 3, '2025-11-18 21:44:16'),\r\n"
				+ "(2, 'SKU-TOY-0021', 1, '2025-10-31 16:56:49'),\r\n"
				+ "(3, 'SKU-OFF-0042', 3, '2025-11-09 12:47:43'),\r\n"
				+ "(2, 'SKU-BOOK-0045', 3, '2025-11-22 20:11:22'),\r\n"
				+ "(1, 'SKU-BEV-0004', 1, '2025-11-10 21:40:46'),\r\n"
				+ "(6, 'SKU-GAME-0049', 3, '2025-11-06 07:49:22'),\r\n"
				+ "(3, 'SKU-GROC-0001', 2, '2025-11-19 08:01:00'),\r\n"
				+ "(1, 'SKU-TLS-0011', 2, '2025-11-16 05:31:41'),\r\n"
				+ "(6, 'SKU-AMMO-0053', 1, '2025-11-28 13:26:08'),\r\n"
				+ "(6, 'SKU-BABY-0044', 1, '2025-11-09 15:13:07'),\r\n"
				+ "(5, 'SKU-ELEC-0014', 1, '2025-11-26 18:32:48'),\r\n"
				+ "(1, 'SKU-GROC-0002', 3, '2025-11-05 18:38:04'),\r\n"
				+ "(4, 'SKU-PHAR-0005', 3, '2025-11-30 05:49:59'),\r\n"
				+ "(1, 'SKU-BOOK-0046', 3, '2025-11-19 03:58:39'),\r\n"
				+ "(1, 'SKU-JEWEL-0027', 2, '2025-11-06 10:44:19'),\r\n"
				+ "(3, 'SKU-GROC-0001', 2, '2025-11-08 21:56:27'),\r\n"
				+ "(5, 'SKU-OFF-0042', 2, '2025-11-18 08:10:04'),\r\n"
				+ "(5, 'SKU-AUTO-0024', 3, '2025-11-15 20:28:35'),\r\n"
				+ "(6, 'SKU-PHAR-0005', 2, '2025-11-10 01:24:50'),\r\n"
				+ "(5, 'SKU-OFF-0041', 2, '2025-11-21 19:51:22'),\r\n"
				+ "(5, 'SKU-FTW-0009', 1, '2025-11-27 17:01:04'),\r\n"
				+ "(3, 'SKU-AUTO-0024', 1, '2025-11-29 15:12:45'),\r\n"
				+ "(2, 'SKU-ELEC-0014', 2, '2025-11-16 20:46:54'),\r\n"
				+ "(4, 'SKU-GROC-0001', 2, '2025-11-04 01:00:07'),\r\n"
				+ "(1, 'SKU-MUSIC-0047', 3, '2025-11-06 11:32:14'),\r\n"
				+ "(2, 'SKU-JEWEL-0028', 1, '2025-11-09 01:43:22'),\r\n"
				+ "(3, 'SKU-PET-0032', 2, '2025-11-03 00:02:25'),\r\n"
				+ "(4, 'SKU-FTW-0009', 1, '2025-11-23 23:30:00'),\r\n"
				+ "(1, 'SKU-AMMO-0018', 2, '2025-11-11 03:16:18'),\r\n"
				+ "(1, 'SKU-BOOK-0045', 2, '2025-11-22 17:05:11'),\r\n"
				+ "(2, 'SKU-PHAR-0005', 3, '2025-11-03 07:21:29'),\r\n"
				+ "(5, 'SKU-TOY-0021', 3, '2025-11-05 01:46:02'),\r\n"
				+ "(6, 'SKU-COS-0029', 3, '2025-11-17 07:44:34'),\r\n"
				+ "(3, 'SKU-PET-0032', 1, '2025-11-10 07:31:29'),\r\n"
				+ "(4, 'SKU-MUSIC-0048', 1, '2025-11-21 17:25:17'),\r\n"
				+ "(6, 'SKU-FTW-0010', 3, '2025-11-04 16:22:37'),\r\n"
				+ "(4, 'SKU-CLEAN-0034', 1, '2025-11-27 21:13:16'),\r\n"
				+ "(2, 'SKU-BEV-0003', 2, '2025-11-05 08:52:24'),\r\n"
				+ "(6, 'SKU-JEWEL-0027', 1, '2025-11-03 08:19:34'),\r\n"
				+ "(6, 'SKU-FTW-0010', 3, '2025-11-23 23:20:32'),\r\n"
				+ "(3, 'SKU-AMMO-0054', 3, '2025-11-15 14:28:25'),\r\n"
				+ "(5, 'SKU-TOY-0021', 2, '2025-11-20 16:52:31'),\r\n"
				+ "(5, 'SKU-BOOK-0046', 2, '2025-11-02 12:48:58'),\r\n"
				+ "(4, 'SKU-SPORT-0025', 2, '2025-11-28 09:05:26'),\r\n"
				+ "(2, 'SKU-BOOK-0045', 3, '2025-11-15 08:33:32'),\r\n"
				+ "(6, 'SKU-BABY-0043', 2, '2025-11-18 08:06:37'),\r\n"
				+ "(6, 'SKU-AMMO-0053', 2, '2025-11-19 02:59:22'),\r\n"
				+ "(4, 'SKU-IND-0040', 1, '2025-11-04 21:10:38'),\r\n"
				+ "(6, 'SKU-GAME-0050', 1, '2025-11-19 20:02:16'),\r\n"
				+ "(6, 'SKU-FTW-0010', 3, '2025-11-07 17:54:53'),\r\n"
				+ "(4, 'SKU-FURN-0020', 1, '2025-11-24 08:53:17'),\r\n"
				+ "(1, 'SKU-GAME-0049', 1, '2025-11-02 03:59:44'),\r\n"
				+ "(1, 'SKU-BOOK-0046', 3, '2025-11-08 11:56:13'),\r\n"
				+ "(3, 'SKU-BEV-0003', 2, '2025-11-30 23:20:12'),\r\n"
				+ "(5, 'SKU-AMMO-0018', 2, '2025-11-12 18:19:54'),\r\n"
				+ "(4, 'SKU-PHAR-0005', 3, '2025-11-27 02:16:32'),\r\n"
				+ "(3, 'SKU-CLEAN-0033', 2, '2025-11-28 03:03:59'),\r\n"
				+ "(4, 'SKU-BEV-0003', 2, '2025-11-08 00:47:08'),\r\n"
				+ "(1, 'SKU-GARD-0038', 2, '2025-11-16 12:46:25'),\r\n"
				+ "(3, 'SKU-FIRE-0015', 1, '2025-11-06 22:47:29'),\r\n"
				+ "(1, 'SKU-JEWEL-0027', 1, '2025-11-27 01:25:15'),\r\n"
				+ "(4, 'SKU-BABY-0044', 2, '2025-11-20 17:57:19'),\r\n"
				+ "(3, 'SKU-BOOK-0045', 1, '2025-11-26 02:15:56'),\r\n"
				+ "(1, 'SKU-TOY-0022', 2, '2025-11-15 06:01:28'),\r\n"
				+ "(6, 'SKU-BEV-0003', 2, '2025-11-28 23:24:06'),\r\n"
				+ "(2, 'SKU-SPORT-0025', 2, '2025-11-13 03:59:09'),\r\n"
				+ "(2, 'SKU-SPORT-0026', 1, '2025-11-19 07:52:26'),\r\n"
				+ "(1, 'SKU-AMMO-0054', 2, '2025-10-31 16:30:58'),\r\n"
				+ "(4, 'SKU-BEV-0004', 3, '2025-11-12 20:07:33'),\r\n"
				+ "(6, 'SKU-COS-0029', 1, '2025-11-20 04:12:55'),\r\n"
				+ "(4, 'SKU-AMMO-0053', 2, '2025-11-03 09:44:59'),\r\n"
				+ "(6, 'SKU-LUX-0051', 3, '2025-11-06 15:16:07'),\r\n"
				+ "(5, 'SKU-LUX-0052', 2, '2025-11-01 00:12:08'),\r\n"
				+ "(2, 'SKU-TLS-0011', 2, '2025-11-15 15:32:50'),\r\n"
				+ "(1, 'SKU-FTW-0009', 3, '2025-11-29 09:37:14'),\r\n"
				+ "(6, 'SKU-FTW-0009', 3, '2025-11-18 15:05:47'),\r\n"
				+ "(5, 'SKU-AUTO-0024', 1, '2025-11-08 01:42:40'),\r\n"
				+ "(4, 'SKU-PHAR-0006', 2, '2025-11-04 13:12:41'),\r\n"
				+ "(4, 'SKU-BEV-0004', 3, '2025-11-28 07:54:09'),\r\n"
				+ "(4, 'SKU-FURN-0020', 3, '2025-11-19 23:19:48'),\r\n"
				+ "(4, 'SKU-OFF-0042', 2, '2025-11-25 17:56:33'),\r\n"
				+ "(5, 'SKU-BOOK-0046', 2, '2025-11-12 18:33:27'),\r\n"
				+ "(5, 'SKU-IND-0039', 1, '2025-11-12 09:29:53'),\r\n"
				+ "(2, 'SKU-CLEAN-0033', 2, '2025-11-21 16:56:16'),\r\n"
				+ "(2, 'SKU-BOOK-0046', 3, '2025-11-15 15:20:33'),\r\n"
				+ "(4, 'SKU-PHAR-0006', 1, '2025-11-16 22:49:00'),\r\n"
				+ "(3, 'SKU-TOY-0022', 3, '2025-11-19 01:16:33'),\r\n"
				+ "(4, 'SKU-CLEAN-0033', 1, '2025-11-07 19:27:33'),\r\n"
				+ "(4, 'SKU-STAT-0035', 2, '2025-11-19 10:11:13'),\r\n"
				+ "(1, 'SKU-CLEAN-0034', 3, '2025-11-20 04:57:10'),\r\n"
				+ "(4, 'SKU-TLS-0011', 1, '2025-11-06 13:55:00'),\r\n"
				+ "(2, 'SKU-CLEAN-0034', 1, '2025-11-01 16:55:46'),\r\n"
				+ "(4, 'SKU-SPORT-0026', 3, '2025-11-23 10:32:57'),\r\n"
				+ "(6, 'SKU-MUSIC-0047', 1, '2025-11-08 20:59:00'),\r\n"
				+ "(1, 'SKU-IND-0040', 2, '2025-11-16 10:41:25'),\r\n"
				+ "(3, 'SKU-FIRE-0015', 2, '2025-11-14 22:55:27'),\r\n"
				+ "(1, 'SKU-AMMO-0018', 3, '2025-11-28 00:20:19'),\r\n"
				+ "(5, 'SKU-BEV-0003', 2, '2025-11-04 18:40:41'),\r\n"
				+ "(3, 'SKU-FIRE-0015', 1, '2025-11-26 11:01:42'),\r\n"
				+ "(2, 'SKU-PET-0032', 2, '2025-11-27 11:43:32'),\r\n"
				+ "(6, 'SKU-CLEAN-0033', 3, '2025-11-15 21:56:52'),\r\n"
				+ "(5, 'SKU-MUSIC-0047', 3, '2025-11-28 17:12:40'),\r\n"
				+ "(5, 'SKU-AMMO-0018', 1, '2025-11-26 10:04:42'),\r\n"
				+ "(5, 'SKU-PHAR-0006', 2, '2025-10-31 09:38:53'),\r\n"
				+ "(4, 'SKU-PET-0031', 1, '2025-11-10 03:35:12'),\r\n"
				+ "(2, 'SKU-COS-0029', 3, '2025-11-27 06:38:59'),\r\n"
				+ "(1, 'SKU-OFF-0041', 1, '2025-11-28 05:16:51'),\r\n"
				+ "(4, 'SKU-OFF-0041', 1, '2025-11-06 11:40:27'),\r\n"
				+ "(3, 'SKU-STAT-0035', 3, '2025-11-17 07:15:25'),\r\n"
				+ "(5, 'SKU-CLEAN-0033', 1, '2025-11-20 19:26:02'),\r\n"
				+ "(2, 'SKU-TLS-0012', 1, '2025-11-14 11:20:31'),\r\n"
				+ "(3, 'SKU-SPORT-0025', 3, '2025-11-02 15:27:56'),\r\n"
				+ "(6, 'SKU-PET-0032', 1, '2025-11-21 14:44:34'),\r\n"
				+ "(4, 'SKU-MUSIC-0048', 3, '2025-11-26 02:13:19'),\r\n"
				+ "(1, 'SKU-ELEC-0014', 1, '2025-11-17 02:15:54'),\r\n"
				+ "(1, 'SKU-PET-0032', 2, '2025-10-31 23:12:54'),\r\n"
				+ "(2, 'SKU-GAME-0049', 3, '2025-11-25 06:23:44'),\r\n"
				+ "(4, 'SKU-PHAR-0006', 1, '2025-11-06 07:41:52'),\r\n"
				+ "(5, 'SKU-ELEC-0014', 3, '2025-11-14 20:36:30'),\r\n"
				+ "(5, 'SKU-BABY-0044', 1, '2025-11-06 22:54:17'),\r\n"
				+ "(5, 'SKU-FIRE-0016', 3, '2025-11-16 22:34:11'),\r\n"
				+ "(2, 'SKU-CLEAN-0034', 3, '2025-11-26 17:49:40'),\r\n"
				+ "(5, 'SKU-ELEC-0013', 1, '2025-11-22 18:13:00'),\r\n"
				+ "(5, 'SKU-GARD-0037', 1, '2025-11-30 14:21:01'),\r\n"
				+ "(6, 'SKU-CLEAN-0033', 1, '2025-11-03 10:30:55'),\r\n"
				+ "(4, 'SKU-JEWEL-0028', 3, '2025-11-11 18:54:08'),\r\n"
				+ "(1, 'SKU-STAT-0036', 3, '2025-11-22 01:29:12'),\r\n"
				+ "(1, 'SKU-IND-0039', 3, '2025-11-22 22:01:30'),\r\n"
				+ "(2, 'SKU-FTW-0009', 3, '2025-11-09 01:37:24'),\r\n"
				+ "(6, 'SKU-LUX-0051', 2, '2025-11-21 19:43:30'),\r\n"
				+ "(1, 'SKU-GARD-0038', 1, '2025-11-23 01:54:25'),\r\n"
				+ "(4, 'SKU-GARD-0037', 1, '2025-11-12 17:39:32'),\r\n"
				+ "(4, 'SKU-PHAR-0005', 1, '2025-11-22 11:28:49'),\r\n"
				+ "(6, 'SKU-AUTO-0023', 2, '2025-11-15 05:20:25'),\r\n"
				+ "(2, 'SKU-JEWEL-0028', 3, '2025-11-07 02:28:52'),\r\n"
				+ "(6, 'SKU-LUX-0052', 1, '2025-11-22 14:11:40'),\r\n"
				+ "(3, 'SKU-BEV-0003', 2, '2025-11-05 07:12:03'),\r\n"
				+ "(4, 'SKU-PHAR-0006', 1, '2025-11-17 14:43:36'),\r\n"
				+ "(6, 'SKU-ELEC-0014', 3, '2025-11-06 00:14:49'),\r\n"
				+ "(3, 'SKU-BOOK-0045', 3, '2025-11-07 18:45:28'),\r\n"
				+ "(4, 'SKU-PET-0031', 1, '2025-11-29 06:50:14'),\r\n"
				+ "(3, 'SKU-OFF-0041', 2, '2025-11-10 04:26:36'),\r\n"
				+ "(1, 'SKU-FURN-0020', 2, '2025-11-16 21:44:47'),\r\n"
				+ "(4, 'SKU-PHAR-0005', 2, '2025-11-10 10:25:36'),\r\n"
				+ "(5, 'SKU-FURN-0019', 2, '2025-11-11 13:51:03'),\r\n"
				+ "(5, 'SKU-OFF-0042', 2, '2025-11-18 20:11:48'),\r\n"
				+ "(5, 'SKU-GARD-0037', 2, '2025-11-25 18:01:27'),\r\n"
				+ "(4, 'SKU-COS-0029', 1, '2025-11-20 04:17:17'),\r\n"
				+ "(1, 'SKU-IND-0039', 2, '2025-11-06 06:50:14'),\r\n"
				+ "(2, 'SKU-BEV-0004', 3, '2025-11-24 21:31:59'),\r\n"
				+ "(2, 'SKU-AUTO-0023', 3, '2025-11-19 19:31:52'),\r\n"
				+ "(4, 'SKU-STAT-0035', 2, '2025-11-04 09:18:39'),\r\n"
				+ "(6, 'SKU-AMMO-0017', 2, '2025-11-08 04:51:33'),\r\n"
				+ "(4, 'SKU-ELEC-0014', 3, '2025-11-27 21:58:45'),\r\n"
				+ "(3, 'SKU-BEV-0003', 3, '2025-11-12 23:34:59'),\r\n"
				+ "(3, 'SKU-JEWEL-0028', 2, '2025-11-06 01:24:22'),\r\n"
				+ "(1, 'SKU-PET-0031', 2, '2025-11-10 08:53:40'),\r\n"
				+ "(2, 'SKU-APP-0007', 2, '2025-11-18 12:43:25'),\r\n"
				+ "(5, 'SKU-STAT-0035', 2, '2025-11-19 16:28:32'),\r\n"
				+ "(3, 'SKU-BEV-0003', 1, '2025-11-25 13:54:53'),\r\n"
				+ "(3, 'SKU-COS-0030', 1, '2025-11-16 01:49:39'),\r\n"
				+ "(1, 'SKU-AMMO-0017', 2, '2025-11-30 15:17:22'),\r\n"
				+ "(3, 'SKU-CLEAN-0033', 1, '2025-11-15 01:25:31'),\r\n"
				+ "(1, 'SKU-GROC-0001', 1, '2025-11-02 05:47:46'),\r\n"
				+ "(6, 'SKU-GARD-0038', 1, '2025-11-04 19:51:33'),\r\n"
				+ "(6, 'SKU-SPORT-0026', 1, '2025-11-06 21:47:45'),\r\n"
				+ "(3, 'SKU-FTW-0010', 2, '2025-11-03 12:29:36'),\r\n"
				+ "(2, 'SKU-OFF-0042', 2, '2025-11-28 07:02:22'),\r\n"
				+ "(4, 'SKU-GAME-0049', 1, '2025-11-30 13:23:14'),\r\n"
				+ "(1, 'SKU-MUSIC-0047', 3, '2025-11-22 02:51:00'),\r\n"
				+ "(6, 'SKU-CLEAN-0034', 1, '2025-10-31 12:45:25'),\r\n"
				+ "(3, 'SKU-COS-0030', 2, '2025-11-13 21:35:15'),\r\n"
				+ "(1, 'SKU-SPORT-0026', 1, '2025-11-27 10:24:50'),\r\n"
				+ "(5, 'SKU-TOY-0021', 2, '2025-11-05 09:54:52'),\r\n"
				+ "(5, 'SKU-PHAR-0005', 2, '2025-11-19 05:33:03'),\r\n"
				+ "(1, 'SKU-GROC-0001', 2, '2025-11-30 02:32:10'),\r\n"
				+ "(6, 'SKU-CLEAN-0034', 2, '2025-11-09 07:51:31'),\r\n"
				+ "(1, 'SKU-APP-0008', 1, '2025-11-12 11:14:23'),\r\n"
				+ "(6, 'SKU-ELEC-0013', 3, '2025-11-26 16:02:29'),\r\n"
				+ "(3, 'SKU-LUX-0052', 3, '2025-11-16 11:02:33'),\r\n"
				+ "(3, 'SKU-PET-0031', 2, '2025-11-15 19:16:14'),\r\n"
				+ "(3, 'SKU-AUTO-0024', 1, '2025-11-05 16:06:38'),\r\n"
				+ "(2, 'SKU-AMMO-0054', 2, '2025-11-27 10:49:23'),\r\n"
				+ "(6, 'SKU-LUX-0052', 2, '2025-11-21 01:51:01'),\r\n"
				+ "(2, 'SKU-CLEAN-0034', 2, '2025-11-05 00:28:38'),\r\n"
				+ "(3, 'SKU-STAT-0036', 2, '2025-11-06 21:07:23'),\r\n"
				+ "(2, 'SKU-MUSIC-0048', 1, '2025-11-14 01:26:45'),\r\n"
				+ "(5, 'SKU-GROC-0001', 1, '2025-11-27 00:55:48'),\r\n"
				+ "(4, 'SKU-IND-0039', 2, '2025-11-25 10:38:44'),\r\n"
				+ "(4, 'SKU-GROC-0001', 2, '2025-11-05 10:53:09'),\r\n"
				+ "(6, 'SKU-COS-0030', 3, '2025-11-27 01:27:29'),\r\n"
				+ "(1, 'SKU-BEV-0004', 1, '2025-11-27 00:38:23'),\r\n"
				+ "(1, 'SKU-APP-0008', 1, '2025-11-07 20:11:27'),\r\n"
				+ "(1, 'SKU-APP-0007', 1, '2025-11-09 05:34:57'),\r\n"
				+ "(4, 'SKU-FURN-0019', 1, '2025-11-30 20:42:00'),\r\n"
				+ "(2, 'SKU-GAME-0049', 2, '2025-11-19 06:38:15'),\r\n"
				+ "(1, 'SKU-OFF-0041', 3, '2025-11-09 06:45:21'),\r\n"
				+ "(1, 'SKU-GARD-0037', 2, '2025-11-03 19:37:40'),\r\n"
				+ "(1, 'SKU-GROC-0001', 2, '2025-11-22 16:20:05'),\r\n"
				+ "(6, 'SKU-COS-0030', 3, '2025-11-27 07:45:30'),\r\n"
				+ "(5, 'SKU-PET-0031', 1, '2025-11-16 07:36:24'),\r\n"
				+ "(2, 'SKU-JEWEL-0028', 2, '2025-11-27 14:57:00'),\r\n"
				+ "(1, 'SKU-AUTO-0024', 3, '2025-11-30 01:20:30'),\r\n"
				+ "(6, 'SKU-IND-0039', 3, '2025-11-27 07:10:46'),\r\n"
				+ "(4, 'SKU-SPORT-0026', 3, '2025-11-05 01:12:25'),\r\n"
				+ "(5, 'SKU-FURN-0020', 1, '2025-11-01 09:22:58'),\r\n"
				+ "(2, 'SKU-BEV-0004', 1, '2025-11-16 05:19:35'),\r\n"
				+ "(6, 'SKU-GROC-0001', 3, '2025-11-29 22:57:33'),\r\n"
				+ "(5, 'SKU-JEWEL-0028', 3, '2025-11-02 22:52:57'),\r\n"
				+ "(4, 'SKU-FURN-0020', 1, '2025-11-12 04:09:05'),\r\n"
				+ "(1, 'SKU-JEWEL-0027', 1, '2025-11-26 18:06:00'),\r\n"
				+ "(5, 'SKU-TOY-0022', 2, '2025-11-08 04:53:40'),\r\n"
				+ "(3, 'SKU-STAT-0035', 1, '2025-11-12 02:08:53'),\r\n"
				+ "(3, 'SKU-TLS-0012', 1, '2025-11-16 07:04:53'),\r\n"
				+ "(6, 'SKU-BABY-0043', 3, '2025-11-20 09:51:06'),\r\n"
				+ "(2, 'SKU-AMMO-0018', 2, '2025-11-08 08:40:04'),\r\n"
				+ "(3, 'SKU-CLEAN-0034', 3, '2025-11-03 20:06:01'),\r\n"
				+ "(5, 'SKU-AMMO-0018', 2, '2025-11-08 00:43:03'),\r\n"
				+ "(4, 'SKU-PHAR-0006', 1, '2025-11-18 17:49:04'),\r\n"
				+ "(2, 'SKU-CLEAN-0034', 2, '2025-11-22 06:07:30'),\r\n"
				+ "(4, 'SKU-JEWEL-0027', 1, '2025-11-12 14:49:50'),\r\n"
				+ "(3, 'SKU-MUSIC-0047', 1, '2025-11-09 11:03:10'),\r\n"
				+ "(1, 'SKU-BOOK-0045', 3, '2025-11-19 17:18:02'),\r\n"
				+ "(1, 'SKU-STAT-0035', 2, '2025-11-26 15:36:42'),\r\n"
				+ "(6, 'SKU-GAME-0050', 3, '2025-11-12 21:55:43'),\r\n"
				+ "(5, 'SKU-ELEC-0014', 1, '2025-11-21 19:26:22'),\r\n"
				+ "(2, 'SKU-PHAR-0005', 3, '2025-11-11 06:55:55'),\r\n"
				+ "(1, 'SKU-GROC-0001', 1, '2025-11-27 11:16:07'),\r\n"
				+ "(3, 'SKU-CLEAN-0034', 1, '2025-11-30 19:05:00'),\r\n"
				+ "(2, 'SKU-TOY-0022', 2, '2025-11-21 21:07:00'),\r\n"
				+ "(6, 'SKU-BOOK-0045', 2, '2025-11-25 22:19:52'),\r\n"
				+ "(3, 'SKU-PET-0032', 1, '2025-11-16 05:46:19'),\r\n"
				+ "(3, 'SKU-APP-0008', 3, '2025-11-18 22:03:23'),\r\n"
				+ "(6, 'SKU-AUTO-0024', 1, '2025-11-29 13:41:09'),\r\n"
				+ "(6, 'SKU-PET-0031', 1, '2025-11-21 03:55:26'),\r\n"
				+ "(3, 'SKU-PET-0032', 2, '2025-11-04 23:24:05'),\r\n"
				+ "(1, 'SKU-GARD-0038', 1, '2025-11-27 11:24:55'),\r\n"
				+ "(6, 'SKU-FURN-0019', 1, '2025-11-13 04:15:44'),\r\n"
				+ "(2, 'SKU-BABY-0044', 2, '2025-11-06 14:51:30'),\r\n"
				+ "(4, 'SKU-MUSIC-0048', 2, '2025-11-10 21:43:07'),\r\n"
				+ "(5, 'SKU-TOY-0022', 3, '2025-11-24 03:20:26'),\r\n"
				+ "(3, 'SKU-CLEAN-0034', 2, '2025-11-15 03:09:13'),\r\n"
				+ "(5, 'SKU-COS-0030', 2, '2025-11-22 14:16:08'),\r\n"
				+ "(1, 'SKU-MUSIC-0048', 2, '2025-11-26 01:30:20'),\r\n"
				+ "(6, 'SKU-OFF-0041', 2, '2025-11-18 15:20:53'),\r\n"
				+ "(2, 'SKU-BOOK-0046', 1, '2025-11-05 06:51:45'),\r\n"
				+ "(6, 'SKU-BABY-0043', 1, '2025-11-07 16:27:38'),\r\n"
				+ "(6, 'SKU-BEV-0003', 2, '2025-11-02 10:15:15'),\r\n"
				+ "(2, 'SKU-APP-0007', 3, '2025-12-01 01:39:43'),\r\n"
				+ "(3, 'SKU-GARD-0037', 1, '2025-11-19 09:56:00'),\r\n"
				+ "(1, 'SKU-AMMO-0017', 1, '2025-11-14 03:40:45'),\r\n"
				+ "(3, 'SKU-AUTO-0024', 1, '2025-11-12 19:24:58'),\r\n"
				+ "(3, 'SKU-COS-0029', 1, '2025-11-12 13:20:35'),\r\n"
				+ "(5, 'SKU-AMMO-0054', 2, '2025-11-24 09:04:02')";
		try {
	        stmt.execute(sql);
	    } catch (SQLException e) {
	        System.out.println("Seed insertion failed - transactions");
	        throw new SQLException(e);
	    }
	}
	
}
