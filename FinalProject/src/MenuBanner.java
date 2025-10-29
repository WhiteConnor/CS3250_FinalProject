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
		
		Menu fileMenu = new Menu("File");
		MenuItem homeFile = new MenuItem("Home");
		MenuItem settingsFile = new MenuItem("Settings");
		MenuItem logoutFile = new MenuItem("Logout");
		
		fileMenu.getItems().addAll(homeFile, settingsFile, logoutFile);
		
		Menu inventoryMenu = new Menu("Inventory");
		
		MenuItem addInv = new MenuItem("Add Item");
		MenuItem viewInv = new MenuItem("View Items");
		
		inventoryMenu.getItems().addAll(addInv, viewInv);
		
		Menu warehouseMenu = new Menu("Warehouse");
		
		MenuItem receiptWare = new MenuItem("Receipts");
		
		warehouseMenu.getItems().addAll(receiptWare);
		
		mainMenuBar.getMenus().addAll(fileMenu, inventoryMenu, warehouseMenu);

		getChildren().add(mainMenuBar);
		
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
			WarehouseReceipt warehouseReceipt = new WarehouseReceipt();
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
