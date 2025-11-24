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
	            + "('SKU-AMMO-0017','2025-10-01','LOT-AMMO17-1',120),\r\n"
	            + "('SKU-AMMO-0017','2025-10-15','LOT-AMMO17-2',80),\r\n"
	            + "('SKU-AMMO-0017','2025-11-01','LOT-AMMO17-3',95),\r\n"
	            + "('SKU-AMMO-0018','2025-10-02','LOT-AMMO18-1',150),\r\n"
	            + "('SKU-AMMO-0018','2025-10-20','LOT-AMMO18-2',90),\r\n"
	            + "('SKU-AMMO-0018','2025-11-05','LOT-AMMO18-3',110),\r\n"
	            + "('SKU-AMMO-0053','2025-10-05','LOT-AMMO53-1',200),\r\n"
	            + "('SKU-AMMO-0053','2025-10-25','LOT-AMMO53-2',110),\r\n"
	            + "('SKU-AMMO-0053','2025-11-10','LOT-AMMO53-3',130),\r\n"
	            + "('SKU-AMMO-0054','2025-10-06','LOT-AMMO54-1',175),\r\n"
	            + "('SKU-AMMO-0054','2025-10-28','LOT-AMMO54-2',95),\r\n"
	            + "('SKU-AMMO-0054','2025-11-12','LOT-AMMO54-3',105),\r\n"
	            + "('SKU-APP-0007','2025-10-03','LOT-APP7-1',60),\r\n"
	            + "('SKU-APP-0007','2025-10-18','LOT-APP7-2',40),\r\n"
	            + "('SKU-APP-0007','2025-11-02','LOT-APP7-3',55),\r\n"
	            + "('SKU-APP-0008','2025-10-04','LOT-APP8-1',75),\r\n"
	            + "('SKU-APP-0008','2025-10-19','LOT-APP8-2',55),\r\n"
	            + "('SKU-APP-0008','2025-11-03','LOT-APP8-3',65),\r\n"
	            + "('SKU-AUTO-0023','2025-10-07','LOT-AUTO23-1',30),\r\n"
	            + "('SKU-AUTO-0023','2025-10-21','LOT-AUTO23-2',25),\r\n"
	            + "('SKU-AUTO-0023','2025-11-04','LOT-AUTO23-3',28),\r\n"
	            + "('SKU-AUTO-0024','2025-10-08','LOT-AUTO24-1',40),\r\n"
	            + "('SKU-AUTO-0024','2025-10-22','LOT-AUTO24-2',35),\r\n"
	            + "('SKU-AUTO-0024','2025-11-06','LOT-AUTO24-3',38),\r\n"
	            + "('SKU-BABY-0043','2025-10-09','LOT-BABY43-1',50),\r\n"
	            + "('SKU-BABY-0043','2025-10-23','LOT-BABY43-2',45),\r\n"
	            + "('SKU-BABY-0043','2025-11-07','LOT-BABY43-3',48),\r\n"
	            + "('SKU-BABY-0044','2025-10-10','LOT-BABY44-1',55),\r\n"
	            + "('SKU-BABY-0044','2025-10-24','LOT-BABY44-2',50),\r\n"
	            + "('SKU-BABY-0044','2025-11-08','LOT-BABY44-3',52),\r\n"
	            + "('SKU-BEV-0003','2025-10-11','LOT-BEV3-1',100),\r\n"
	            + "('SKU-BEV-0003','2025-10-26','LOT-BEV3-2',90),\r\n"
	            + "('SKU-BEV-0003','2025-11-09','LOT-BEV3-3',95),\r\n"
	            + "('SKU-BEV-0004','2025-10-12','LOT-BEV4-1',110),\r\n"
	            + "('SKU-BEV-0004','2025-10-27','LOT-BEV4-2',95),\r\n"
	            + "('SKU-BEV-0004','2025-11-10','LOT-BEV4-3',105),\r\n"
	            + "('SKU-BOOK-0045','2025-10-13','LOT-BOOK45-1',70),\r\n"
	            + "('SKU-BOOK-0045','2025-10-28','LOT-BOOK45-2',65),\r\n"
	            + "('SKU-BOOK-0045','2025-11-11','LOT-BOOK45-3',68),\r\n"
	            + "('SKU-BOOK-0046','2025-10-14','LOT-BOOK46-1',80),\r\n"
	            + "('SKU-BOOK-0046','2025-10-29','LOT-BOOK46-2',75),\r\n"
	            + "('SKU-BOOK-0046','2025-11-12','LOT-BOOK46-3',78),\r\n"
	            + "('SKU-CLEAN-0033','2025-10-15','LOT-CLEAN33-1',85),\r\n"
	            + "('SKU-CLEAN-0033','2025-10-30','LOT-CLEAN33-2',70),\r\n"
	            + "('SKU-CLEAN-0033','2025-11-13','LOT-CLEAN33-3',75),\r\n"
	            + "('SKU-CLEAN-0034','2025-10-16','LOT-CLEAN34-1',95),\r\n"
	            + "('SKU-CLEAN-0034','2025-10-31','LOT-CLEAN34-2',80),\r\n"
	            + "('SKU-CLEAN-0034','2025-11-14','LOT-CLEAN34-3',85),\r\n"
	            + "('SKU-COS-0029','2025-10-17','LOT-COS29-1',60),\r\n"
	            + "('SKU-COS-0029','2025-10-25','LOT-COS29-2',55),\r\n"
	            + "('SKU-COS-0029','2025-11-15','LOT-COS29-3',58),\r\n"
	            + "('SKU-COS-0030','2025-10-18','LOT-COS30-1',65),\r\n"
	            + "('SKU-COS-0030','2025-10-26','LOT-COS30-2',60),\r\n"
	            + "('SKU-COS-0030','2025-11-16','LOT-COS30-3',63),\r\n"
	            + "('SKU-ELEC-0013','2025-10-19','LOT-ELEC13-1',40),\r\n"
	            + "('SKU-ELEC-0013','2025-10-27','LOT-ELEC13-2',35),\r\n"
	            + "('SKU-ELEC-0013','2025-11-05','LOT-ELEC13-3',38),\r\n"
	            + "('SKU-ELEC-0014','2025-10-20','LOT-ELEC14-1',50),\r\n"
	            + "('SKU-ELEC-0014','2025-10-28','LOT-ELEC14-2',45),\r\n"
	            + "('SKU-ELEC-0014','2025-11-06','LOT-ELEC14-3',48),\r\n"
	            + "('SKU-FIRE-0015','2025-10-21','LOT-FIRE15-1',25),\r\n"
	            + "('SKU-FIRE-0015','2025-10-29','LOT-FIRE15-2',20),\r\n"
	            + "('SKU-FIRE-0015','2025-11-07','LOT-FIRE15-3',22),\r\n"
	            + "('SKU-FIRE-0016','2025-10-22','LOT-FIRE16-1',30),\r\n"
	            + "('SKU-FIRE-0016','2025-10-30','LOT-FIRE16-2',25),\r\n"
	            + "('SKU-FIRE-0016','2025-11-08','LOT-FIRE16-3',28),\r\n"
	            + "('SKU-FTW-0009','2025-10-23','LOT-FTW9-1',70),\r\n"
	            + "('SKU-FTW-0009','2025-10-31','LOT-FTW9-2',65),\r\n"
	            + "('SKU-FTW-0009','2025-11-09','LOT-FTW9-3',68),\r\n"
	            + "('SKU-FTW-0010','2025-10-24','LOT-FTW10-1',80),\r\n"
	            + "('SKU-FTW-0010','2025-11-01','LOT-FTW10-2',75),\r\n"
	            + "('SKU-FTW-0010','2025-11-10','LOT-FTW10-3',78),\r\n"
	            + "('SKU-FURN-0019','2025-10-25','LOT-FURN19-1',45),\r\n"
	            + "('SKU-FURN-0019','2025-11-02','LOT-FURN19-2',40),\r\n"
	            + "('SKU-FURN-0019','2025-11-11','LOT-FURN19-3',42),\r\n"
	            + "('SKU-FURN-0020','2025-10-26','LOT-FURN20-1',55),\r\n"
	            + "('SKU-FURN-0020','2025-11-03','LOT-FURN20-2',50),\r\n"
	            + "('SKU-FURN-0020','2025-11-12','LOT-FURN20-3',53),\r\n"
	            + "('SKU-GAME-0049','2025-10-27','LOT-GAME49-1',90),\r\n"
	            + "('SKU-GAME-0049','2025-11-04','LOT-GAME49-2',85),\r\n"
	            + "('SKU-GAME-0049','2025-11-13','LOT-GAME49-3',88),\r\n"
	            + "('SKU-GAME-0050','2025-10-28','LOT-GAME50-1',100),\r\n"
	            + "('SKU-GAME-0050','2025-11-05','LOT-GAME50-2',95),\r\n"
	            + "('SKU-GAME-0050','2025-11-14','LOT-GAME50-3',98),\r\n"
	            + "('SKU-GARD-0037','2025-10-29','LOT-GARD37-1',60),\r\n"
	            + "('SKU-GARD-0037','2025-11-06','LOT-GARD37-2',55),\r\n"
	            + "('SKU-GARD-0037','2025-11-15','LOT-GARD37-3',58),\r\n"
	            + "('SKU-GARD-0038','2025-10-30','LOT-GARD38-1',70),\r\n"
	            + "('SKU-GARD-0038','2025-11-07','LOT-GARD38-2',65),\r\n"
	            + "('SKU-GARD-0038','2025-11-16','LOT-GARD38-3',68),\r\n"
	            + "('SKU-GROC-0001','2025-10-31','LOT-GROC1-1',200),\r\n"
	            + "('SKU-GROC-0001','2025-11-08','LOT-GROC1-2',180),\r\n"
	            + "('SKU-GROC-0001','2025-11-17','LOT-GROC1-3',190),\r\n"
	            + "('SKU-GROC-0002','2025-11-01','LOT-GROC2-1',220),\r\n"
	            + "('SKU-GROC-0002','2025-11-09','LOT-GROC2-2',190),\r\n"
	            + "('SKU-GROC-0002','2025-11-18','LOT-GROC2-3',200),\r\n"
	            + "('SKU-IND-0039','2025-11-02','LOT-IND39-1',35),\r\n"
	            + "('SKU-IND-0039','2025-11-10','LOT-IND39-2',30),\r\n"
	            + "('SKU-IND-0039','2025-11-19','LOT-IND39-3',32),\r\n"
	            + "('SKU-IND-0040','2025-11-03','LOT-IND40-1',45),\r\n"
	            + "('SKU-IND-0040','2025-11-11','LOT-IND40-2',40),\r\n"
	            + "('SKU-IND-0040','2025-11-20','LOT-IND40-3',42),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-04','LOT-JEWEL27-1',15),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-12','LOT-JEWEL27-2',12),\r\n"
	            + "('SKU-JEWEL-0027','2025-11-21','LOT-JEWEL27-3',14),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-05','LOT-JEWEL28-1',20),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-13','LOT-JEWEL28-2',18),\r\n"
	            + "('SKU-JEWEL-0028','2025-11-22','LOT-JEWEL28-3',19),\r\n"
	            + "('SKU-LUX-0051','2025-11-06','LOT-LUX51-1',10),\r\n"
	            + "('SKU-LUX-0051','2025-11-14','LOT-LUX51-2',8),\r\n"
	            + "('SKU-LUX-0051','2025-11-23','LOT-LUX51-3',9),\r\n"
	            + "('SKU-LUX-0052','2025-11-07','LOT-LUX52-1',12),\r\n"
	            + "('SKU-LUX-0052','2025-11-15','LOT-LUX52-2',9),\r\n"
	            + "('SKU-LUX-0052','2025-11-24','LOT-LUX52-3',11),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-08','LOT-MUSIC47-1',50),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-16','LOT-MUSIC47-2',45),\r\n"
	            + "('SKU-MUSIC-0047','2025-11-25','LOT-MUSIC47-3',48),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-09','LOT-MUSIC48-1',55),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-17','LOT-MUSIC48-2',50),\r\n"
	            + "('SKU-MUSIC-0048','2025-11-26','LOT-MUSIC48-3',53),\r\n"
	            + "('SKU-OFF-0041','2025-11-10','LOT-OFF41-1',70),\r\n"
	            + "('SKU-OFF-0041','2025-11-18','LOT-OFF41-2',65),\r\n"
	            + "('SKU-OFF-0041','2025-11-27','LOT-OFF41-3',68),\r\n"
	            + "('SKU-OFF-0042','2025-11-11','LOT-OFF42-1',80),\r\n"
	            + "('SKU-OFF-0042','2025-11-19','LOT-OFF42-2',75),\r\n"
	            + "('SKU-OFF-0042','2025-11-28','LOT-OFF42-3',78),\r\n"
	            + "('SKU-PET-0031','2025-11-12','LOT-PET31-1',90),\r\n"
	            + "('SKU-PET-0031','2025-11-20','LOT-PET31-2',85),\r\n"
	            + "('SKU-PET-0031','2025-11-29','LOT-PET31-3',88),\r\n"
	            + "('SKU-PET-0032','2025-11-13','LOT-PET32-1',100),\r\n"
	            + "('SKU-PET-0032','2025-11-21','LOT-PET32-2',95),\r\n"
	            + "('SKU-PET-0032','2025-11-30','LOT-PET32-3',98),\r\n"
	            + "('SKU-PHAR-0005','2025-11-14','LOT-PHAR5-1',120),\r\n"
	            + "('SKU-PHAR-0005','2025-11-22','LOT-PHAR5-2',110),\r\n"
	            + "('SKU-PHAR-0005','2025-12-01','LOT-PHAR5-3',115),\r\n"
	            + "('SKU-PHAR-0006','2025-11-15','LOT-PHAR6-1',130),\r\n"
	            + "('SKU-PHAR-0006','2025-11-23','LOT-PHAR6-2',115),\r\n"
	            + "('SKU-PHAR-0006','2025-12-02','LOT-PHAR6-3',120),\r\n"
	            + "('SKU-SPORT-0025','2025-11-16','LOT-SPORT25-1',75),\r\n"
	            + "('SKU-SPORT-0025','2025-11-24','LOT-SPORT25-2',70),\r\n"
	            + "('SKU-SPORT-0025','2025-12-03','LOT-SPORT25-3',72),\r\n"
	            + "('SKU-SPORT-0026','2025-11-17','LOT-SPORT26-1',85),\r\n"
	            + "('SKU-SPORT-0026','2025-11-25','LOT-SPORT26-2',80),\r\n"
	            + "('SKU-SPORT-0026','2025-12-04','LOT-SPORT26-3',83),\r\n"
	            + "('SKU-STAT-0035','2025-11-18','LOT-STAT35-1',60),\r\n"
	            + "('SKU-STAT-0035','2025-11-26','LOT-STAT35-2',55),\r\n"
	            + "('SKU-STAT-0035','2025-12-05','LOT-STAT35-3',58),\r\n"
	            + "('SKU-STAT-0036','2025-11-19','LOT-STAT36-1',65),\r\n"
	            + "('SKU-STAT-0036','2025-11-27','LOT-STAT36-2',60),\r\n"
	            + "('SKU-STAT-0036','2025-12-06','LOT-STAT36-3',63),\r\n"
	            + "('SKU-TLS-0011','2025-11-20','LOT-TLS11-1',40),\r\n"
	            + "('SKU-TLS-0011','2025-11-28','LOT-TLS11-2',35),\r\n"
	            + "('SKU-TLS-0011','2025-12-07','LOT-TLS11-3',38),\r\n"
	            + "('SKU-TLS-0012','2025-11-21','LOT-TLS12-1',50),\r\n"
	            + "('SKU-TLS-0012','2025-11-29','LOT-TLS12-2',45),\r\n"
	            + "('SKU-TLS-0012','2025-12-08','LOT-TLS12-3',48),\r\n"
	            + "('SKU-TOY-0021','2025-11-22','LOT-TOY21-1',90),\r\n"
	            + "('SKU-TOY-0021','2025-11-30','LOT-TOY21-2',85),\r\n"
	            + "('SKU-TOY-0021','2025-12-09','LOT-TOY21-3',88),\r\n"
	            + "('SKU-TOY-0022','2025-11-23','LOT-TOY22-1',100),\r\n"
	            + "('SKU-TOY-0022','2025-12-01','LOT-TOY22-2',95),\r\n"
	            + "('SKU-TOY-0022','2025-12-10','LOT-TOY22-3',98)\r\n"
	            + ";";

	    try {
	        stmt.execute(sql);
	    } catch (SQLException e) {
	        System.out.println("Seed insertion failed - warehouse_receipt");
	        throw new SQLException(e);
	    }
	}
	
}
