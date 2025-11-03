import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewItemsSection extends VBox {
	private ArrayList<String> propertyNames = new ArrayList<>(Arrays.asList(
		    "itemName",
		    "description",
		    "weightKG",
		    "price",
		    "taxBracket",
		    "expirationTime",
		    "SKU",
		    "category",
		    "unitsPerBin",
		    "dateAdded",
		    "lastUpdated",
		    "minTemp",
		    "maxTemp"
		));
	
	public ViewItemsSection(MainPage page, User user) {
		Label titleLabel = new Label("View Inventory Items");
		getChildren().add(titleLabel);
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList("Item Name", "Description", "Weight (kg)", "Price", "Tax Bracket", "Expiration Time", "SKU", "Category", "Units Per Bin", "Date Added", "Last Updated", "Min temp", "Max temp"));
//        for (String colStr : tableCols) {
//        	TableColumn column = new TableColumn(colStr);
//        	table.getColumns().add(column);
//        }
        for (int i = 0; i < tableCols.size(); i++) {
            TableColumn<InventoryItem, Object> column = new TableColumn<>(tableCols.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames.get(i)));
            table.getColumns().add(column);
        }

        setSpacing(5);
        getChildren().addAll(table);
        
        DB db = new DB();
        try {
        	ArrayList<InventoryItem> allItems = db.getItems();
        	for (InventoryItem item : allItems)
        		table.getItems().add(item);
			System.out.println(allItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
}
