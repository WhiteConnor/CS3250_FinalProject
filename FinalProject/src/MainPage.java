import javafx.scene.layout.BorderPane;

/**
 * MainPage class to handle main page content
 * 
 * @author white
 */
public class MainPage extends BorderPane {
	/**
	 * MainPage constructor builds main page
	 */
	public MainPage() {
		MenuBanner menuBanner = new MenuBanner();
		menuBanner.getStyleClass().add("menuBanner");
		setTop(menuBanner);
		WarehouseReceipt warehouseReceipt = new WarehouseReceipt();
		setCenter(warehouseReceipt);
	}
}
