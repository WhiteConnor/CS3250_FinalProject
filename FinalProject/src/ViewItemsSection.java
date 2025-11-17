import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewItemsSection extends StackPane {
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
		DB db = new DB();
		VBox root = new VBox();
		this.getChildren().add(root);
		Label titleLabel = new Label("View Inventory Items");
		root.getChildren().add(titleLabel);
		
		
//		select enums, date by range, ints by equals or range, floats by range,
//		sort dates, ints and floats, sort strings alphabetically
		
		/* Category Select */
		FlowPane categoryFP = new FlowPane();
		root.getChildren().add(categoryFP);
		Button addCatFiltersBtn = new Button("Add Categories");
		root.getChildren().add(addCatFiltersBtn);
		Button applyFiltersBtn = new Button("Apply Filters");
		
		applyFiltersBtn.setOnAction(e -> {
			ArrayList<Category> selectedCategories = new ArrayList<>();
        	categoryFP.getChildren().forEach(child -> {
        		String text = ((CategoryButton) child).getText();
        		System.out.println(text);
        		Category category = Category.valueOf(text);
        		selectedCategories.add(category);
        	});
        	if (selectedCategories.isEmpty()) {
        		selectedCategories.addAll(List.of(Category.values()));
        		System.out.println(selectedCategories);
        	} else {
        		System.out.println(selectedCategories);
        	}
		});
		
		root.getChildren().add(applyFiltersBtn);
		
		Popup categoryRoot = new Popup();
		FlowPane categoryVBox = new FlowPane();

		categoryVBox.setPrefSize(200,200);
		categoryVBox.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");

		Category[] categoryVals = Category.values();
		for (Category category : categoryVals) {
		    Button button = new Button(category.name());
		    button.getStyleClass().add("select-item");
		    button.setOnAction(e -> {
		    	categoryFP.getChildren().add(new CategoryButton(category.name(), button));
		    	button.setVisible(false);
		        categoryRoot.hide();
		    });

		    categoryVBox.getChildren().add(button);
		    
		}
		
		categoryRoot.getContent().add(categoryVBox);
		
		addCatFiltersBtn.setOnAction(event -> {
			Bounds bounds = categoryFP.localToScreen(categoryFP.getBoundsInLocal());
			categoryRoot.show(categoryFP, bounds.getMinX(), bounds.getMaxY());

		});
		
//		getChildren().addAll(categoryRoot);
		
		/* Search Bar */
		HBox searchBar = new HBox();
		
		Label searchLbl = new Label("Search: ");
		TextField titleTextTF = new TextField();
		Button searchBtn = new Button("Search");
		
		searchBar.getChildren().addAll(searchLbl,titleTextTF,searchBtn);
		root.getChildren().add(searchBar);
		
		
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList("Item Name", "Description", "Weight (kg)", "Price", "Tax Bracket", "Expiration Time", "SKU", "Category", "Units Per Bin", "Date Added", "Last Updated", "Min temp", "Max temp"));
        for (int i = 0; i < tableCols.size(); i++) {
            TableColumn<InventoryItem, Object> column = new TableColumn<>(tableCols.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames.get(i)));
            table.getColumns().add(column);
        }

        root.setSpacing(5);
        root.getChildren().addAll(table);
        
        
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
