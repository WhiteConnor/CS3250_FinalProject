import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
/**
 * MenuBanner class to handle menu on every page
 */
public class MenuBanner extends FlowPane {
	/**
	 * Builds menu on top of every page
	 */
	public MenuBanner(MainPage page, User user) {
		Button homeButton = new Button("Home");
		Button settingsButton = new Button("Settings");
		Button addInventoryButton = new Button("Add Inventory Item");
		Button warehouseReceiptButton = new Button("Warehouse Receipt");
		Button logoutButton = new Button("Logout");
		getChildren().addAll(
				homeButton,
				settingsButton,
				addInventoryButton,
				warehouseReceiptButton,
				logoutButton
				);
		
		homeButton.setOnAction(event -> {
			System.out.println("Home page");
			page.setCenter(new WelcomePage());
		});
		
		addInventoryButton.setOnAction(event -> {
			System.out.println("Transferring to Add Inventory Item Page");
			CreateInventoryItemSection invItemPage = new CreateInventoryItemSection(user);
			page.setCenter(invItemPage);
		});
		
		warehouseReceiptButton.setOnAction(event -> {
			System.out.println("Transferring to warehouse receipt page");
			WarehouseReceipt warehouseReceipt = new WarehouseReceipt();
			page.setCenter(warehouseReceipt);
		});
		
		logoutButton.setOnAction(event -> {
			System.out.println("Logging out");
			page.setTop(null);
			LoginSection loginSection = new LoginSection(page);
			page.setCenter(loginSection);
		});
	}
}
