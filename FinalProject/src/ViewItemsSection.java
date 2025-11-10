import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
		
		
//		select enums, date by range, ints by equals or range, floats by range,
//		sort dates, ints and floats, sort strings alphabetically
		
		/* Category Select */
		FlowPane categorySelect = new FlowPane();
		TextField categoryTF = new TextField();
		categorySelect.getChildren().add(categoryTF);
		VBox categoryVBox = new VBox();
		Category[] categoryVals = Category.values();
		for (Category category : Category.values()) {
		    Label label = new Label(category.name());
		    label.getStyleClass().add("select-item");
		    categoryVBox.getChildren().add(label);
		}
		categoryTF.setOnMouseClicked(event -> {
			
		});
		
		getChildren().addAll(categorySelect);
		
		/* Search Bar */
		HBox searchBar = new HBox();
		
		Label searchLbl = new Label("Search: ");
		TextField titleTextTF = new TextField();
		Button searchBtn = new Button("Search");
		
		searchBar.getChildren().addAll(searchLbl,titleTextTF,searchBtn);
		getChildren().add(searchBar);
		
		
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList("Item Name", "Description", "Weight (kg)", "Price", "Tax Bracket", "Expiration Time", "SKU", "Category", "Units Per Bin", "Date Added", "Last Updated", "Min temp", "Max temp"));
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
        
        searchBtn.setOnAction(event -> {
	        try {
	        	String text = titleTextTF.getText();
	        	if (text.equals(""))
	        		text = "%";
	        	
        		table.getItems().clear();
	        	ArrayList<InventoryItem> allItems = db.getItems(text);
	        	for (InventoryItem item : allItems)
	        		table.getItems().add(item);
				System.out.println(allItems);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
 
	}
}
