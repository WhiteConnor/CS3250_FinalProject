import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
/**
 * MenuBanner class to handle menu on every page
 */
public class MenuBanner extends FlowPane {
	/**
	 * Builds menu on top of every page
	 */
	public MenuBanner(MainPage page, User user) {
		
		MenuBar mainMenuBar = new MenuBar();
		
		/* File */
		Menu fileMenu = new Menu("File");
		MenuItem homeFile = new MenuItem("Home");
		MenuItem logoutFile = new MenuItem("Logout");
		
		fileMenu.getItems().addAll(homeFile, logoutFile);
		
		/* Inventory */
		Menu inventoryMenu = new Menu("Inventory");
		
		MenuItem addInv = new MenuItem("Add Item");
		MenuItem viewInv = new MenuItem("View Items");
		
		inventoryMenu.getItems().addAll(addInv, viewInv);
		
		/* warehouse */
		Menu warehouseMenu = new Menu("Warehouse");
		
		MenuItem receiptWare = new MenuItem("Receipts");
		
		warehouseMenu.getItems().addAll(receiptWare);
		
		/* Transactions */
		
		Menu transactionsMenu = new Menu("Transactions");
		MenuItem viewTransaction = new MenuItem("View");
		MenuItem createTransaction = new MenuItem("Create");
		transactionsMenu.getItems().addAll(viewTransaction, createTransaction);
		
		mainMenuBar.getMenus().addAll(fileMenu, inventoryMenu, warehouseMenu, transactionsMenu);

		getChildren().add(mainMenuBar);
		
		createTransaction.setOnAction(event -> {
			System.out.println("Create Transactions Page");
			CreateTransactionSection newSection = new CreateTransactionSection(page, user);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(newSection);
		});
		
		viewTransaction.setOnAction(event -> {
			System.out.println("View Transactions Page");
			ViewTransactionsSection newSection = new ViewTransactionsSection(page);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(newSection);
		});
		
		addInv.setOnAction(event -> {
			System.out.println("Create Item Page");
			CreateInventoryItemSection newSection = new CreateInventoryItemSection(page, user);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(newSection);
		});
		
		viewInv.setOnAction(event -> {
			System.out.println("View Items page");
			ViewItemsSection newSection = new ViewItemsSection(page, user);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(newSection);
		});
		
		homeFile.setOnAction(event -> {
			System.out.println("Home page");
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(new WelcomePage());
		});
		
		
		
		receiptWare.setOnAction(event -> {
			System.out.println("Transferring to warehouse receipt page");
			WarehouseReceipt warehouseReceipt = new WarehouseReceipt(page);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(warehouseReceipt);
		});
		
		logoutFile.setOnAction(event -> {
			System.out.println("Logging out");
			page.setTop(null);
			LoginSection loginSection = new LoginSection(page);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(loginSection);
		});
	}
}
