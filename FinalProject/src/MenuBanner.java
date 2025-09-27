import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
/**
 * MenuBanner class to handle menu on every page
 */
public class MenuBanner extends FlowPane {
	/**
	 * Builds menu on top of every page
	 */
	public MenuBanner() {
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
		
	}
}
